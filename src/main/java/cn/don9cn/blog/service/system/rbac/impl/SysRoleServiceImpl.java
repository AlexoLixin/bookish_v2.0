package cn.don9cn.blog.service.system.rbac.impl;

import cn.don9cn.blog.dao.system.rbac.interf.SysPermissionDao;
import cn.don9cn.blog.dao.system.rbac.interf.SysRoleDao;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.system.rbac.interf.SysRoleService;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysPermissionDao sysPermissionDao;


	@Override
	public OperaResult baseInsert(SysRole entity) {
		return OperaResultUtil.insert(sysRoleDao.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<SysRole> list) {
		return OperaResultUtil.insertBatch(sysRoleDao.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(SysRole entity) {
		return OperaResultUtil.update(sysRoleDao.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.deleteOne(sysRoleDao.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			return OperaResultUtil.deleteBatch(sysRoleDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		return OperaResultUtil.findOne(sysRoleDao.baseFindById(id));
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.findAll(sysRoleDao.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(SysRole entity) {
		return OperaResultUtil.findListByParams(sysRoleDao.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<SysRole> pageResult) {
		return OperaResultUtil.findPage(sysRoleDao.baseFindByPage(pageResult));
	}

	@Override
	public OperaResult authorizeRole(String roleCode, String permissionCodes) {
		SysRole role;
		if(StringUtils.isNotBlank(permissionCodes)){
			role = new SysRole(roleCode,permissionCodes);
		}else{
			role = new SysRole(roleCode,"");
		}
		return OperaResultUtil.update(sysRoleDao.baseUpdate(role));
	}

	@Override
	public OperaResult findRoleWithPermissions(String code) {
		Optional<SysRole> sysRoleOptional = sysRoleDao.baseFindById(code);
		sysRoleOptional.ifPresent(sysRole -> {
			if(sysRole.getMenuCodesList()!=null){
				Optional<List<SysPermission>> optional = sysPermissionDao.baseFindListInIds(sysRole.getMenuCodesList());
				optional.ifPresent(sysRole::setMenuList);
			}
		});
		return OperaResultUtil.findOne(sysRoleOptional);
	}
}
