package cn.don9cn.blog.action.system.upload

import cn.don9cn.blog.annotation.SkipOperaLog
import cn.don9cn.blog.autoconfigure.filepath.FileSavePathConfig
import cn.don9cn.blog.exception.ExceptionWrapper
import cn.don9cn.blog.model.system.file.UploadFile
import cn.don9cn.blog.service.system.file.UploadFileService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.vue.VueImageUploadMsg
import cn.don9cn.blog.util.FileSaveUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.io.FileInputStream
import java.net.URLEncoder
import java.util.ArrayList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/system/upload"])
open class UploadAction {

    @Autowired
    private var uploadFileService: UploadFileService? = null

    @Autowired
    private var fileSavePathConfig: FileSavePathConfig? = null

    /**
     * 图片上传
     * @param request
     * @return
     */
    @PostMapping("/image")
    open fun doImageUpload(request: HttpServletRequest): VueImageUploadMsg {

        val images = (request as MultipartHttpServletRequest).getFiles("imageName")

        if (images != null && images.size > 0) {
            val imageUrls = ArrayList<String>()

            val futureStream = images.stream().map { image ->
                CompletableFuture.runAsync(Runnable{
                    //将图片保存至服务器,并返回图片url地址
                    val savePath = FileSaveUtil.saveImage(image, fileSavePathConfig!!)
                    savePath?.let { imageUrls.add(it) }
                }, Executors.newFixedThreadPool(images.size))
            }

            val futures = futureStream.toArray<CompletableFuture<*>> { size -> arrayOfNulls(size) }
            CompletableFuture.allOf(*futures).join()

            return VueImageUploadMsg(0, imageUrls)
        } else {
            return VueImageUploadMsg(1, ArrayList())
        }
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/file")
    open fun doFileUpload(@RequestParam("file") file: CommonsMultipartFile?): Any {
        return file?.let { file_ ->
            //将文件保存至服务器
            val uploadFile = FileSaveUtil.saveFile(file_, fileSavePathConfig!!)
            //将文件信息保存到数据库
            uploadFile?.let {
                uploadFileService!!.insertWithCode(it)?:ActionMsg(false, "文件上传失败")
            }
        }?:ActionMsg(false, "文件上传失败,文件列表为空")
    }

    /**
     * 批量文件上传
     * @param request
     * @return
     */
    @PostMapping("/fileBatch")
    open fun doFileUploadBatch(request: HttpServletRequest): Any {

        val files = (request as MultipartHttpServletRequest).getFiles("files")

        if (files != null && files.size > 0) {
            val successFiles = ArrayList<UploadFile>()

            val futureStream = files.stream().map { file ->
                CompletableFuture.runAsync(Runnable{
                    //将图片保存至服务器,并返回图片url地址
                    val uploadFile = FileSaveUtil.saveFile(file, fileSavePathConfig!!)
                    //数据库保存上传文件信息
                    uploadFile?.let { file ->
                        uploadFileService!!.insertWithCode(file)?.let {
                            successFiles.add(it)
                        }
                    }
                }, Executors.newFixedThreadPool(files.size))
            }

            val futures = futureStream.toArray<CompletableFuture<*>> { size -> arrayOfNulls(size) }
            CompletableFuture.allOf(*futures).join()

            return ActionMsg(true, "文件上传成功！").setObj(successFiles)
        } else {
            return ActionMsg(false, "上传文件失败,上传文件列表为空！")
        }

    }

    /**
     * 文件下载
     */
    @SkipOperaLog
    @GetMapping("/fileDownLoad")
    open fun doDownload(code: String, response: HttpServletResponse) {
        val uploadFile = uploadFileService!!.baseFindById(code)
        uploadFile?.let {   file ->
            try {
                FileInputStream(file.path + "/" + file.name).use({ input ->
                    response.outputStream.use { out ->
                        //设置响应头和客户端保存文件名
                        response.contentType = "application/octet-stream"
                        val fileRealName = URLEncoder.encode(file.realName, "UTF-8")
                        response.setHeader("Content-Disposition", "attachment;fileName=" + fileRealName)
                        val arr = ByteArray(2048)
                        var len = 0
                        while (input.read(arr).apply { len = this } > 0) {
                            out.write(arr, 0, len)
                        }
                    }
                })
            } catch (e: Exception) {
                throw ExceptionWrapper(e, "下载文件失败!")
            }
        }
    }

}