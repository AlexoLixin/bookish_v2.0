package cn.don9cn.blog.service.system

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.bussiness.ArticleAndFileDao
import cn.don9cn.blog.dao.system.UploadFileDao
import cn.don9cn.blog.model.system.UploadFile
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.util.UuidUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 上传文件管理service接口
 */
interface UploadFileService : BaseService<UploadFile> {

    /**
     * 使用自己生成的主键,因为需要返回
     * @param file
     * @return
     */
    fun insertWithCode(file: UploadFile): UploadFile?

}

/**
 * 上传文件管理service接口实现
 */
@Service
@Transactional
open class UploadFileServiceImpl : UploadFileService {

    @Autowired
    private val uploadFileDao: UploadFileDao? = null

    @Autowired
    private val articleAndFileDao: ArticleAndFileDao? = null


    override fun baseInsert(entity: UploadFile): Int {
        return uploadFileDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<UploadFile>): Int {
        return uploadFileDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: UploadFile): Int {
        return uploadFileDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return uploadFileDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return if (codes.isNotBlank()) {
            uploadFileDao!!.baseDeleteBatch(codes.split(","))
        } else {
            0
        }
    }

    override fun baseFindById(id: String): UploadFile? {
        return uploadFileDao!!.baseFindById(id)
    }

    override fun baseFindAll(): List<UploadFile> {
        return uploadFileDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: UploadFile): List<UploadFile> {
        return uploadFileDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<UploadFile>): PageResult<UploadFile> {
        val page = uploadFileDao!!.baseFindByPage(pageResult)
        page.rows.forEach {
            articleAndFileDao!!.fillLink(it)
        }
        return page
    }

    /**
     * 使用自己生成的主键,因为需要返回
     * @param file
     * @return
     */
    override fun insertWithCode(file: UploadFile): UploadFile? {
        file.code = UuidUtil.getUuid()
        return if(uploadFileDao!!.baseInsert(file)>0){
            file
        }else{
            null
        }
    }
}
