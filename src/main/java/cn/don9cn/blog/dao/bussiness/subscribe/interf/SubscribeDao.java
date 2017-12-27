package cn.don9cn.blog.dao.bussiness.subscribe.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo;

import java.util.OptionalInt;

/**
 * @author Don9
 * @create 2017-12-27-11:16
 **/
public interface SubscribeDao extends BaseDao<SubscribeInfo> {

    OptionalInt deleteByEmail(String email);

    OptionalInt deleteByEmailAndAuthor(String email,String author);

}
