package cn.don9cn.blog.action.bussiness.subscribe;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.bussiness.subscribe.interf.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Don9
 * @create 2017-12-27-13:18
 **/
@RestController
@RequestMapping(value = "/bussiness/subscribe")
public class SubscribeAction extends BaseAction<SubscribeInfo>{

    @Autowired
    private SubscribeService subscribeService;

    @Override
    @PostMapping("/public")
    protected Object baseInsert(SubscribeInfo subscribeInfo) {
        return subscribeService.baseInsert(subscribeInfo);
    }

    @Override
    protected Object baseInsertBatch(List<SubscribeInfo> list) {
        return null;
    }

    @Override
    protected Object baseUpdate(SubscribeInfo subscribeInfo) {
        return null;
    }

    @Override
    protected Object baseRemove(String code) {
        return null;
    }

    @Override
    protected Object baseRemoveBatch(String codes) {
        return null;
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
    @GetMapping("/list")
    protected Object baseFindListByParams(SubscribeInfo subscribeInfo) {
        return subscribeService.baseFindListByParams(subscribeInfo);
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SubscribeInfo subscribeInfo) {
        return subscribeService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,subscribeInfo));
    }

    @DeleteMapping("/public")
    public Object delete(String email,String author){
        return subscribeService.delete(email, author);
    }



}
