package cn.don9cn.blog.action.bussiness.article;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.article.interf.ArticleService;
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
    @PostMapping
    protected Object baseInsert(Article article) {
        return articleService.baseInsert(article);
    }

    @Override
    protected Object baseInsertBatch(List<Article> list) {
        return null;
    }

    @Override
    @PutMapping
    protected Object baseUpdate(Article article) {
        return articleService.baseUpdate(article);
    }

    @Override
    @DeleteMapping
    protected Object baseRemove(String code) {
        return articleService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected Object baseRemoveBatch(String codes) {
        return articleService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected Object baseFindById(String code) {
        return articleService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected Object baseFindAll() {
        return articleService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected Object baseFindListByParams(Article article) {
        return articleService.baseFindListByParams(article);
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit,String startTime,String endTime, String orderBy, Article article) {
        return articleService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,article));
    }

    /**
     * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
     * @param article
     * @return
     */
    @PutMapping("/byUser")
    public Object doUpdateByUser(Article article){
        return articleService.doUpdateByUser(article);
    }

    /**
     * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
     * @param code
     * @return
     */
    @DeleteMapping("/byUser")
    public Object doRemoveByUser(String code) {
        return articleService.doRemoveByUser(code);
    }

    /**
     * 个人中心-普通获取文章列表(只能获取自己发布的文章列表,防止其他用户数据被恶意篡改)
     * @param pageResult
     * @return
     */
    @GetMapping("/page/byUser")
    public Object doFindByPageByUser(PageResult<Article> pageResult) {
        return articleService.doFindByPageByUser(pageResult);
    }
}
