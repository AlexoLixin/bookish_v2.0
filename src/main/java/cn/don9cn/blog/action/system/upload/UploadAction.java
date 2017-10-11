package cn.don9cn.blog.action.system.upload;

import cn.don9cn.blog.autoconfigs.upload.FileSavePathConfig;
import cn.don9cn.blog.exception.ExceptionWrapper;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;
import cn.don9cn.blog.plugins.actionmsg.util.ActionMsgUtil;
import cn.don9cn.blog.service.system.file.interf.UploadFileService;
import cn.don9cn.blog.support.vue.VueImageUploadMsg;
import cn.don9cn.blog.util.ExecutorUtil;
import cn.don9cn.blog.util.FileSaveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


/**
 * @Author: liuxindong
 * @Description: 文件上传和下载
 * @Create: 2017/10/11 9:57
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/upload")
public class UploadAction {

    private static Logger logger = Logger.getLogger(UploadAction.class);

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private FileSavePathConfig fileSavePathConfig;

    /**
     * 图片上传
     * @param request
     * @return
     */
    @PostMapping("/image")
    public Object doImageUpload(HttpServletRequest request) {

        List<MultipartFile> images = ((MultipartHttpServletRequest)request).getFiles("imageName");

        if(images!=null && images.size()>0){
            List<String> imageUrls = new ArrayList<>();

            Stream<CompletableFuture<Void>> futureStream = images.stream().map(image -> CompletableFuture.runAsync(() -> {
                //将图片保存至服务器,并返回图片url地址
                Optional<String> optional = FileSaveUtil.saveImage(image, fileSavePathConfig);
                optional.ifPresent(imageUrls::add);
            }, ExecutorUtil.build(images.size())));

            CompletableFuture[] futures = futureStream.toArray(size -> new CompletableFuture[size]);
            CompletableFuture.allOf(futures).join();

            return new VueImageUploadMsg(0,imageUrls);
        }else {
            return new VueImageUploadMsg(1,new ArrayList<>());
        }
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/file")
    public Object doFileUpload(@RequestParam("file") CommonsMultipartFile file) {
        if (file != null) {
            //将文件保存至服务器
            Optional<UploadFile> optional = FileSaveUtil.saveFile(file, fileSavePathConfig);
            if(optional.isPresent()){
                return ActionMsgUtil.apply(uploadFileService.insertWithCode(optional.get()),
                        insertResult -> insertResult.map(value -> new ActionMsg(true,"文件上传成功"))
                                                    .orElseGet(() -> new ActionMsg(false,"文件上传信息保存至服务器失败")));
            }
            return new ActionMsg(false,"文件上传失败");
        }
        return new ActionMsg(false,"文件上传失败,文件列表为空");
    }

    /**
     * 批量文件上传
     * @param request
     * @return
     */
    @PostMapping("/fileBatch")
    public Object doFileUploadBatch(HttpServletRequest request) {

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("files");

        if(files!=null && files.size()>0){
            List<UploadFile> successFiles = new ArrayList<>();

            Stream<CompletableFuture<Void>> futureStream = files.stream().map(file -> CompletableFuture.runAsync(() -> {
                //将图片保存至服务器,并返回图片url地址
                Optional<UploadFile> optional = FileSaveUtil.saveFile(file, fileSavePathConfig);
                //数据库保存上传文件信息
                optional.ifPresent(uploadFile -> uploadFileService.insertWithCode(uploadFile).ifPresent(successFiles::add));
            }, ExecutorUtil.build(files.size())));

            CompletableFuture[] futures = futureStream.toArray(size -> new CompletableFuture[size]);
            CompletableFuture.allOf(futures).join();

            return new ActionMsg(true, "文件上传成功！").setObj(successFiles);
        }else {
            return new ActionMsg(false, "上传文件失败,上传文件列表为空！");
        }

    }

    /**
     * 文件下载
     * @param code
     * @param response
     */
    @GetMapping("fileDownLoad")
    public void doDownload(String code, HttpServletResponse response) {

        Optional<UploadFile> fileInfo = uploadFileService.findById(code);
        fileInfo.ifPresent(file -> {
            try(
                    InputStream in = new FileInputStream(file.getPath() + "/" + file.getName());
                    ServletOutputStream out = response.getOutputStream()
            ){
                //设置响应头和客户端保存文件名
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + file.getRealName());
                byte[] arr = new byte[2048];
                int length;
                while ((length = in.read(arr)) > 0) {
                    out.write(arr, 0, length);
                }

            }catch (Exception e){
                throw new ExceptionWrapper(e, "下载文件失败!");
            }
        });

    }

}