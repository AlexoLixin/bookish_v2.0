package cn.don9cn.blog.dao.system.file

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.system.file.UploadFile
import org.springframework.stereotype.Repository

/**
 * 上传文件管理dao接口
 */
interface UploadFileDao : BaseDao<UploadFile> {
    fun findListInCodes(codes: List<String>): List<UploadFile>
}

/**
 * 上传文件管理dao接口实现
 */
@Repository
open class UploadFileDaoImpl : UploadFileDao {

    override fun findListInCodes(codes: List<String>): List<UploadFile> {
        return dslOperator{
            findListInIds(codes)
        }
    }
}

