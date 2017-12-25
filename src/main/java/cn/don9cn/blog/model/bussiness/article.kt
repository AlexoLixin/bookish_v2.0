package cn.don9cn.blog.model.bussiness

import cn.don9cn.blog.model.BaseModel
import cn.don9cn.blog.model.system.UploadFile
import java.util.ArrayList

/**
 * @Author: liuxindong
 * @Description:   文章model实体
 * @Created: 2017/12/22 15:20
 * @Modified:
 */
class Article : BaseModel() {
    var title: String? = null                   //文章标题
    var author: String? = null                  //文章作者
    var classify: String? = null                //文章分类
    var content: String? = null                 //文章内容
    var files: String? = null                   //附件code
    var filesList: MutableList<UploadFile>? = null     //附件实体
    var classifyName: String? = null            //分类名称
}

/**
 * @Author: liuxindong
 * @Description:  文章实体和上传文件的关联实体
 * @Created: 2017/12/22 15:20
 * @Modified:
 */
class ArticleAndFile(val articleCode: String,val fileCode: String) : BaseModel()


/**
 * @Author: liuxindong
 * @Description:  文章分类实体
 * @Created: 2017/12/22 15:20
 * @Modified:
 */
class ArticleClassify : BaseModel() {
    var name: String? = null        //分类名称
    var parent: String? = null      //分类父节点
    var level: String? = null       //分类等级
    var leaf: String? = null        //是否是叶子节点
    var childrenCodes: MutableList<String> = ArrayList()    //子节点主键集合
    var children: MutableList<ArticleClassify> = ArrayList()    //子节点集合

    fun addChild(articleClassify: ArticleClassify) {
        this.children.add(articleClassify)
    }
}

/**
 * @Author: liuxindong
 * @Description:  文章评论实体
 * @Created: 2017/12/22 15:20
 * @Modified:
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

