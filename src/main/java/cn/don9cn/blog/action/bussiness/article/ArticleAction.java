package cn.don9cn.blog.action.bussiness.article;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;
import cn.don9cn.blog.plugins.actionmsg.util.ActionMsgUtil;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.service.bussiness.article.interf.ArticleService;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: Article Action
 * @Create: 2017/10/9 15:24
 * @Modify:
 */
@RestController
@RequestMapping(value = "/bussiness/article")
public class ArticleAction extends BaseAction<Article> {

    @Autowired
    private ArticleService articleService;


    @Override
    @PostMapping("/doSave")
    protected Object doSave(Article article) {
        return ActionMsgUtil.doSave(articleService.insert(article));
    }

    @Override
    @PostMapping("/doUpdate")
    protected Object doUpdate(Article article) {
        return ActionMsgUtil.doUpdate(articleService.update(article));
    }

    @Override
    @PostMapping("/doRemove")
    protected Object doRemove(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return ActionMsgUtil.doRemove(articleService.deleteBatch(codesList));
        }else{
            return new ActionMsg(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    @GetMapping("/doFindById/{code}")
    protected Object doFindById(@PathVariable String code) {
        return ActionMsgUtil.doFindById(articleService.findById(code));
    }

    @Override
    @GetMapping("/doFindAll")
    protected Object doFindAll(Article article) {
        return ActionMsgUtil.doFindAll(articleService.findAll());
    }

    @Override
    @GetMapping("/doFindListByParams")
    protected Object doFindListByParams(Article article) {
        return ActionMsgUtil.doFindListByParams(articleService.findListByParams(article));
    }

    @Override
    @GetMapping("/doFindByPage")
    protected Object doFindByPage(int page, int limit, Article article) {
        return ActionMsgUtil.doFindByPage(articleService.findByPage(new PageParamsBean<>(page, limit,  article)));
    }
}
