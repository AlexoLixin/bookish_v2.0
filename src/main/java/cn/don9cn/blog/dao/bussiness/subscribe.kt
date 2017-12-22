package cn.don9cn.blog.dao.bussiness

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.SubscribeInfo
import org.springframework.stereotype.Repository


/**
 * 订阅模块dao接口
 */
interface SubscribeInfoDao:BaseDao<SubscribeInfo>

/**
 * 订阅模块dao实现类
 */
@Repository
open class SubscribeInfoDaoImpl:SubscribeInfoDao