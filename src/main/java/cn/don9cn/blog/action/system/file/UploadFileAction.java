package cn.don9cn.blog.action.system.file;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;
import cn.don9cn.blog.plugins.actionmsg.util.ActionMsgUtil;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
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
    protected Object doSave(UploadFile uploadFile) {
        return ActionMsgUtil.apply(uploadFileService.insertWithCode(uploadFile), optional ->
                optional.map(value -> new ActionMsg(true,"文件上传成功"))
                        .orElseGet(() -> new ActionMsg(false,"文件上传信息保存至服务器失败")));
    }

    @Override
    @PutMapping("/uploadFile")
    protected Object doUpdate(UploadFile uploadFile) {
        return ActionMsgUtil.doUpdate(uploadFileService.update(uploadFile));
    }

    @Override
    @DeleteMapping("/uploadFile")
    protected Object doRemove(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return ActionMsgUtil.doRemove(uploadFileService.deleteBatch(codesList));
        }else{
            return new ActionMsg(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    @GetMapping("/uploadFile/{code}")
    protected Object doFindById(String code) {
        return ActionMsgUtil.doFindById(uploadFileService.findById(code));
    }

    @Override
    @GetMapping("/uploadFile/all")
    protected Object doFindAll() {
        return ActionMsgUtil.doFindAll(uploadFileService.findAll());
    }

    @Override
    @GetMapping("/uploadFile/list")
    protected Object doFindListByParams(UploadFile uploadFile) {
        return ActionMsgUtil.doFindListByParams(uploadFileService.findListByParams(uploadFile));
    }

    @Override
    @GetMapping("/uploadFile/page")
    protected Object doFindByPage(int page, int limit, UploadFile uploadFile) {
        return ActionMsgUtil.doFindByPage(uploadFileService.findByPage(new PageParamsBean<>(page, limit,  uploadFile)));
    }
}
