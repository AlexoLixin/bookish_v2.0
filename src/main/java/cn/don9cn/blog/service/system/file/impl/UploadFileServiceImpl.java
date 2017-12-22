package cn.don9cn.blog.service.system.file.impl;

import cn.don9cn.blog.dao.bussiness.article.interf.ArticleAndFileDao;
import cn.don9cn.blog.dao.system.file.interf.UploadFileDao;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.support.operaresult.core.OperaResult;
import cn.don9cn.blog.support.daohelper.core.PageResult;
import cn.don9cn.blog.support.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.system.file.interf.UploadFileService;
import cn.don9cn.blog.util.MyStringUtil;
import cn.don9cn.blog.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
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
	private UploadFileDao uploadFileDao;

	@Autowired
	private ArticleAndFileDao articleAndFileDao;


	@Override
	public OperaResult baseInsert(UploadFile entity) {
		return OperaResultUtil.insert(uploadFileDao.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<UploadFile> list) {
		return OperaResultUtil.insertBatch(uploadFileDao.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(UploadFile entity) {
		return OperaResultUtil.update(uploadFileDao.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.deleteOne(uploadFileDao.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			return OperaResultUtil.deleteBatch(uploadFileDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		return OperaResultUtil.findOne(uploadFileDao.baseFindById(id));
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.findAll(uploadFileDao.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(UploadFile entity) {
		return OperaResultUtil.findListByParams(uploadFileDao.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<UploadFile> pageResult) {
		Optional<PageResult<UploadFile>> page = uploadFileDao.baseFindByPage(pageResult);
		page.ifPresent(pageResult1 ->
			pageResult1.getRows().forEach(uploadFile -> articleAndFileDao.fillLink(uploadFile))
		);
		return OperaResultUtil.findPage(page);
	}

	/**
	 * 使用自己生成的主键,因为需要返回
	 * @param file
	 * @return
	 */
	@Override
	public OperaResult insertWithCode(UploadFile file) {
		file.setCode(UuidUtil.getUuid());
		OptionalInt optional = uploadFileDao.baseInsert(file);
		if(optional.isPresent() && optional.getAsInt()>0 ){
			return new OperaResult(true,"文件上传成功").setObj(file);
		}else{
			return new OperaResult(false,"文件上传信息保存至服务器失败");
		}
	}
}
