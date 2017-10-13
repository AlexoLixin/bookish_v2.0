package cn.don9cn.blog.action.system.file;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;
import cn.don9cn.blog.plugins.actionmsg.util.ActionMsgUtil;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.system.file.interf.UploadFileService;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping(value = "/system/file")
public class UploadFileAction extends BaseAction<UploadFile> {

    private static Logger logger = Logger.getLogger(UploadFileAction.class);

    @Autowired
    private UploadFileService uploadFileService;


    @Override
    @PostMapping("/uploadFile")
    protected Object baseSave(UploadFile uploadFile) {
        return ActionMsgUtil.apply(uploadFileService.insertWithCode(uploadFile), optional ->
                optional.map(value -> new ActionMsg(true,"文件上传成功"))
                        .orElseGet(() -> new ActionMsg(false,"文件上传信息保存至服务器失败")));
    }

    @Override
    protected Object baseSaveBatch(List<UploadFile> list) {
        return null;
    }


    @Override
    @PutMapping("/uploadFile")
    protected Object baseUpdate(UploadFile uploadFile) {
        return ActionMsgUtil.baseUpdate(uploadFileService.baseUpdate(uploadFile));
    }

    @Override
    @DeleteMapping("/uploadFile")
    protected Object baseRemove(String code) {
        return ActionMsgUtil.baseRemove(uploadFileService.baseDeleteById(code));
    }

    @Override
    @DeleteMapping("/uploadFile/batch")
    protected Object baseRemoveBatch(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return ActionMsgUtil.baseRemove(uploadFileService.baseDeleteBatch(codesList));
        }else{
            return new ActionMsg(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    @GetMapping("/uploadFile/{code}")
    protected Object baseFindById(String code) {
        return ActionMsgUtil.baseFindById(uploadFileService.baseFindById(code));
    }

    @Override
    @GetMapping("/uploadFile/all")
    protected Object baseFindAll() {
        return ActionMsgUtil.baseFindAll(uploadFileService.baseFindAll());
    }

    @Override
    @GetMapping("/uploadFile/list")
    protected Object baseFindListByParams(UploadFile uploadFile) {
        return ActionMsgUtil.baseFindListByParams(uploadFileService.baseFindListByParams(uploadFile));
    }

    @Override
    @GetMapping("/uploadFile/page")
    protected Object baseFindByPage(int page, int limit, String orderBy, UploadFile uploadFile) {
        return ActionMsgUtil.baseFindByPage(uploadFileService.baseFindByPage(new PageResult<>(page, limit, orderBy, uploadFile)));
    }
}
