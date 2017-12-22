package cn.don9cn.blog.service.system.log.impl;

import cn.don9cn.blog.dao.system.log.interf.SysLoginLogDao;
import cn.don9cn.blog.model.system.log.SysLoginLog;
import cn.don9cn.blog.support.daohelper.core.PageResult;
import cn.don9cn.blog.support.operaresult.core.OperaResult;
import cn.don9cn.blog.support.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.system.log.interf.SysLoginLogService;
import cn.don9cn.blog.util.DateUtil;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: 登录日志service接口实现
 * @Create: 2017/10/18 10:10
 * @Modify:
 */
@Service
@Transactional
public class SysLoginLogServiceImpl implements SysLoginLogService {

	@Autowired
	private SysLoginLogDao sysLoginLogDao;


	@Override
	public OperaResult baseInsert(SysLoginLog entity) {
		return OperaResultUtil.insert(sysLoginLogDao.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<SysLoginLog> list) {
		return OperaResultUtil.insertBatch(sysLoginLogDao.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(SysLoginLog entity) {
		return OperaResultUtil.update(sysLoginLogDao.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.deleteOne(sysLoginLogDao.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			return OperaResultUtil.deleteBatch(sysLoginLogDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		return OperaResultUtil.findOne(sysLoginLogDao.baseFindById(id));
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.findAll(sysLoginLogDao.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(SysLoginLog entity) {
		return OperaResultUtil.findListByParams(sysLoginLogDao.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<SysLoginLog> pageResult) {
		return OperaResultUtil.findPage(sysLoginLogDao.baseFindByPage(pageResult));
	}

	@Override
	public OperaResult doRemoveEarly30() {
		return OperaResultUtil.deleteBatch(sysLoginLogDao.doRemoveEarly30(DateUtil.getEarly30Date()));
	}
}
