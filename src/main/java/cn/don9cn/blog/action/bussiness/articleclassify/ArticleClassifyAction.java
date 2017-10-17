package cn.don9cn.blog.action.bussiness.articleclassify;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.articleclassify.interf.ArticleClassifyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/** 
 * @Title: ArticleClassifyAction.java
 * @Package cn.don9.action.article
 * @Description: ArticleClassify模块action
 * @author liuxindong
 * @createDate 2017-08-11 16:31:11
 */
@RestController
@RequestMapping(value = "/bussiness/articleClassify")
public class ArticleClassifyAction extends BaseAction<ArticleClassify> {
	
    private static Logger logger = Logger.getLogger(ArticleClassifyAction.class);
	
	@Autowired
	private ArticleClassifyService articleClassifyService;

	@Override
	@PostMapping
	protected Object baseInsert(ArticleClassify articleClassify) {
		return articleClassifyService.doSave(articleClassify);
	}

	@Override
	protected Object baseInsertBatch(List<ArticleClassify> list) {
		return null;
	}

	@Override
	@PutMapping
	protected Object baseUpdate(ArticleClassify articleClassify) {
		return articleClassifyService.baseUpdate(articleClassify);
	}

	@Override
	protected Object baseRemove(String codes) {
		return null;
	}

	@Override
	protected Object baseRemoveBatch(String codes) {
		return null;
	}

	@Override
	@GetMapping
	protected Object baseFindById(String code) {
		return articleClassifyService.baseFindById(code);
	}

	@Override
	@GetMapping("/all")
	protected Object baseFindAll() {
		return articleClassifyService.baseFindAll();
	}

	@Override
	@GetMapping("/list")
	protected Object baseFindListByParams(ArticleClassify articleClassify) {
		return articleClassifyService.baseFindListByParams(articleClassify);
	}

	@Override
	@GetMapping("/page")
	protected Object baseFindByPage(int page,int limit,String startTime,String endTime,String orderBy,ArticleClassify articleClassify) {
		return articleClassifyService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,articleClassify));
	}

	/**
	 * 删除分类
	 * @param codes
	 * @param levels
	 * @return
	 */
	@DeleteMapping
	protected Object doRemove(String codes,String levels) {
		return articleClassifyService.doRemove(codes,levels);
	}

	/**
	 * 获取分类下拉列表
	 * @return
	 */
	@GetMapping("/selectOptions")
	public Object doGetSelectOptions() {
		return articleClassifyService.doGetSelectOptions();
	}

	/**
	 * 获取分类树
	 * @return
	 */
	@GetMapping("/tree")
	public Object getTree() {
		return articleClassifyService.getTree();
	}
}
