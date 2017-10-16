package cn.don9cn.blog.service.system.rbac.impl;

import cn.don9cn.blog.dao.system.rbac.SysRoleDaoImpl;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operation.core.OperaResult;
import cn.don9cn.blog.plugins.operation.util.OperaResultUtil;
import cn.don9cn.blog.service.system.rbac.interf.SysRoleService;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: 角色service实现类
 * @Create: 2017/10/16 10:04
 * @Modify:
 */
@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDaoImpl sysRoleDaoImpl;


	@Override
	public OperaResult baseInsert(SysRole entity) {
		return OperaResultUtil.baseInsert(sysRoleDaoImpl.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<SysRole> list) {
		return OperaResultUtil.baseInsertBatch(sysRoleDaoImpl.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(SysRole entity) {
		return OperaResultUtil.baseUpdate(sysRoleDaoImpl.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.baseRemove(sysRoleDaoImpl.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			return OperaResultUtil.baseRemoveBatch(sysRoleDaoImpl.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		return OperaResultUtil.baseFindOne(sysRoleDaoImpl.baseFindById(id));
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.baseFindAll(sysRoleDaoImpl.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(SysRole entity) {
		return OperaResultUtil.baseFindListByParams(sysRoleDaoImpl.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<SysRole> pageResult) {
		return OperaResultUtil.baseFindByPage(sysRoleDaoImpl.baseFindByPage(pageResult));
	}
}
