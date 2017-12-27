package cn.don9cn.blog.service.bussiness.article.impl;

import cn.don9cn.blog.autoconfigs.activemq.constant.MqConstant;
import cn.don9cn.blog.autoconfigs.activemq.constant.MqDestinationType;
import cn.don9cn.blog.autoconfigs.activemq.core.MqManager;
import cn.don9cn.blog.autoconfigs.activemq.model.CommonMqMessage;
import cn.don9cn.blog.autoconfigs.activemq.model.MqRegisterMessage;
import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.dao.bussiness.article.interf.ArticleAndFileDao;
import cn.don9cn.blog.dao.bussiness.article.interf.ArticleDao;
import cn.don9cn.blog.dao.bussiness.articleclassify.interf.ArticleClassifyDao;
import cn.don9cn.blog.dao.system.file.interf.UploadFileDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.bussiness.article.interf.ArticleService;
import cn.don9cn.blog.util.MyStringUtil;
import cn.don9cn.blog.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * @Author: liuxindong
 * @Description: Article Service实现类
 * @Create: 2017/10/9 14:24
 * @Modify:
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private ArticleClassifyDao articleClassifyDao;

	@Autowired
	private UploadFileDao uploadFileDao;

	@Autowired
	private ArticleAndFileDao articleAndFileDao;

	@Autowired
	private MqConstant mqConstant;

	@Override
	//@CacheEvict(value = "Article",allEntries = true)
	public OperaResult baseInsert(Article entity) {
		entity.setCode(UuidUtil.getUuid());
		entity.setAuthor(MyShiroSessionUtil.getUserNameFromSession());
		if(StringUtils.isNotBlank(entity.getFiles())){
			articleAndFileDao.insertBatch(entity);
		}
		OptionalInt optionalInt = articleDao.baseInsert(entity);
		/*optionalInt.ifPresent(x -> {
			if(x>0){
				CommonMqMessage message = new CommonMqMessage();
				message.setTitle("新文章发布!");
				message.setContent(entity.getCode()+";"+entity.getAuthor());
				MqManager.submit(new MqRegisterMessage(MqDestinationType.TOPIC,mqConstant.TOPIC_MAIL_SUBSCRIBE,message));
			}
		});*/
		return OperaResultUtil.insert(optionalInt);
	}

	@Override
	//@CacheEvict(value = "Article",allEntries = true)
	public OperaResult baseInsertBatch(List<Article> list) {
		list.forEach(article -> article.setAuthor(MyShiroSessionUtil.getUserNameFromSession()));
		return OperaResultUtil.insertBatch(articleDao.baseInsertBatch(list));
	}

	@Override
	//@CacheEvict(value = "Article",allEntries = true)
	public OperaResult baseUpdate(Article entity) {
		articleAndFileDao.deleteByArticleCode(entity.getCode());
		if(StringUtils.isNotBlank(entity.getFiles())){
			articleAndFileDao.insertBatch(entity);
		}
		return OperaResultUtil.update(articleDao.baseUpdate(entity));
	}

	@Override
	//@CacheEvict(value = "Article",allEntries = true)
	public OperaResult baseDeleteById(String id) {
		articleAndFileDao.deleteByArticleCode(id);
		return OperaResultUtil.deleteOne(articleDao.baseDeleteById(id));
	}

	@Override
	//@CacheEvict(value = "Article",allEntries = true)
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			articleAndFileDao.deleteByArticleCodes(codesList);
			return OperaResultUtil.deleteBatch(articleDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	//@Cacheable(value = "Article")
	public OperaResult baseFindById(String id) {
		Optional<Article> article = articleDao.baseFindById(id);
		article.ifPresent(a -> {
			if(StringUtils.isNotBlank(a.getFiles())){
				uploadFileDao.findListInCodes(MyStringUtil.codesStr2List(a.getFiles())).ifPresent(a::setFilesList);
			}
			articleClassifyDao.baseFindById(a.getClassify()).ifPresent(articleClassify -> a.setClassifyName(articleClassify.getName()));
		});
		return OperaResultUtil.findOne(article);
	}

	@Override
	//@Cacheable(value = "Article")
	public OperaResult baseFindAll() {
		return OperaResultUtil.findAll(articleDao.baseFindAll());
	}

	@Override
	//@Cacheable(value = "Article")
	public OperaResult baseFindListByParams(Article entity) {
		return OperaResultUtil.findListByParams(articleDao.findListWithoutContent(entity));
	}

	@Override
	//@Cacheable(value = "Article")
	public OperaResult baseFindByPage(PageResult<Article> pageResult) {
		Optional<PageResult<Article>> resultOptional = articleDao.findPageWithoutContent(pageResult);
		resultOptional.ifPresent(pageResult1 -> pageResult1.getRows().forEach(article -> {
            Optional<ArticleClassify> articleClassify = articleClassifyDao.baseFindById(article.getClassify());
            articleClassify.ifPresent(articleClassify1 -> article.setClassifyName(articleClassify1.getName()));
        }));
		return OperaResultUtil.findPage(resultOptional);
	}

	/**
	 * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
	 * @param code
	 * @return
	 */
	@Override
	//@CacheEvict(value = "Article",allEntries = true)
	public OperaResult doRemoveByUser(String code) {
		return OperaResultUtil.deleteOne(articleDao.removeByUser(code,MyShiroSessionUtil.getUserCodeFromSession()));
	}

	/**
	 * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
	 * @param article
	 * @return
	 */
	@Override
	//@CacheEvict(value = "Article",allEntries = true)
	public OperaResult doUpdateByUser(Article article) {
		article.setCreateBy(MyShiroSessionUtil.getUserCodeFromSession());
		article.setModifyBy(MyShiroSessionUtil.getUserCodeFromSession());
		return OperaResultUtil.update(articleDao.updateByUser(article));
	}

	/**
	 * 个人中心-普通获取文章列表(只能获取自己发布的文章列表,防止其他用户数据被恶意篡改)
	 * @param pageResult
	 * @return
	 */
	@Override
	//@Cacheable(value = "Article")
	public OperaResult doFindByPageByUser(PageResult<Article> pageResult) {
		pageResult.getEntity().setCreateBy(MyShiroSessionUtil.getUserCodeFromSession());
		Optional<PageResult<Article>> resultOptional = articleDao.findPageWithoutContent(pageResult);
		resultOptional.ifPresent(pageResult1 -> pageResult1.getRows().forEach(article -> {
			Optional<ArticleClassify> articleClassify = articleClassifyDao.baseFindById(article.getClassify());
			articleClassify.ifPresent(articleClassify1 -> article.setClassifyName(articleClassify1.getName()));
		}));
		return OperaResultUtil.findPage(resultOptional);
	}
}
