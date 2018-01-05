package cn.don9cn.blog.model

import org.springframework.data.annotation.Id
import java.io.Serializable

/**
 * @Author: liuxindong
 * @Description:  基础model
 * @Created: 2017/12/22 16:25
 * @Modified:
 */
open class BaseModel : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
    @Id
    var code: String? = null            //主键
    var flagDel = "N"                   //删除标记
    var createTime: String? = null      //创建时间
    var modifyTime: String? = null      //修改时间
    var createBy: String? = null        //创建人
    var modifyBy: String? = null        //修改人
    var zMore01: String? = null //备用字段1
    var zMore02: String? = null //备用字段2
    var zMore03: String? = null //备用字段3
    var zMore04: String? = null //备用字段4
    var zMore05: String? = null //备用字段5
    var remark: String? = null          //说明
    var num: String? = null             //排序
}