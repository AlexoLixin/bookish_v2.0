package cn.don9cn.blog.model.bussiness.acticlecomment

import cn.don9cn.blog.model.BaseModel
import java.util.ArrayList


/**
 * 文章评论实体
 */
class ArticleComment : BaseModel() {
    var nickname: String? = null        //昵称
    var content: String? = null         //内容
    var articleCode: String? = null     //关联的文章主键
    var parent: String? = null          //父节点
    var parentName: String? = null      //父节点昵称
    var replyCodes: MutableList<String> = ArrayList()      //回复主键集合
    var replies: MutableList<ArticleComment> = ArrayList()  //回复实体集合

    fun addReply(reply: ArticleComment) {
        this.replies.add(reply)
    }
}
