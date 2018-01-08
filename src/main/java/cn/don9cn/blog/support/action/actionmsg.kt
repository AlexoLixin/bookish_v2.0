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

    fun insert(x:Int,successMsg:String = "添加成功",failMsg:String = "添加失败"):ActionMsg{
        return when{
            x>0 -> ActionMsg(true,successMsg)
            else -> ActionMsg(false,failMsg)
        }
    }

    fun update(x:Int,successMsg:String = "更新成功",failMsg:String = "更新失败"):ActionMsg{
        return when{
            x>0 -> ActionMsg(true,successMsg)
            else -> ActionMsg(false,failMsg)
        }
    }

    fun delete(x:Int,successMsg:String = "删除成功",failMsg:String = "删除失败"):ActionMsg{
        return when{
            x>0 -> ActionMsg(true,successMsg)
            else -> ActionMsg(false,failMsg)
        }
    }

    fun find(entity:Any?,successMsg:String = "查询失败",failMsg:String = "查询成功"):ActionMsg{
        return when (entity) {
            null -> ActionMsg(false,successMsg)
            else -> ActionMsg(true,failMsg).setObj(entity)
        }
    }

}
