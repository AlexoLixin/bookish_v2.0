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
@RequestMapping(value = "/bussiness")
public class ArticleAction extends BaseAction<Article> {

    @Autowired
    private ArticleService articleService;


    @Override
    @PostMapping("/article")
    protected Object doSave(Article article) {
        return ActionMsgUtil.doSave(articleService.insert(article));
    }

    @Override
    @PutMapping("/article")
    protected Object doUpdate(Article article) {
        return ActionMsgUtil.doUpdate(articleService.update(article));
    }

    @Override
    @DeleteMapping("/article")
    protected Object doRemove(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return ActionMsgUtil.doRemove(articleService.deleteBatch(codesList));
        }else{
            return new ActionMsg(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    @GetMapping("/article/{code}")
    protected Object doFindById(@PathVariable String code) {
        return ActionMsgUtil.doFindById(articleService.findById(code));
    }

    @Override
    @GetMapping("/article/all")
    protected Object doFindAll() {
        return ActionMsgUtil.doFindAll(articleService.findAll());
    }

    @Override
    @GetMapping("/article/list")
    protected Object doFindListByParams(Article article) {
        return ActionMsgUtil.doFindListByParams(articleService.findListByParams(article));
    }

    @Override
    @GetMapping("/article/page")
    protected Object doFindByPage(int page, int limit, Article article) {
        return ActionMsgUtil.doFindByPage(articleService.findByPage(new PageParamsBean<>(page, limit,  article)));
    }

    /**
     * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
     * @param article
     * @return
     */
    @PutMapping("/article/byUser")
    public Object doUpdateByUser(Article article){
        return ActionMsgUtil.doUpdate(articleService.doUpdateByUser(article));
    }

    /**
     * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
     * @param code
     * @return
     */
    @DeleteMapping("/article/byUser")
    public Object doRemoveByUser(String code) {
        return ActionMsgUtil.doRemove(articleService.doRemoveByUser(code));
    }
}
