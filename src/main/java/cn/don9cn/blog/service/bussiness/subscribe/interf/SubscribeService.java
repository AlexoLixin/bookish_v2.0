package cn.don9cn.blog.service.bussiness.subscribe.interf;

import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo;
import cn.don9cn.blog.service.BaseService;

/**
 * @author Don9
 * @create 2017-12-27-11:19
 **/
public interface SubscribeService extends BaseService<SubscribeInfo> {

    Object delete(String email,String author);

}
