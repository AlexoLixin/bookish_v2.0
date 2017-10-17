package cn.don9cn.blog.service.bussiness.articleclassify.impl;

import cn.don9cn.blog.dao.bussiness.articleclassify.interf.ArticleClassifyDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.bussiness.articleclassify.interf.ArticleClassifyService;
import cn.don9cn.blog.support.vue.VueSelectOption;
import cn.don9cn.blog.util.MyStringUtil;
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
@SuppressWarnings("Since15")
@Service
@Transactional
public class ArticleClassifyServiceImpl implements ArticleClassifyService {

	@Autowired
	private ArticleClassifyDao articleClassifyDao;


	@Override
	public OperaResult baseInsert(ArticleClassify entity) {
		entity.setLeaf("Y");
		return OperaResultUtil.baseInsert(articleClassifyDao.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<ArticleClassify> list) {
		return OperaResultUtil.baseInsertBatch(articleClassifyDao.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(ArticleClassify entity) {
		return OperaResultUtil.baseUpdate(articleClassifyDao.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.baseRemove(articleClassifyDao.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			return OperaResultUtil.baseRemoveBatch(articleClassifyDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		return OperaResultUtil.baseFindOne(articleClassifyDao.baseFindById(id));
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.baseFindAll(articleClassifyDao.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(ArticleClassify entity) {
		return OperaResultUtil.baseFindListByParams(articleClassifyDao.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<ArticleClassify> pageResult) {
		return OperaResultUtil.baseFindByPage(articleClassifyDao.baseFindByPage(pageResult));
	}

	/**
	 * 添加分类
	 * @param articleClassify
	 * @return
	 */
	@Override
	public OperaResult doSave(ArticleClassify articleClassify) {
		//将当前节点设置为叶子节点
		articleClassify.setLeaf("Y");
		//保存当前节点
		OptionalInt optional = articleClassifyDao.baseInsert(articleClassify);
		//更新父节点为非叶子节点
		if(!articleClassify.getParent().equals("ROOT")){
			articleClassifyDao.baseUpdate(new ArticleClassify(articleClassify.getParent(),"N"));
		}
		return OperaResultUtil.baseInsert(optional);
	}

	/**
	 * 构造文章分类树(支持多级菜单)
	 * @return
	 */
	@Override
	public OperaResult getTree() {

		Optional<List<ArticleClassify>> listOptional = articleClassifyDao.baseFindAll();
		Optional<List<ArticleClassify>> resultOptional;
		if(listOptional.isPresent()){
			List<ArticleClassify> all = listOptional.get();
			List<ArticleClassify> temp = new ArrayList<>();
			// 对所有节点按照父子关系进行重新组装
			List<ArticleClassify> result = all.stream().filter(t -> t.getLeaf().equals("N")).map(t -> {
				List<ArticleClassify> children = all.stream()
						.filter(sub -> sub.getParent().equals(t.getCode()))
						.sorted(Comparator.comparing(sub -> Integer.parseInt(sub.getLevel())))
						.collect(Collectors.toList());
				if(children!=null){
					t.setChildren(children);
					temp.addAll(children);
				}
				temp.add(t);
				return t;
			}).filter(t -> t.getParent().equals("ROOT"))
					.sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel()))).collect(Collectors.toList());
			all.removeAll(temp);
			result.addAll(all);
			resultOptional = Optional.ofNullable(result.stream()
					.sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel())))
					.collect(Collectors.toList()));
		}else{
			resultOptional =  Optional.of(new ArrayList<>());
		}
		return OperaResultUtil.baseFindAll(resultOptional);

	}

	/**
	 * 获取下拉分类
	 * @return
	 */
	@Override
	public OperaResult doGetSelectOptions() {
		List<ArticleClassify> allClassifies = articleClassifyDao.baseFindAll().get();
		Map<String, List<ArticleClassify>> map = allClassifies.stream().collect(Collectors.groupingBy(ArticleClassify::getCode));
		List<VueSelectOption> result = allClassifies.stream().filter(t -> t.getLeaf().equals("Y")&&!t.getParent().equals("ROOT"))
				.map(t -> new VueSelectOption("[" + map.get(t.getParent()).get(0).getName() + "] - " + t.getName(), t.getCode(), t.getLevel()))
				.sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel())))
				.collect(Collectors.toList());
		return OperaResultUtil.baseFindAll(Optional.ofNullable(result));
	}

	/**
	 * 删除分类
	 * @param codes
	 * @param levels
	 * @return
	 */
	@Override
	public OperaResult doRemove(String codes, String levels) {
		if(StringUtils.isNotBlank(codes) && StringUtils.isNotBlank(levels)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			List<String> levelsList = MyStringUtil.codesStr2List(levels);
			//删除当前分类
			OptionalInt optional_1 = articleClassifyDao.baseDeleteBatch(codesList);
			//级联删除分类
			OptionalInt optional_2 = deleteCascade(levelsList);
			//更新节点状态
			updateLeaf();
			return OperaResultUtil.baseRemove(OptionalInt.of(optional_1.orElse(0)+optional_2.orElse(0)));
		}else{
			return new OperaResult(false,"传入codes或者levels为空!");
		}
	}

	/**
	 * 级联删除分类
	 * @param levelsList
	 */
	private OptionalInt deleteCascade(List<String> levelsList) {
		int x = 0;
		for(String level:levelsList){
			int y = articleClassifyDao.deleteCascade(level).getAsInt();
			x += y;
		}
		return OptionalInt.of(x);
	}

	/**
	 * 更新节点状态
	 * @return
	 */
	private OptionalInt updateLeaf() {
		List<ArticleClassify> allClassifies = articleClassifyDao.baseFindAll().get();
		List<String> allCodes = allClassifies.stream().map(ArticleClassify::getCode).collect(Collectors.toList());
		allCodes.add("ROOT");
		List<String> temp = new ArrayList<>();
		allClassifies.forEach(classify -> {
			if(!allCodes.contains(classify.getParent())){
				temp.add(classify.getParent());
			}
		});
		return articleClassifyDao.updateLeaf(temp);
	}
}
