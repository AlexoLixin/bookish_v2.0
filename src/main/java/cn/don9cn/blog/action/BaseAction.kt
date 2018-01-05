package cn.don9cn.blog.action

import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler

/**
 * 基础action,定义了action层的基础行为
 */
abstract class BaseAction<in T:Any> {

    protected fun insert(x:Int): ActionMsg{
        return ActionMsgHandler.insert(x)
    }

    protected fun update(x:Int): ActionMsg{
        return ActionMsgHandler.update(x)
    }

    protected fun delete(x:Int): ActionMsg{
        return ActionMsgHandler.delete(x)
    }

    protected fun find(entity:Any?): ActionMsg{
        return ActionMsgHandler.find(entity)
    }

    protected abstract fun baseInsert(t: T): ActionMsg

    protected abstract fun baseInsertBatch(list: List<T>): ActionMsg

    protected abstract fun baseUpdate(t: T): ActionMsg

    protected abstract fun baseRemove(code: String): ActionMsg

    protected abstract fun baseRemoveBatch(codes: String): ActionMsg

    protected abstract fun baseFindById(code: String): ActionMsg

    protected abstract fun baseFindAll(): ActionMsg

    protected abstract fun baseFindListByParams(t: T): ActionMsg

    protected abstract fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: T): ActionMsg

}