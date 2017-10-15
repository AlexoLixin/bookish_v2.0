package cn.don9cn.blog.service.bussiness.article.impl;

import cn.don9cn.blog.dao.bussiness.article.ArticleAndFileDaoImpl;
import cn.don9cn.blog.dao.bussiness.article.ArticleDaoImpl;
import cn.don9cn.blog.dao.bussiness.articleclassify.ArticleClassifyDaoImpl;
import cn.don9cn.blog.dao.system.file.UploadFileDaoImpl;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operation.core.OperaResult;
import cn.don9cn.blog.plugins.operation.util.OperaResultUtil;
import cn.don9cn.blog.service.bussiness.article.interf.ArticleService;
import cn.don9cn.blog.util.MyStringUtil;
import cn.don9cn.blog.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private ArticleClassifyDaoImpl articleClassifyDaoImpl;

	@Autowired
	private UploadFileDaoImpl uploadFileDaoImpl;

	@Autowired
	private ArticleAndFileDaoImpl articleAndFileDaoImpl;

	@Override
	public OperaResult baseInsert(Article entity) {
		entity.setCode(UuidUtil.getUuid());
		entity.setAuthor("test");
		if(StringUtils.isNotBlank(entity.getFiles())){
			articleAndFileDaoImpl.insertBatch(entity);
		}
		return OperaResultUtil.baseInsert(articleDaoImpl.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<Article> list) {
		return OperaResultUtil.baseInsertBatch(articleDaoImpl.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(Article entity) {
		articleAndFileDaoImpl.deleteByArticleCode(entity.getCode());
		if(StringUtils.isNotBlank(entity.getFiles())){
			articleAndFileDaoImpl.insertBatch(entity);
		}
		return OperaResultUtil.baseUpdate(articleDaoImpl.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		articleAndFileDaoImpl.deleteByArticleCode(id);
		return OperaResultUtil.baseRemove(articleDaoImpl.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			articleAndFileDaoImpl.deleteByArticleCodes(codesList);
			return OperaResultUtil.baseRemoveBatch(articleDaoImpl.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		Optional<Article> article = articleDaoImpl.baseFindById(id);
		article.ifPresent(a -> {
			if(StringUtils.isNotBlank(a.getFiles())){
				uploadFileDaoImpl.findListInCodes(MyStringUtil.codesStr2List(a.getFiles())).ifPresent(a::setFilesList);
			}
		});
		return OperaResultUtil.baseFindOne(article);
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.baseFindAll(articleDaoImpl.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(Article entity) {
		return OperaResultUtil.baseFindListByParams(articleDaoImpl.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<Article> pageResult) {
		Optional<PageResult<Article>> resultOptional = articleDaoImpl.baseFindByPage(pageResult);
		resultOptional.ifPresent(pageResult1 -> pageResult1.getRows().forEach(article -> {
            Optional<ArticleClassify> articleClassify = articleClassifyDaoImpl.baseFindById(article.getClassify());
            articleClassify.ifPresent(articleClassify1 -> article.setClassifyName(articleClassify1.getName()));
        }));
		return OperaResultUtil.baseFindByPage(resultOptional);
	}

	/**
	 * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
	 * @param code
	 * @return
	 */
	@Override
	public OperaResult doRemoveByUser(String code) {
		return null;
	}

	/**
	 * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
	 * @param article
	 * @return
	 */
	@Override
	public OperaResult doUpdateByUser(Article article) {
		return null;
	}

	/**
	 * 个人中心-普通获取文章列表(只能获取自己发布的文章列表,防止其他用户数据被恶意篡改)
	 * @param pageResult
	 * @return
	 */
	@Override
	public OperaResult doFindByPageByUser(PageResult<Article> pageResult) {
		return null;
	}
}
