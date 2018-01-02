package cn.don9cn.blog.support.vue

import cn.don9cn.blog.model.BaseModel

/**
 * 文件上传后的返回结果
 */
class VueImageUploadMsg(
        /**
         * 上传结果状态码, 0 为成功, 1 为失败(WangEditor中定义的)
         */
        val errno: Int,
        /**
         * 图片链接
         */
        val data: List<String>)


/**
 * 用于显示分类的下拉列表
 */
class VueSelectOption(val label: String, val value: String) : BaseModel()
