package cn.don9cn.blog.service.bussiness.articlecomment.impl;

import cn.don9cn.blog.autoconfigs.activemq.constant.MqDestinationType;
import cn.don9cn.blog.autoconfigs.activemq.core.MqManager;
import cn.don9cn.blog.autoconfigs.activemq.model.CommonMqMessage;
import cn.don9cn.blog.autoconfigs.activemq.model.MqRegisterMessage;
import cn.don9cn.blog.dao.bussiness.article.interf.ArticleDao;
import cn.don9cn.blog.dao.bussiness.articlecomment.interf.ArticleCommentDao;
import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.support.daohelper.core.PageResult;
import cn.don9cn.blog.support.operaresult.core.OperaResult;
import cn.don9cn.blog.support.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.bussiness.articlecomment.interf.ArticleCommentService;
import cn.don9cn.blog.util.MyStringUtil;
import cn.don9cn.blog.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author: liuxindong
 * @Description: 文章留言service接口实现类
 * @Create: 2017/10/10 10:24
 * @Modify:
 */
@SuppressWarnings({"Since15", "Duplicates"})
@Service
@Transactional
public class ArticleCommentServiceImpl implements ArticleCommentService {

	@Autowired
	private ArticleCommentDao articleCommentDao;

	@Autowired
	private ArticleDao articleDao;

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseInsert(ArticleComment entity) {
		String code = UuidUtil.getUuid();
		entity.setCode(code);
		//保存当前节点
		OptionalInt optional = articleCommentDao.baseInsert(entity);
		optional.ifPresent(x -> {
			//更新父节点
			if(!entity.getParent().equals("ROOT")){
				articleCommentDao.updateParentForPush(entity.getParent(),code);
			}
		});
		OperaResult result = OperaResultUtil.insert(optional);
		//保存成功后推送消息到用户的个人消息队列
		if(result.isSuccess()){
			Optional<Article> article = articleDao.baseFindById(entity.getArticleCode());
			article.ifPresent(article1 -> {
				CommonMqMessage message = new CommonMqMessage();
				message.setTitle("您有新留言!");
				message.setContent("您的文章 《"+article1.getTitle()+"》 收到一条来自 ["+ entity.getNickname() +"] 的新留言!");
				message.setLink("/loadArticle?articleCode="+article1.getCode());
				MqManager.submit(new MqRegisterMessage(MqDestinationType.QUEUE,
						"queue-user-" + article1.getAuthor(),message));
			});
		}
		return result;
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseInsertBatch(List<ArticleComment> list) {
		return OperaResultUtil.insertBatch(articleCommentDao.baseInsertBatch(list));
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseUpdate(ArticleComment entity) {
		return OperaResultUtil.update(articleCommentDao.baseUpdate(entity));
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseDeleteById(String id) {
		// 先删除节点
		Optional<List<ArticleComment>> removeNodes = articleCommentDao.removeNode(id);
		if(removeNodes.isPresent()){
			List<ArticleComment> list = removeNodes.get();
			list.forEach(comment -> {
				// 级联删除其子节点
				comment.getReplyCodes().forEach(code -> articleCommentDao.baseDeleteById(code));
				// 更新父节点
				articleCommentDao.updateParentForPull(comment.getParent(),comment.getCode());
			});
			return OperaResultUtil.deleteBatch(OptionalInt.of(list.size()));
		}else{
			return OperaResultUtil.deleteBatch(OptionalInt.empty());
		}
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			// 先删除选中的节点
			Optional<List<ArticleComment>> removeNodes = articleCommentDao.removeNodes(codesList);
			if(removeNodes.isPresent()){
				List<ArticleComment> list = removeNodes.get();
				list.forEach(comment -> {
					// 级联删除其子节点
					comment.getReplyCodes().forEach(code -> articleCommentDao.baseDeleteById(code));
					// 更新父节点
					articleCommentDao.updateParentForPull(comment.getParent(),comment.getCode());
				});
				return OperaResultUtil.deleteBatch(OptionalInt.of(list.size()));
			}else{
				return OperaResultUtil.deleteBatch(OptionalInt.empty());
			}
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult baseFindById(String id) {
		return OperaResultUtil.findOne(articleCommentDao.baseFindById(id));
	}

	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult baseFindAll() {
		return OperaResultUtil.findAll(articleCommentDao.baseFindAll());
	}

	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult baseFindListByParams(ArticleComment entity) {
		return OperaResultUtil.findListByParams(articleCommentDao.baseFindListByParams(entity));
	}

	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult baseFindByPage(PageResult<ArticleComment> pageResult) {
		return OperaResultUtil.findPage(articleCommentDao.baseFindByPage(pageResult));
	}

	/**
	 * 构造文章分类树(支持多级菜单)
	 * @return
	 */
	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult getTree(String articleCode) {

		Optional<List<ArticleComment>> listOptional = articleCommentDao.findListByArticleCode(articleCode);
		if(listOptional.isPresent()){
			List<ArticleComment> comments = listOptional.get();
			Map<String,ArticleComment> tempMap = new HashMap<>();
			comments.forEach(comment -> tempMap.put(comment.getCode(),comment));
			comments.forEach(comment -> comment.getReplyCodes().forEach(childCode -> {
				if(tempMap.get(childCode)!=null){
					comment.addReply(tempMap.get(childCode));
				}
            }));
			List<ArticleComment> classifyList = comments.stream()
															.filter(comment -> comment.getParent().equals("ROOT"))
															.collect(Collectors.toList());
			return OperaResultUtil.findAll(Optional.ofNullable(classifyList));
		}
		return OperaResultUtil.findAll(Optional.empty());

	}


}
