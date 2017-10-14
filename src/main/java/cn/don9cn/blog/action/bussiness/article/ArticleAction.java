package cn.don9cn.blog.action.bussiness.article;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;
import cn.don9cn.blog.plugins.actionmsg.util.ActionMsgUtil;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
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
    protected Object baseSave(Article article) {
        return ActionMsgUtil.baseSave(articleService.baseInsert(article));
    }

    @Override
    protected Object baseSaveBatch(List<Article> list) {
        return null;
    }

    @Override
    @PutMapping("/article")
    protected Object baseUpdate(Article article) {
        return ActionMsgUtil.baseUpdate(articleService.baseUpdate(article));
    }

    @Override
    @DeleteMapping("/article")
    protected Object baseRemove(String code) {
        return ActionMsgUtil.baseRemove(articleService.baseDeleteById(code));
    }

    @Override
    @DeleteMapping("/article/batch")
    protected Object baseRemoveBatch(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return ActionMsgUtil.baseRemoveBatch(articleService.baseDeleteBatch(codesList));
        }else{
            return new ActionMsg(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    @GetMapping("/article/{code}")
    protected Object baseFindById(@PathVariable String code) {
        return ActionMsgUtil.baseFindById(articleService.baseFindById(code));
    }

    @Override
    @GetMapping("/article/all")
    protected Object baseFindAll() {
        return ActionMsgUtil.baseFindAll(articleService.baseFindAll());
    }

    @Override
    @GetMapping("/article/list")
    protected Object baseFindListByParams(Article article) {
        return ActionMsgUtil.baseFindListByParams(articleService.baseFindListByParams(article));
    }

    @Override
    @GetMapping("/article/page")
    protected Object baseFindByPage(int page, int limit,String startTime,String endTime, String orderBy, Article article) {
        return ActionMsgUtil.baseFindByPage(articleService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,article)));
    }

    /**
     * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
     * @param article
     * @return
     */
    @PutMapping("/article/byUser")
    public Object doUpdateByUser(Article article){
        return ActionMsgUtil.baseUpdate(articleService.doUpdateByUser(article));
    }

    /**
     * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
     * @param code
     * @return
     */
    @DeleteMapping("/article/byUser")
    public Object doRemoveByUser(String code) {
        return ActionMsgUtil.baseRemove(articleService.doRemoveByUser(code));
    }
}
