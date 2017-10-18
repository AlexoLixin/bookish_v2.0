package cn.don9cn.blog.service.system.rbac.impl;

import cn.don9cn.blog.dao.system.rbac.impl.SysRoleDaoImpl;
import cn.don9cn.blog.dao.system.rbac.impl.SysUserDaoImpl;
import cn.don9cn.blog.dao.system.rbac.interf.SysRoleDao;
import cn.don9cn.blog.dao.system.rbac.interf.SysUserDao;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.system.rbac.interf.SysUserService;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 *@Author: liuxindong
 *@Description: 用户service接口实现类
 *@Create: 2017/10/15 19:20
 *@Modify:
 **/
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysRoleDao sysRoleDao;


	@Override
	public OperaResult baseInsert(SysUser entity) {
		return OperaResultUtil.baseInsert(sysUserDao.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<SysUser> list) {
		return OperaResultUtil.baseInsertBatch(sysUserDao.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(SysUser entity) {
		return OperaResultUtil.baseUpdate(sysUserDao.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.baseRemove(sysUserDao.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			return OperaResultUtil.baseRemoveBatch(sysUserDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		return OperaResultUtil.baseFindOne(sysUserDao.baseFindById(id));
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.baseFindAll(sysUserDao.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(SysUser entity) {
		return OperaResultUtil.baseFindListByParams(sysUserDao.baseFindListByParams(entity));
	}

	@Override
	public OperaResult baseFindByPage(PageResult<SysUser> pageResult) {
		Optional<PageResult<SysUser>> resultOptional = sysUserDao.baseFindByPage(pageResult);
		resultOptional.ifPresent(page ->
			page.getRows().forEach(sysUser -> {
				List<String> roleCodes = sysUser.getRoleCodes();
				if(roleCodes!=null) {
					Optional<List<SysRole>> roleListOptional = sysRoleDao.baseFindListInIds(roleCodes);
					roleListOptional.ifPresent(roleList -> {
						sysUser.setRoleList(roleList);
						sysUser.setRoleNames(roleList.stream().map(SysRole::getName).reduce("",(s1,s2)-> s1+" "+s2));
					});
				}
			})
		);
		return OperaResultUtil.baseFindByPage(resultOptional);
	}

	@Override
	public OperaResult findByUserName(String username) {
		Optional<SysUser> userOptional = sysUserDao.findByUserName(username);
		userOptional.ifPresent(sysUser -> {
			if(sysUser.getRoleCodes()!=null){
				sysRoleDao.baseFindListInIds(sysUser.getRoleCodes()).ifPresent(sysUser::setRoleList);
			}
		});
		return OperaResultUtil.baseFindOne(userOptional);
	}

	@Override
	public OperaResult checkUserName(String username) {
		if(sysUserDao.checkUserName(username)){
			return new OperaResult(true,"用户名可用");
		}
		return new OperaResult(false,"用户名不可用");
	}

	@Override
	public OperaResult authorizeUser(String userCode, String roleCodes) {
		SysUser user;
		if(StringUtils.isNotBlank(roleCodes)){
			user = new SysUser(userCode,roleCodes);
		}else{
			user = new SysUser(userCode,"");
		}
		return OperaResultUtil.baseUpdate(sysUserDao.baseUpdate(user));
	}
}