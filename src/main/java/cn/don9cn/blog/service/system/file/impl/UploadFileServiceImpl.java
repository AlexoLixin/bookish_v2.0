package cn.don9cn.blog.service.system.file.impl;

import cn.don9cn.blog.dao.system.file.UploadFileDaoImpl;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.system.file.interf.UploadFileService;
import cn.don9cn.blog.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * @Author: liuxindong
 * @Description: 上传文件管理service接口实现
 * @Create: 2017/10/11 8:56
 * @Modify:
 */
@Service
@Transactional
public class UploadFileServiceImpl implements UploadFileService {

	@Autowired
	private UploadFileDaoImpl uploadFileDaoImpl;


	@Override
	public OptionalInt baseInsert(UploadFile entity) {
		return uploadFileDaoImpl.baseInsert(entity);
	}

	@Override
	public OptionalInt baseInsertBatch(List<UploadFile> list) {
		return uploadFileDaoImpl.baseInsertBatch(list);
	}

	@Override
	public OptionalInt baseUpdate(UploadFile entity) {
		return uploadFileDaoImpl.baseUpdate(entity);
	}

	@Override
	public OptionalInt baseDeleteById(String id) {
		return uploadFileDaoImpl.baseDeleteById(id);
	}

	@Override
	public OptionalInt baseDeleteBatch(List<String> list) {
		return uploadFileDaoImpl.baseDeleteBatch(list);
	}

	@Override
	public Optional<UploadFile> baseFindById(String id) {
		return uploadFileDaoImpl.baseFindById(id);
	}

	@Override
	public Optional<List<UploadFile>> baseFindAll() {
		return uploadFileDaoImpl.baseFindAll();
	}

	@Override
	public Optional<List<UploadFile>> baseFindListByParams(UploadFile entity) {
		return uploadFileDaoImpl.baseFindListByParams(entity);
	}

	@Override
	public Optional<PageResult<UploadFile>> baseFindByPage(PageResult<UploadFile> pageResult) {
		return uploadFileDaoImpl.baseFindByPage(pageResult);
	}

	/**
	 * 使用自己生成的主键,因为需要返回
	 * @param file
	 * @return
	 */
	@Override
	public Optional<UploadFile> insertWithCode(UploadFile file) {
		file.setCode(UuidUtil.getUuid());
		OptionalInt optional = uploadFileDaoImpl.baseInsert(file);
		if(optional.isPresent() && optional.getAsInt()>0 ){
			return Optional.of(file);
		}else{
			return Optional.empty();
		}
	}
}
