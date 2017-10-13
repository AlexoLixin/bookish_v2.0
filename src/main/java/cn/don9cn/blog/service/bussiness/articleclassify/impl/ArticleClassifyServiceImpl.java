package cn.don9cn.blog.service.bussiness.articleclassify.impl;

import cn.don9cn.blog.dao.bussiness.articleclassify.ArticleClassifyDaoImpl;
import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.articleclassify.interf.ArticleClassifyService;
import cn.don9cn.blog.support.vue.VueSelectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
	private ArticleClassifyDaoImpl articleClassifyDaoImpl;


	@Override
	public OptionalInt baseInsert(ArticleClassify entity) {
		entity.setLeaf("Y");
		return articleClassifyDaoImpl.baseInsert(entity);
	}

	@Override
	public OptionalInt baseInsertBatch(List<ArticleClassify> list) {
		return articleClassifyDaoImpl.baseInsertBatch(list);
	}

	@Override
	public OptionalInt baseUpdate(ArticleClassify entity) {
		return articleClassifyDaoImpl.baseUpdate(entity);
	}

	@Override
	public OptionalInt baseDeleteById(String id) {
		return articleClassifyDaoImpl.baseDeleteById(id);
	}

	@Override
	public OptionalInt baseDeleteBatch(List<String> list) {
		return articleClassifyDaoImpl.baseDeleteBatch(list);
	}

	@Override
	public Optional<ArticleClassify> baseFindById(String id) {
		return articleClassifyDaoImpl.baseFindById(id);
	}

	@Override
	public Optional<List<ArticleClassify>> baseFindAll() {
		return articleClassifyDaoImpl.baseFindAll();
	}

	@Override
	public Optional<List<ArticleClassify>> baseFindListByParams(ArticleClassify entity) {
		return articleClassifyDaoImpl.baseFindListByParams(entity);
	}

	@Override
	public Optional<PageResult<ArticleClassify>> baseFindByPage(PageResult<ArticleClassify> pageResult) {
		return articleClassifyDaoImpl.baseFindByPage(pageResult);
	}

	/**
	 * 添加分类
	 * @param articleClassify
	 * @return
	 */
	@Override
	public OptionalInt doSave(ArticleClassify articleClassify) {
		//将当前节点设置为叶子节点
		articleClassify.setLeaf("Y");
		//保存当前节点
		OptionalInt optional = articleClassifyDaoImpl.baseInsert(articleClassify);
		//更新父节点为非叶子节点
		if(!articleClassify.getParent().equals("ROOT")){
			articleClassifyDaoImpl.baseUpdate(new ArticleClassify(articleClassify.getParent(),"N"));
		}
		return optional;
	}

	/**
	 * 构造文章分类树(支持多级菜单)
	 * @return
	 */
	@Override
	public Optional<List<ArticleClassify>> getTree() {

		Optional<List<ArticleClassify>> listOptional = articleClassifyDaoImpl.baseFindAll();

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
			return Optional.ofNullable(result.stream()
					.sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel())))
					.collect(Collectors.toList()));
		}else{
			return Optional.of(new ArrayList<>());
		}

	}

	/**
	 * 获取下拉分类
	 * @return
	 */
	@Override
	public Optional<List<VueSelectOption>> doGetSelectOptions() {
		List<ArticleClassify> allClassifies = articleClassifyDaoImpl.baseFindAll().get();
		Map<String, List<ArticleClassify>> map = allClassifies.stream().collect(Collectors.groupingBy(ArticleClassify::getCode));
		List<VueSelectOption> result = allClassifies.stream().filter(t -> t.getLeaf().equals("Y")&&!t.getParent().equals("ROOT"))
				.map(t -> new VueSelectOption("[" + map.get(t.getParent()).get(0).getName() + "] - " + t.getName(), t.getCode(), t.getLevel()))
				.sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel())))
				.collect(Collectors.toList());
		return Optional.ofNullable(result);
	}

	/**
	 * 删除分类
	 * @param codesList
	 * @param levelsList
	 * @return
	 */
	@Override
	public OptionalInt doRemove(List<String> codesList, List<String> levelsList) {
		//删除当前分类
		OptionalInt optional_1 = articleClassifyDaoImpl.baseDeleteBatch(codesList);
		//级联删除分类
		OptionalInt optional_2 = deleteCascade(levelsList);
		//更新节点状态
		updateLeaf();
		return OptionalInt.of(optional_1.orElse(0)+optional_2.orElse(0));
	}

	/**
	 * 级联删除分类
	 * @param levelsList
	 */
	private OptionalInt deleteCascade(List<String> levelsList) {
		int x = 0;
		for(String level:levelsList){
			int y = articleClassifyDaoImpl.deleteCascade(level).getAsInt();
			x += y;
		}
		return OptionalInt.of(x);
	}

	/**
	 * 更新节点状态
	 * @return
	 */
	private OptionalInt updateLeaf() {
		List<ArticleClassify> allClassifies = articleClassifyDaoImpl.baseFindAll().get();
		List<String> allCodes = allClassifies.stream().map(ArticleClassify::getCode).collect(Collectors.toList());
		allCodes.add("ROOT");
		List<String> temp = new ArrayList<>();
		allClassifies.forEach(classify -> {
			if(!allCodes.contains(classify.getParent())){
				temp.add(classify.getParent());
			}
		});
		return articleClassifyDaoImpl.updateLeaf(temp);
	}
}
