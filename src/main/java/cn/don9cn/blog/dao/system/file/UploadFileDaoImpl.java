package cn.don9cn.blog.dao.system.file;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.system.file.UploadFile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @Author: liuxindong
 * @Description: 上传文件管理dao接口实现
 * @Create: 2017/10/11 8:53
 * @Modify:
 */
@Repository
public class UploadFileDaoImpl implements BaseDao<UploadFile> {

    public Optional<List<UploadFile>> findListInCodes(List<String> codes){
        return Optional.ofNullable(this.getSessionTemplate().selectList(getSqlName("findListInCodes"),codes));
    }
}
