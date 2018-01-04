package cn.don9cn.blog.util

import cn.don9cn.blog.autoconfigure.filepath.FileSavePathConfig
import cn.don9cn.blog.model.system.file.UploadFile
import org.apache.log4j.Logger
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

/**
 * 文件保存工具类
 */
object FileSaveUtil {

    private val logger = Logger.getLogger(this::class.java)

    /**
     * 文件保存目录, 以 年-月-日 的形式
     */
    fun getSaveDir(dateTime: LocalDateTime): String {
        return dateTime.year.toString() + "-" + dateTime.month + "-" + dateTime.dayOfMonth
    }

    /**
     * 保存上传文件
     */
    fun saveFile(file: MultipartFile, fileSavePathConfig: FileSavePathConfig): UploadFile? {

        // 获取当前时间
        val dateTime = LocalDateTime.now()
        // 文件的原始名称
        val originalFilename = file.originalFilename
        // 创建文件的保存名称
        val saveFileName = Md5Util.getMD5(originalFilename + dateTime)
        // 创建保存目录
        val dir = fileSavePathConfig.serverRoot + fileSavePathConfig.uploadFiles + "/" + getSaveDir(dateTime)
        val saveDir = File(dir)
        if (!saveDir.exists()) {
            saveDir.setWritable(true, false)
            saveDir.mkdirs()
        }
        // 将文件保存至服务器
        val savePath = dir + "/" + saveFileName
        val targetFile = File(savePath)
        targetFile.setWritable(true, false)
        try {
            file.transferTo(targetFile)
        } catch (e: IOException) {
            logger.error("FileSaveUtil.saveFile 上传文件保存至服务器硬盘失败!")
            return null
        }

        return UploadFile(originalFilename, saveFileName, dir)

    }

    /**
     * 保存上传图片
     */
    fun saveImage(file: MultipartFile, fileSavePathConfig: FileSavePathConfig): String? {

        // 获取当前时间
        val dateTime = LocalDateTime.now()
        // 图片的原始名称
        val originalFilename = file.originalFilename
        // 创建图片的保存名称
        val saveFileName = Md5Util.getMD5(originalFilename + dateTime) + "." + originalFilename.split("\\.".toRegex())[1]
        // 创建保存目录
        val dir = getSaveDir(dateTime)
        val dirPath = fileSavePathConfig.serverRoot + fileSavePathConfig.images + "/" + dir
        val saveDir = File(dirPath)
        if (!saveDir.exists()) {
            saveDir.setWritable(true, false)
            saveDir.mkdirs()
        }
        // 将文件保存至服务器
        val savePath = dirPath + "/" + saveFileName
        val targetFile = File(savePath)
        targetFile.setWritable(true, false)
        try {
            file.transferTo(targetFile)
        } catch (e: IOException) {
            logger.error("FileSaveUtil.saveFile 上传图片保存至服务器硬盘失败!")
            return null
        }

        return fileSavePathConfig.serverUrl + fileSavePathConfig.images + "/" + dir + "/" + saveFileName

    }
}