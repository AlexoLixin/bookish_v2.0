package cn.don9cn.blog.service.bussiness.subscribe.impl;

import cn.don9cn.blog.dao.bussiness.subscribe.interf.SubscribeDao;
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.bussiness.subscribe.interf.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Don9
 * @create 2017-12-27-11:19
 **/
@Service
@Transactional
//@RpcService(path = "/subscribe/service")
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private SubscribeDao subscribeDao;

    @Override
    public Object baseInsert(SubscribeInfo entity) {
        return OperaResultUtil.insert(subscribeDao.baseInsert(entity));
    }

    @Override
    public Object baseInsertBatch(List<SubscribeInfo> list) {
        return null;
    }

    @Override
    public Object baseUpdate(SubscribeInfo entity) {
        return null;
    }

    @Override
    public Object baseDeleteById(String id) {
        return null;
    }

    @Override
    public Object baseDeleteBatch(String codes) {
        return null;
    }

    @Override
    public Object baseFindById(String id) {
        return null;
    }

    @Override
    public Object baseFindAll() {
        return null;
    }

    @Override
    public Object baseFindListByParams(SubscribeInfo entity) {
        return OperaResultUtil.findListByParams(subscribeDao.baseFindListByParams(entity));
    }

    @Override
    public Object baseFindByPage(PageResult<SubscribeInfo> pageResult) {
        return OperaResultUtil.findPage(subscribeDao.baseFindByPage(pageResult));
    }

    @Override
    public Object delete(String email, String author) {
        if(author.equals("*")){
            return OperaResultUtil.deleteBatch(subscribeDao.deleteByEmail(email));
        }else{
            return OperaResultUtil.deleteBatch(subscribeDao.deleteByEmailAndAuthor(email,author));
        }
    }

    @Override
    public Object findByAuthor(String author) {
        Optional<List<SubscribeInfo>> optional = subscribeDao.findByAuthor(author);
        return optional.orElse(null);
    }

}
