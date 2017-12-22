package cn.don9cn.blog.service.bussiness

import cn.don9cn.blog.dao.bussiness.SubscribeInfoDao
import cn.don9cn.blog.model.bussiness.SubscribeInfo
import cn.don9cn.blog.support.daohelper.core.PageResult
import cn.don9cn.blog.support.operaresult.core.OperaResult
import cn.don9cn.blog.support.operaresult.util.OperaResultUtil
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.util.MyStringUtil
import cn.don9cn.blog.util.UuidUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 订阅模块service接口
 */
interface SubscribeInfoService:BaseService<SubscribeInfo>


/**
 * 订阅模块service实现类
 */
@Service
@Transactional
open class SubscribeInfoServiceImpl:SubscribeInfoService{

    @Autowired
    private var subscribeInfoDao: SubscribeInfoDao? = null

    override fun baseInsert(entity: SubscribeInfo): Any {
        entity.code = UuidUtil.getUuid()
        return OperaResultUtil.insert(subscribeInfoDao?.baseInsert(entity))
    }

    override fun baseInsertBatch(list: MutableList<SubscribeInfo>): Any {
        return OperaResultUtil.insertBatch(subscribeInfoDao?.baseInsertBatch(list))
    }

    override fun baseUpdate(entity: SubscribeInfo): Any {
        return OperaResultUtil.update(subscribeInfoDao?.baseUpdate(entity))
    }

    override fun baseDeleteById(id: String): Any {
        return OperaResultUtil.deleteOne(subscribeInfoDao?.baseDeleteById(id))
    }

    override fun baseDeleteBatch(codes: String): Any {
        return if(codes.isNotBlank()){
            val codesList = MyStringUtil.codesStr2List(codes)
            OperaResultUtil.deleteBatch(subscribeInfoDao?.baseDeleteBatch(codesList))
        }else{
            OperaResult(false, "删除失败,传入codes为空!")
        }
    }

    override fun baseFindById(id: String): Any {
        return OperaResultUtil.findOne<SubscribeInfo>(subscribeInfoDao?.baseFindById(id))
    }

    override fun baseFindAll(): Any {
        return OperaResultUtil.findAll<SubscribeInfo>(subscribeInfoDao?.baseFindAll())
    }

    override fun baseFindListByParams(entity: SubscribeInfo): Any {
        return OperaResultUtil.findListByParams<SubscribeInfo>(subscribeInfoDao?.baseFindListByParams(entity))
    }

    override fun baseFindByPage(pageResult: PageResult<SubscribeInfo>): Any {
        return OperaResultUtil.findPage<SubscribeInfo>(subscribeInfoDao?.baseFindByPage(pageResult))
    }

}