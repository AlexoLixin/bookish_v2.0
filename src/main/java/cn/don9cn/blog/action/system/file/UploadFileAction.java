package cn.don9cn.blog.action.system.file;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.file.interf.UploadFileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: 上传文件管理action
 * @Create: 2017/10/11 8:56
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/file/uploadFile")
public class UploadFileAction extends BaseAction<UploadFile> {

    private static Logger logger = Logger.getLogger(UploadFileAction.class);

    @Autowired
    private UploadFileService uploadFileService;


    @Override
    protected OperaResult baseInsert(UploadFile uploadFile) {
        return null;
    }

    @Override
    protected OperaResult baseInsertBatch(List<UploadFile> list) {
        return null;
    }

    @Override
    protected OperaResult baseUpdate(UploadFile uploadFile) {
        return null;
    }

    @Override
    @DeleteMapping
    protected OperaResult baseRemove(String code) {
        return uploadFileService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected OperaResult baseRemoveBatch(String codes) {
        return uploadFileService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected OperaResult baseFindById(String code) {
        return uploadFileService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected OperaResult baseFindAll() {
        return uploadFileService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected OperaResult baseFindListByParams(UploadFile uploadFile) {
        return uploadFileService.baseFindListByParams(uploadFile);
    }

    @Override
    @GetMapping("/page")
    protected OperaResult baseFindByPage(int page,int limit,String startTime,String endTime,String orderBy,UploadFile uploadFile) {
        return uploadFileService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,uploadFile));
    }

}
