package cn.don9cn.blog.service.system.file.interf;


import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;

/**
 * @Author: liuxindong
 * @Description: 上传文件管理service接口
 * @Create: 2017/10/11 8:56
 * @Modify:
 */
public interface UploadFileService extends BaseService<UploadFile> {

    /**
     * 使用自己生成的主键,因为需要返回
     * @param file
     * @return
     */
    Object insertWithCode(UploadFile file);

}
