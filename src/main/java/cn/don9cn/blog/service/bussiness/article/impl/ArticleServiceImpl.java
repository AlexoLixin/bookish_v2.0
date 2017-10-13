package cn.don9cn.blog.service.bussiness.article.impl;

import cn.don9cn.blog.dao.bussiness.article.ArticleDaoImpl;
import cn.don9cn.blog.dao.system.file.UploadFileDaoImpl;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.article.interf.ArticleService;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	private ArticleDaoImpl articleDaoImpl;

	@Autowired
	private UploadFileDaoImpl uploadFileDaoImpl;


	@Override
	public OptionalInt baseInsert(Article entity) {
		entity.setAuthor("test");
		return articleDaoImpl.baseInsert(entity);
	}

	@Override
	public OptionalInt baseInsertBatch(List<Article> list) {
		return articleDaoImpl.baseInsertBatch(list);
	}

	@Override
	public OptionalInt baseUpdate(Article entity) {
		return articleDaoImpl.baseUpdate(entity);
	}

	@Override
	public OptionalInt baseDeleteById(String id) {
		return articleDaoImpl.baseDeleteById(id);
	}

	@Override
	public OptionalInt baseDeleteBatch(List<String> list) {
		return articleDaoImpl.baseDeleteBatch(list);
	}

	@Override
	public Optional<Article> baseFindById(String id) {
		Optional<Article> article = articleDaoImpl.baseFindById(id);
		article.ifPresent(a -> {
			if(StringUtils.isNotBlank(a.getFiles())){
				uploadFileDaoImpl.findListInCodes(MyStringUtil.codesStr2List(a.getFiles())).ifPresent(a::setFilesList);
			}
		});
		return article;
	}

	@Override
	public Optional<List<Article>> baseFindAll() {
		return articleDaoImpl.baseFindAll();
	}

	@Override
	public Optional<List<Article>> baseFindListByParams(Article entity) {
		return articleDaoImpl.baseFindListByParams(entity);
	}

	@Override
	public Optional<PageResult<Article>> baseFindByPage(PageParamsBean<Article> pageParamsBean) {
		return articleDaoImpl.baseFindByPage(pageParamsBean);
	}

	/**
	 * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
	 * @param code
	 * @return
	 */
	@Override
	public OptionalInt doRemoveByUser(String code) {
		return null;
	}

	/**
	 * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
	 * @param article
	 * @return
	 */
	@Override
	public OptionalInt doUpdateByUser(Article article) {
		return null;
	}

	/**
	 * 个人中心-普通获取文章列表(只能获取自己发布的文章列表,防止其他用户数据被恶意篡改)
	 * @param pageParamsBean
	 * @return
	 */
	@Override
	public Optional<PageResult<Article>> doFindByPageByUser(PageParamsBean<Article> pageParamsBean) {
		return null;
	}
}
