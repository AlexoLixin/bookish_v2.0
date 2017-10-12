package cn.don9cn.blog.service.bussiness.articleclassify.impl;

import cn.don9cn.blog.dao.bussiness.articleclassify.ArticleClassifyDaoImpl;
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
	public Optional<Integer> insert(ArticleClassify entity) {
		return articleClassifyDaoImpl.insert(entity);
	}

	@Override
	public Optional<Integer> insertBatch(List<ArticleClassify> list) {
		return articleClassifyDaoImpl.insertBatch(list);
	}

	@Override
	public Optional<Integer> update(ArticleClassify entity) {
		return articleClassifyDaoImpl.update(entity);
	}

	@Override
	public Optional<Integer> deleteById(String id) {
		return articleClassifyDaoImpl.deleteById(id);
	}

	@Override
	public Optional<Integer> deleteBatch(List<String> list) {
		return articleClassifyDaoImpl.deleteBatch(list);
	}

	@Override
	public Optional<ArticleClassify> findById(String id) {
		return articleClassifyDaoImpl.findById(id);
	}

	@Override
	public Optional<List<ArticleClassify>> findAll() {
		return articleClassifyDaoImpl.findAll();
	}

	@Override
	public Optional<List<ArticleClassify>> findListByParams(ArticleClassify entity) {
		return articleClassifyDaoImpl.findListByParams(entity);
	}

	@Override
	public Optional<PageResult<ArticleClassify>> findByPage(PageParamsBean<ArticleClassify> pageParamsBean) {
		return articleClassifyDaoImpl.findByPage(pageParamsBean);
	}

	/**
	 * 添加分类
	 * @param articleClassify
	 * @return
	 */
	@Override
	public Optional<Integer> doSave(ArticleClassify articleClassify) {
		//将当前节点设置为叶子节点
		articleClassify.setLeaf("Y");
		//保存当前节点
		Optional<Integer> optional = articleClassifyDaoImpl.insert(articleClassify);
		//更新父节点为非叶子节点
		if(!articleClassify.getParent().equals("ROOT")){
			articleClassifyDaoImpl.update(new ArticleClassify(articleClassify.getParent(),"N"));
		}
		return optional;
	}

	/**
	 * 构造文章分类树(支持多级菜单)
	 * @return
	 */
	@Override
	public Optional<List<ArticleClassify>> getTree() {
		List<ArticleClassify> all = articleClassifyDaoImpl.findAll().get();
		List<ArticleClassify> temp = new ArrayList<>();
		// 对所有节点按照父子关系进行重新组装
		List<ArticleClassify> result = all.stream().filter(t -> t.getLeaf().equals("N")).map(t -> {
			System.out.println(t.getCode());
			List<ArticleClassify> children = all.stream()
					.filter(sub -> sub.getParent().equals(t.getCode()))
					.sorted(Comparator.comparing(sub -> Integer.parseInt(sub.getLevel())))
					.collect(Collectors.toList());
			t.setChildren(children);
			temp.add(t);
			temp.addAll(children);
			return t;
		}).filter(t -> t.getParent().equals("ROOT"))
				.sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel()))).collect(Collectors.toList());
		all.removeAll(temp);
		result.addAll(all);
		return Optional.ofNullable(result.stream().sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel()))).collect(Collectors.toList()));
	}

	/**
	 * 获取下拉分类
	 * @return
	 */
	@Override
	public Optional<List<VueSelectOption>> doGetSelectOptions() {
		List<ArticleClassify> allClassifies = articleClassifyDaoImpl.findAll().get();
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
	public Optional<Integer> doRemove(List<String> codesList, List<String> levelsList) {
		//删除当前分类
		Optional<Integer> optional_1 = articleClassifyDaoImpl.deleteBatch(codesList);
		//级联删除分类
		Optional<Integer> optional_2 = deleteCascade(levelsList);
		//更新节点状态
		updateLeaf();

		return Optional.of(optional_1.orElse(0)+optional_2.orElse(0));
	}

	/**
	 * 级联删除分类
	 * @param levelsList
	 */
	private Optional<Integer> deleteCascade(List<String> levelsList) {
		int x = 0;
		for(String level:levelsList){
			int y = articleClassifyDaoImpl.deleteCascade(level).get();
			x += y;
		}
		return Optional.ofNullable(x);
	}

	/**
	 * 更新节点状态
	 * @return
	 */
	private Optional<Integer> updateLeaf() {
		List<ArticleClassify> allClassifies = articleClassifyDaoImpl.findAll().get();
		List<String> allCodes = new ArrayList<String>();
		allClassifies.stream().forEach(classify -> allCodes.add(classify.getCode()));
		allCodes.add("ROOT");
		allClassifies.stream().forEach(classify -> {
			if(allCodes.contains(classify.getParent())){
				allCodes.remove(classify.getParent());
			}
		});
		return articleClassifyDaoImpl.updateLeaf(allCodes);
	}
}
