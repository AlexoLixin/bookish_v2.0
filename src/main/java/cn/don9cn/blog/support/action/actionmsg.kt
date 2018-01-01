package cn.don9cn.blog.support.action

import java.io.Serializable

/**
 * action返回消息实体
 */
class ActionMsg : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    var isSuccess = false

    var message: String? = null

    private var obj: Any? = null

    constructor() {}

    constructor(success: Boolean, message: String) {
        this.isSuccess = success
        this.message = message
    }

    fun setObj(obj: Any): ActionMsg {
        this.obj = obj
        return this
    }

    fun getObj(): Any? {
        return obj
    }

}

/**
 * action返回消息实体处理器: 根据service层的返回结果生成ActionMsg实体
 */
object ActionMsgHandler{

    fun insert(x:Int):ActionMsg{
        return when{
            x>0 -> ActionMsg(true,"添加成功")
            else -> ActionMsg(false,"添加失败")
        }
    }

    fun update(x:Int):ActionMsg{
        return when{
            x>0 -> ActionMsg(true,"更新成功")
            else -> ActionMsg(false,"更新失败")
        }
    }

    fun delete(x:Int):ActionMsg{
        return when{
            x>0 -> ActionMsg(true,"删除成功,$x 条记录被删除")
            else -> ActionMsg(false,"删除失败")
        }
    }

    fun find(entity:Any?):ActionMsg{
        return when (entity) {
            null -> ActionMsg(false,"查询失败")
            else -> ActionMsg(true,"查询成功").setObj(entity)
        }
    }

}
