package cn.don9cn.blog.dao.system.file.interf;

import cn.don9cn.blog.model.system.file.UploadFile;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 上传文件管理dao接口
 * @Create: 2017/10/17 9:08
 * @Modify:
 */
public interface UploadFileDao extends BaseDao<UploadFile> {

    Optional<List<UploadFile>> findListInCodes(List<String> codes);

}

