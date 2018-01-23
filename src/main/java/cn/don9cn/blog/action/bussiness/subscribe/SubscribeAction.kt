package cn.don9cn.blog.action.bussiness.subscribe

import cn.don9cn.blog.support.mongo.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo
import cn.don9cn.blog.service.bussiness.SubscribeService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/bussiness/subscribe"])
open class SubscribeAction : BaseAction<SubscribeInfo>() {

    @Autowired
    private var subscribeService: SubscribeService? = null

    @PostMapping("/public")
    override fun baseInsert(t: SubscribeInfo): ActionMsg {
        return subscribeService!!.insert(t)
    }

    override fun baseInsertBatch(list: List<SubscribeInfo>): ActionMsg {
        return ActionMsgHandler.insert(subscribeService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(t: SubscribeInfo): ActionMsg {
        return ActionMsgHandler.update(subscribeService!!.baseUpdate(t))
    }


    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(subscribeService!!.baseDeleteById(code))
    }

    @DeleteMapping
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(subscribeService!!.baseDeleteBatch(codes))
    }

    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(subscribeService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(subscribeService!!.baseFindAll())
    }

    override fun baseFindListByParams(t: SubscribeInfo): ActionMsg {
        return ActionMsgHandler.find(subscribeService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SubscribeInfo): ActionMsg {
        return ActionMsgHandler.find(subscribeService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @GetMapping("/page/byUser")
    open fun findByPageByUser(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SubscribeInfo): ActionMsg {
        return ActionMsgHandler.find(subscribeService!!.findByPageByUser(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @GetMapping("/subscribes/page/byUser")
    open fun findSubscribesByPageByUser(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SubscribeInfo): ActionMsg {
        return ActionMsgHandler.find(subscribeService!!.findSubscribesByPageByUser(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @DeleteMapping("/public")
    open fun delete(email: String, author: String): ActionMsg {
        return subscribeService!!.delete(email, author)
    }

}
