package cn.don9cn.blog.action.system.file

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.file.UploadFile
import cn.don9cn.blog.service.system.file.UploadFileService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = "/system/file/uploadFile")
open class UploadFileAction : BaseAction<UploadFile>() {

    @Autowired
    private var uploadFileService: UploadFileService? = null


    override fun baseInsert(uploadFile: UploadFile): ActionMsg {
        return super.insert(uploadFileService!!.baseInsert(uploadFile))
    }

    override fun baseInsertBatch(list: List<UploadFile>):ActionMsg {
        return super.insert(uploadFileService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(uploadFile: UploadFile): ActionMsg {
        return super.update(uploadFileService!!.baseUpdate(uploadFile))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return super.delete(uploadFileService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(uploadFileService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return super.find(uploadFileService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return super.find(uploadFileService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(uploadFile: UploadFile): ActionMsg {
        return super.find(uploadFileService!!.baseFindListByParams(uploadFile))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, uploadFile: UploadFile): ActionMsg {
        return super.find(uploadFileService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, uploadFile)))
    }

}