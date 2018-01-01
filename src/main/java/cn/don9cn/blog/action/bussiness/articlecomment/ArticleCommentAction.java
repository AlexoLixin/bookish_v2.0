package cn.don9cn.blog.action.bussiness.articlecomment;

import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.service.bussiness.articlecomment.interf.ArticleCommentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: 文章评论action
 * @Create: 2017/11/15 16:43
 * @Modify:
 */
@RestController
@RequestMapping(value = "/bussiness/articleComment")
public class ArticleCommentAction extends BaseAction<ArticleComment> {
	
    private static Logger logger = Logger.getLogger(ArticleCommentAction.class);

	@Autowired
	private ArticleCommentService articleCommentService;


	@Override
	@PostMapping({"","/insert/public"})
	protected Object baseInsert(ArticleComment articleComment) {
		return articleCommentService.baseInsert(articleComment);
	}

	@Override
	protected Object baseInsertBatch(List<ArticleComment> list) {
		return null;
	}

	@Override
	protected Object baseUpdate(ArticleComment articleComment) {
		return null;
	}

	@Override
	@DeleteMapping
	protected Object baseRemove(String code) {
		return articleCommentService.baseDeleteById(code);
	}

	@Override
	@DeleteMapping("/batch")
	protected Object baseRemoveBatch(String codes) {
		return articleCommentService.baseDeleteBatch(codes);
	}

	@Override
	protected Object baseFindById(String code) {
		return null;
	}

	@Override
	protected Object baseFindAll() {
		return null;
	}

	@Override
	protected Object baseFindListByParams(ArticleComment articleComment) {
		return null;
	}

	@Override
	@GetMapping("/page")
	protected Object baseFindByPage(int page,int limit,String startTime,String endTime,String orderBy,ArticleComment articleComment) {
		return articleCommentService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,articleComment));
	}

	/**
	 * 获取留言树
	 * @return
	 */
	@GetMapping("/tree/public")
	public Object getTree(String articleCode) {
		return articleCommentService.getTree(articleCode);
	}
}
