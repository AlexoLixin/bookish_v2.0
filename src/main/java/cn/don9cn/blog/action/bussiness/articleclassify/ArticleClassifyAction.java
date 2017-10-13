package cn.don9cn.blog.action.bussiness.articleclassify;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;
import cn.don9cn.blog.plugins.actionmsg.util.ActionMsgUtil;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.articleclassify.interf.ArticleClassifyService;
import cn.don9cn.blog.util.MyStringUtil;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
	protected Object baseSave(ArticleClassify articleClassify) {
		return ActionMsgUtil.baseSave(articleClassifyService.doSave(articleClassify));
	}

	@Override
	protected Object baseSaveBatch(List<ArticleClassify> list) {
		return null;
	}


	@Override
	@PutMapping("/articleClassify")
	protected Object baseUpdate(ArticleClassify articleClassify) {
		return ActionMsgUtil.baseUpdate(articleClassifyService.baseUpdate(articleClassify));
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
	protected Object baseFindById(String code) {
		return ActionMsgUtil.baseFindById(articleClassifyService.baseFindById(code));
	}

	@Override
	@GetMapping("/articleClassify/all")
	protected Object baseFindAll() {
		return ActionMsgUtil.baseFindAll(articleClassifyService.baseFindAll());
	}

	@Override
	@GetMapping("/articleClassify/list")
	protected Object baseFindListByParams(ArticleClassify articleClassify) {
		return ActionMsgUtil.baseFindListByParams(articleClassifyService.baseFindListByParams(articleClassify));
	}

	@Override
	@GetMapping("/articleClassify/page")
	protected Object baseFindByPage(int page, int limit, String orderBy, ArticleClassify articleClassify) {
		return ActionMsgUtil.baseFindByPage(articleClassifyService.baseFindByPage(new PageResult<>(page, limit, orderBy, articleClassify)));
	}

	/**
	 * 删除分类
	 * @param codes
	 * @param levels
	 * @return
	 */
	@DeleteMapping("/articleClassify")
	protected Object doRemove(String codes,String levels) {
		if(StringUtils.isNotBlank(codes) && StringUtils.isNotBlank(levels)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			List<String> levelsList = MyStringUtil.codesStr2List(levels);
			return ActionMsgUtil.baseRemove(articleClassifyService.doRemove(codesList,levelsList));
		}else{
			return new ActionMsg(false,"传入codes或者levels为空!");
		}
	}

	/**
	 * 获取分类下拉列表
	 * @return
	 */
	@GetMapping(value = "/articleClassify/selectOptions")
	public Object doGetSelectOptions() {
		return ActionMsgUtil.baseFindAll(articleClassifyService.doGetSelectOptions());
	}

	/**
	 * 获取分类树
	 * @return
	 */
	@GetMapping(value = "/articleClassify/tree")
	public Object getTree() {
		return ActionMsgUtil.baseFindAll(articleClassifyService.getTree());
	}
}
