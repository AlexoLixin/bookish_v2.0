package cn.don9cn.blog.action.bussiness.subscribe

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.SubscribeInfo
import cn.don9cn.blog.service.bussiness.SubscribeService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/bussiness/subscribe")
open class SubscribeAction : BaseAction<SubscribeInfo>() {

    @Autowired
    private val subscribeService: SubscribeService? = null

    @PostMapping("/public")
    override fun baseInsert(subscribeInfo: SubscribeInfo): ActionMsg {
        return super.insert(subscribeService!!.baseInsert(subscribeInfo))
    }

    override fun baseInsertBatch(list: List<SubscribeInfo>): ActionMsg {
        return super.insert(subscribeService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(subscribeInfo: SubscribeInfo): ActionMsg {
        return super.update(subscribeService!!.baseUpdate(subscribeInfo))
    }

    override fun baseRemove(code: String): ActionMsg {
        return super.delete(subscribeService!!.baseDeleteById(code))
    }

    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(subscribeService!!.baseDeleteBatch(codes))
    }

    override fun baseFindById(code: String): ActionMsg {
        return super.find(subscribeService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return super.find(subscribeService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(subscribeInfo: SubscribeInfo): ActionMsg {
        return super.find(subscribeService!!.baseFindListByParams(subscribeInfo))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, subscribeInfo: SubscribeInfo): ActionMsg {
        return super.find(subscribeService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, subscribeInfo)))
    }

    @DeleteMapping("/public")
    fun delete(email: String, author: String): ActionMsg {
        return super.delete(subscribeService!!.delete(email, author))
    }

}
