package cn.don9cn.blog.model.bussiness.article

import cn.don9cn.blog.model.BaseModel
import cn.don9cn.blog.model.system.file.UploadFile

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
    var filesList: List<UploadFile>? = null     //附件实体
    var classifyName: String? = null            //分类名称
}

/**
 * @Author: liuxindong
 * @Description:  文章实体和上传文件的关联实体
 * @Created: 2017/12/22 15:20
 * @Modified:
 */
class ArticleAndFile(val articleCode: String,val fileCode: String) : BaseModel()





