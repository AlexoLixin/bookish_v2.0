package cn.don9cn.blog.model.bussiness.articleclassify

import cn.don9cn.blog.model.BaseModel
import java.util.ArrayList


/**
 * 文章分类实体
 */
class ArticleClassify : BaseModel() {
    var name: String? = null        //分类名称
    var parent: String? = null      //分类父节点
    var level: String? = null       //分类等级
    var leaf: String? = null        //是否是叶子节点
    var childrenCodes: List<String> = ArrayList()    //子节点主键集合
    var children: MutableList<ArticleClassify> = ArrayList()    //子节点集合

    fun addChild(articleClassify: ArticleClassify) {
        this.children.add(articleClassify)
    }
}
