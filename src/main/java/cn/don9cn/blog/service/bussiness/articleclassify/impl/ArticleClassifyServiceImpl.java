package cn.don9cn.blog.service.bussiness.articleclassify.impl;

import cn.don9cn.blog.dao.bussiness.articleclassify.interf.ArticleClassifyDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.support.daohelper.core.PageResult;
import cn.don9cn.blog.support.operaresult.core.OperaResult;
import cn.don9cn.blog.support.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.bussiness.articleclassify.interf.ArticleClassifyService;
import cn.don9cn.blog.support.vue.VueSelectOption;
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
 * @Description: 文章分类模块service接口实现类
 * @Create: 2017/10/10 10:24
 * @Modify:
 */
@SuppressWarnings({"Since15", "Duplicates"})
@Service
@Transactional
public class ArticleClassifyServiceImpl implements ArticleClassifyService {

	@Autowired
	private ArticleClassifyDao articleClassifyDao;


	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseInsert(ArticleClassify articleClassify) {
		String code = UuidUtil.getUuid();
		articleClassify.setCode(code);
		//保存当前节点
		OptionalInt optional = articleClassifyDao.baseInsert(articleClassify);
		optional.ifPresent(x -> {
			//更新父节点
			if(!articleClassify.getParent().equals("ROOT")){
				articleClassifyDao.updateParentForPush(articleClassify.getParent(),code);
			}
		});
		return OperaResultUtil.insert(optional);
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseInsertBatch(List<ArticleClassify> list) {
		return OperaResultUtil.insertBatch(articleClassifyDao.baseInsertBatch(list));
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseUpdate(ArticleClassify entity) {
		return OperaResultUtil.update(articleClassifyDao.update(entity));
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.deleteOne(articleClassifyDao.baseDeleteById(id));
	}

	@Override
	//@CacheEvict(value = "ArticleClassify",allEntries = true)
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			// 先删除选中的节点
			Optional<List<ArticleClassify>> removeNodes = articleClassifyDao.removeNodes(codesList);
			if(removeNodes.isPresent()){
				List<ArticleClassify> list = removeNodes.get();
				list.forEach(articleClassify -> {
					// 级联删除其子节点
					articleClassify.getChildrenCodes().forEach(code -> articleClassifyDao.baseDeleteById(code));
					// 更新父节点
					articleClassifyDao.updateParentForPull(articleClassify.getParent(),articleClassify.getCode());
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
		return OperaResultUtil.findOne(articleClassifyDao.baseFindById(id));
	}

	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult baseFindAll() {
		return OperaResultUtil.findAll(articleClassifyDao.baseFindAll());
	}

	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult baseFindListByParams(ArticleClassify entity) {
		return OperaResultUtil.findListByParams(articleClassifyDao.baseFindListByParams(entity));
	}

	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult baseFindByPage(PageResult<ArticleClassify> pageResult) {
		return OperaResultUtil.findPage(articleClassifyDao.baseFindByPage(pageResult));
	}

	/**
	 * 构造文章分类树(支持多级菜单)
	 * @return
	 */
	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult getTree() {

		Optional<List<ArticleClassify>> listOptional = articleClassifyDao.baseFindAll();
		if(listOptional.isPresent()){
			List<ArticleClassify> classifies = listOptional.get();
			Map<String,ArticleClassify> tempMap = new HashMap<>();
			classifies.forEach(classify -> tempMap.put(classify.getCode(),classify));
			classifies.forEach(classify -> classify.getChildrenCodes().forEach(childCode -> {
                classify.addChild(tempMap.get(childCode));
            }));
			List<ArticleClassify> classifyList = classifies.stream()
															.filter(articleClassify -> articleClassify.getParent().equals("ROOT"))
															.collect(Collectors.toList());
			return OperaResultUtil.findAll(Optional.ofNullable(classifyList));
		}
		return OperaResultUtil.findAll(Optional.empty());

	}

	/**
	 * 获取下拉分类
	 * @return
	 */
	@Override
	//@Cacheable(value = "ArticleClassify")
	public OperaResult doGetSelectOptions() {
		Optional<List<ArticleClassify>> allClassifies = articleClassifyDao.baseFindAll();
		List<VueSelectOption> result = new ArrayList<>();
		if(allClassifies.isPresent()){
			List<ArticleClassify> all = allClassifies.get();
			Map<String,ArticleClassify> tempMap = new HashMap<>();
			all.forEach(articleClassify -> tempMap.put(articleClassify.getCode(),articleClassify));
			all.stream().filter(articleClassify -> articleClassify.getChildrenCodes().size()>0)
						.forEach(articleClassify -> {
							articleClassify.getChildrenCodes().forEach(code -> {
								ArticleClassify classify = tempMap.get(code);
								result.add(new VueSelectOption("【 "+articleClassify.getName()+" 】 - "+classify.getName(),classify.getCode()));
							});
						});

		}
		return new OperaResult(true,"查询成功").setObj(result);
	}


}
