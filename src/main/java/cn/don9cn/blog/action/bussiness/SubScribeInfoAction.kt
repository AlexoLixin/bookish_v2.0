package cn.don9cn.blog.action.bussiness

import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.SubscribeInfo
import cn.don9cn.blog.support.daohelper.core.PageResult
import cn.don9cn.blog.service.bussiness.SubscribeInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/bussiness/subscribe"])
open class SubScribeInfoAction: BaseAction<SubscribeInfo>() {

    @Autowired
    private var subscribeInfoService: SubscribeInfoService? = null

    @PostMapping
    override fun baseInsert(t: SubscribeInfo): Any {
        return subscribeInfoService!!.baseInsert(t)
    }

    override fun baseInsertBatch(list: MutableList<SubscribeInfo>): Any {
        return subscribeInfoService!!.baseInsertBatch(list)
    }

    override fun baseUpdate(t: SubscribeInfo): Any {
        return subscribeInfoService!!.baseUpdate(t)
    }

    override fun baseRemove(code: String): Any {
        return subscribeInfoService!!.baseDeleteById(code)
    }

    override fun baseRemoveBatch(codes: String): Any {
        return subscribeInfoService!!.baseDeleteBatch(codes)
    }

    override fun baseFindById(code: String): Any {
        return subscribeInfoService!!.baseFindById(code)
    }

    override fun baseFindAll(): Any {
        return subscribeInfoService!!.baseFindAll()
    }

    override fun baseFindListByParams(t: SubscribeInfo): Any {
        return subscribeInfoService!!.baseFindListByParams(t)
    }

    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SubscribeInfo): Any {
        return subscribeInfoService!!.baseFindByPage(PageResult<SubscribeInfo>(page, limit, startTime, endTime, orderBy, t))
    }
}