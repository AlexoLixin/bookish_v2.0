package cn.don9cn.blog.action.bussiness.articleclassify;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.operation.core.OperaResult;
import cn.don9cn.blog.plugins.operation.util.OperaResultUtil;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.articleclassify.interf.ArticleClassifyService;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping(value = "/bussiness")
public class ArticleClassifyAction extends BaseAction<ArticleClassify> {
	
    private static Logger logger = Logger.getLogger(ArticleClassifyAction.class);
	
	@Autowired
	private ArticleClassifyService articleClassifyService;

	@Override
	@PostMapping("/articleClassify")
	protected Object baseInsert(ArticleClassify articleClassify) {
		return articleClassifyService.doSave(articleClassify);
	}

	@Override
	protected Object baseInsertBatch(List<ArticleClassify> list) {
		return null;
	}

	@Override
	@PutMapping("/articleClassify")
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
	@GetMapping("/articleClassify/{code}")
	protected Object baseFindById(@PathVariable String code) {
		return articleClassifyService.baseFindById(code);
	}

	@Override
	@GetMapping("/articleClassify/all")
	protected Object baseFindAll() {
		return articleClassifyService.baseFindAll();
	}

	@Override
	@GetMapping("/articleClassify/list")
	protected Object baseFindListByParams(ArticleClassify articleClassify) {
		return articleClassifyService.baseFindListByParams(articleClassify);
	}

	@Override
	@GetMapping("/articleClassify/page")
	protected Object baseFindByPage(int page,int limit,String startTime,String endTime,String orderBy,ArticleClassify articleClassify) {
		return articleClassifyService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,articleClassify));
	}

	/**
	 * 删除分类
	 * @param codes
	 * @param levels
	 * @return
	 */
	@DeleteMapping("/articleClassify")
	protected Object doRemove(String codes,String levels) {
		return articleClassifyService.doRemove(codes,levels);
	}

	/**
	 * 获取分类下拉列表
	 * @return
	 */
	@GetMapping(value = "/articleClassify/selectOptions")
	public Object doGetSelectOptions() {
		return articleClassifyService.doGetSelectOptions();
	}

	/**
	 * 获取分类树
	 * @return
	 */
	@GetMapping(value = "/articleClassify/tree")
	public Object getTree() {
		return articleClassifyService.getTree();
	}
}
