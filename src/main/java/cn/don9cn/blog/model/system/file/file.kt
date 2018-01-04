package cn.don9cn.blog.model.system.file

import cn.don9cn.blog.model.BaseModel

/**
 * @Author: liuxindong
 * @Description:  上传文件实体
 * @Created: 2017/12/22 15:23
 * @Modified:
 */
class UploadFile() : BaseModel() {
    var name: String? = null    //文件名称
    var realName: String? = null    //真实名称
    var path: String? = null        //文件路径
    var link: String? = null        //关联来源

    constructor(realName: String, name: String, path: String) : this() {
        this.realName = realName
        this.name = name
        this.path = path
    }

}