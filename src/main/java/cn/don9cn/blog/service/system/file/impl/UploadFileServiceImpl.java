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
	public Optional<Integer> insert(UploadFile entity) {
		return uploadFileDaoImpl.insert(entity);
	}

	@Override
	public Optional<Integer> insertBatch(List<UploadFile> list) {
		return uploadFileDaoImpl.insertBatch(list);
	}

	@Override
	public Optional<Integer> update(UploadFile entity) {
		return uploadFileDaoImpl.update(entity);
	}

	@Override
	public Optional<Integer> deleteById(String id) {
		return uploadFileDaoImpl.deleteById(id);
	}

	@Override
	public Optional<Integer> deleteBatch(List<String> list) {
		return uploadFileDaoImpl.deleteBatch(list);
	}

	@Override
	public Optional<UploadFile> findById(String id) {
		return uploadFileDaoImpl.findById(id);
	}

	@Override
	public Optional<List<UploadFile>> findAll() {
		return uploadFileDaoImpl.findAll();
	}

	@Override
	public Optional<List<UploadFile>> findListByParams(UploadFile entity) {
		return uploadFileDaoImpl.findListByParams(entity);
	}

	@Override
	public Optional<PageResult<UploadFile>> findByPage(PageParamsBean<UploadFile> pageParamsBean) {
		return uploadFileDaoImpl.findByPage(pageParamsBean);
	}

	/**
	 * 使用自己生成的主键,因为需要返回
	 * @param file
	 * @return
	 */
	@Override
	public Optional<UploadFile> insertWithCode(UploadFile file) {
		file.setCode(UuidUtil.getUuid());
		Optional<Integer> optional = uploadFileDaoImpl.insert(file);
		if(optional.isPresent() && optional.get()>0){
			return Optional.of(file);
		}else{
			return Optional.empty();
		}
	}
}
