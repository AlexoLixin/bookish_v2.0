package cn.don9cn.blog.action.bussiness.subscribe

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo
import cn.don9cn.blog.service.bussiness.SubscribeService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/bussiness/subscribe"])
open class SubscribeAction : BaseAction<SubscribeInfo>() {

    @Autowired
    private var subscribeService: SubscribeService? = null

    @PostMapping("/public")
    override fun baseInsert(t: SubscribeInfo): ActionMsg {
        return super.insert(subscribeService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<SubscribeInfo>): ActionMsg {
        return super.insert(subscribeService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(t: SubscribeInfo): ActionMsg {
        return super.update(subscribeService!!.baseUpdate(t))
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
    override fun baseFindListByParams(t: SubscribeInfo): ActionMsg {
        return super.find(subscribeService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SubscribeInfo): ActionMsg {
        return super.find(subscribeService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @DeleteMapping("/public")
    open fun delete(email: String, author: String): ActionMsg {
        return super.delete(subscribeService!!.delete(email, author))
    }

}
