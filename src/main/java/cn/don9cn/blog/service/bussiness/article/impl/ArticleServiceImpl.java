package cn.don9cn.blog.service.bussiness.article.impl;

import cn.don9cn.blog.dao.bussiness.article.ArticleDaoImpl;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.article.interf.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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
	private ArticleDaoImpl articleDaoImpl;


	@Override
	public Optional<Integer> insert(Article entity) {
		return articleDaoImpl.insert(entity);
	}

	@Override
	public Optional<Integer> insertBatch(List<Article> list) {
		return articleDaoImpl.insertBatch(list);
	}

	@Override
	public Optional<Integer> update(Article entity) {
		return articleDaoImpl.update(entity);
	}

	@Override
	public Optional<Integer> deleteById(String id) {
		return articleDaoImpl.deleteById(id);
	}

	@Override
	public Optional<Integer> deleteBatch(List<String> list) {
		return articleDaoImpl.deleteBatch(list);
	}

	@Override
	public Optional<Article> findById(String id) {
		return articleDaoImpl.findById(id);
	}

	@Override
	public Optional<List<Article>> findAll() {
		return articleDaoImpl.findAll();
	}

	@Override
	public Optional<List<Article>> findListByParams(Article entity) {
		return articleDaoImpl.findListByParams(entity);
	}

	@Override
	public Optional<PageResult<Article>> findByPage(PageParamsBean<Article> pageParamsBean) {
		return articleDaoImpl.findByPage(pageParamsBean);
	}

	@Override
	public Optional<Integer> doRemoveByUser(String code) {
		return null;
	}

	@Override
	public Optional<Integer> doUpdateByUser(Article article) {
		return null;
	}

	@Override
	public Optional<PageResult<Article>> doFindByPageByUser(PageParamsBean<Article> pageParamsBean) {
		return null;
	}
}
