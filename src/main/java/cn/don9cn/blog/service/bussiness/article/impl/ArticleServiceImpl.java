package cn.don9cn.blog.service.bussiness.article.impl;

import cn.don9cn.blog.dao.bussiness.article.impl.ArticleAndFileDaoImpl;
import cn.don9cn.blog.dao.bussiness.article.impl.ArticleDaoImpl;
import cn.don9cn.blog.dao.bussiness.article.interf.ArticleAndFileDao;
import cn.don9cn.blog.dao.bussiness.article.interf.ArticleDao;
import cn.don9cn.blog.dao.bussiness.articleclassify.impl.ArticleClassifyDaoImpl;
import cn.don9cn.blog.dao.bussiness.articleclassify.interf.ArticleClassifyDao;
import cn.don9cn.blog.dao.system.file.impl.UploadFileDaoImpl;
import cn.don9cn.blog.dao.system.file.interf.UploadFileDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
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
	private ArticleDao articleDao;

	@Autowired
	private ArticleClassifyDao articleClassifyDao;

	@Autowired
	private UploadFileDao uploadFileDao;

	@Autowired
	private ArticleAndFileDao articleAndFileDao;

	@Override
	public OperaResult baseInsert(Article entity) {
		entity.setCode(UuidUtil.getUuid());
		entity.setAuthor("test");
		if(StringUtils.isNotBlank(entity.getFiles())){
			articleAndFileDao.insertBatch(entity);
		}
		return OperaResultUtil.baseInsert(articleDao.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<Article> list) {
		return OperaResultUtil.baseInsertBatch(articleDao.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(Article entity) {
		articleAndFileDao.deleteByArticleCode(entity.getCode());
		if(StringUtils.isNotBlank(entity.getFiles())){
			articleAndFileDao.insertBatch(entity);
		}
		return OperaResultUtil.baseUpdate(articleDao.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		articleAndFileDao.deleteByArticleCode(id);
		return OperaResultUtil.baseRemove(articleDao.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			articleAndFileDao.deleteByArticleCodes(codesList);
			return OperaResultUtil.baseRemoveBatch(articleDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		Optional<Article> article = articleDao.baseFindById(id);
		article.ifPresent(a -> {
			if(StringUtils.isNotBlank(a.getFiles())){
				uploadFileDao.findListInCodes(MyStringUtil.codesStr2List(a.getFiles())).ifPresent(a::setFilesList);
			}
		});
		return OperaResultUtil.baseFindOne(article);
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.baseFindAll(articleDao.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(Article entity) {
		return OperaResultUtil.baseFindListByParams(articleDao.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<Article> pageResult) {
		Optional<PageResult<Article>> resultOptional = articleDao.baseFindByPage(pageResult);
		resultOptional.ifPresent(pageResult1 -> pageResult1.getRows().forEach(article -> {
            Optional<ArticleClassify> articleClassify = articleClassifyDao.baseFindById(article.getClassify());
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
