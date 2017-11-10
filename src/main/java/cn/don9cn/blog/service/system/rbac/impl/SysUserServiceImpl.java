package cn.don9cn.blog.service.system.rbac.impl;

import cn.don9cn.blog.autoconfigs.activemq.core.MqConstant;
import cn.don9cn.blog.autoconfigs.activemq.core.MqProducer;
import cn.don9cn.blog.autoconfigs.activemq.model.MailMessage;
import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.dao.system.rbac.interf.SysRoleDao;
import cn.don9cn.blog.dao.system.rbac.interf.SysUserDao;
import cn.don9cn.blog.model.system.LoginResult;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.plugins.validatecode.ValidateCodeCache;
import cn.don9cn.blog.service.system.rbac.interf.SysUserService;
import cn.don9cn.blog.util.MyStringUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

	@Autowired
	private MqConstant mqConstant;

	@Autowired
	private MqProducer mqProducer;

	@Override
	public OperaResult baseInsert(SysUser entity) {
		entity.setByWay("管理员添加");
		sysRoleDao.findByRoleEncoding("ROLE_USER")
				.ifPresent(sysRole -> entity.setRoleCodes(Lists.newArrayList(sysRole.getCode())));
		return OperaResultUtil.insert(sysUserDao.baseInsert(entity));
	}

	@Override
	public OperaResult baseInsertBatch(List<SysUser> list) {
		return OperaResultUtil.insertBatch(sysUserDao.baseInsertBatch(list));
	}

	@Override
	public OperaResult baseUpdate(SysUser entity) {
		return OperaResultUtil.update(sysUserDao.baseUpdate(entity));
	}

	@Override
	public OperaResult baseDeleteById(String id) {
		return OperaResultUtil.deleteOne(sysUserDao.baseDeleteById(id));
	}

	@Override
	public OperaResult baseDeleteBatch(String codes) {
		if(StringUtils.isNotBlank(codes)){
			List<String> codesList = MyStringUtil.codesStr2List(codes);
			return OperaResultUtil.deleteBatch(sysUserDao.baseDeleteBatch(codesList));
		}else{
			return new OperaResult(false,"删除失败,传入codes为空!");
		}
	}

	@Override
	public OperaResult baseFindById(String id) {
		Optional<SysUser> sysUserOptional = sysUserDao.baseFindById(id);
		sysUserOptional.ifPresent(sysUser -> {
			if(sysUser.getRoleCodes()!=null && sysUser.getRoleCodes().size()>0){
				Optional<List<SysRole>> roleListOptional = sysRoleDao.baseFindListInIds(sysUser.getRoleCodes());
				roleListOptional.ifPresent(roleList -> {
					sysUser.setRoleList(roleList);
					sysUser.setRoleNames(roleList.stream().map(SysRole::getName).reduce("",(s1,s2)-> s1+" "+s2));
				});
			}
		});
		return OperaResultUtil.findOne(sysUserOptional);
	}

	@Override
	public OperaResult baseFindAll() {
		return OperaResultUtil.findAll(sysUserDao.baseFindAll());
	}

	@Override
	public OperaResult baseFindListByParams(SysUser entity) {
		return OperaResultUtil.findListByParams(sysUserDao.baseFindListByParams(entity));
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
		return OperaResultUtil.findPage(resultOptional);
	}

	@Override
	public OperaResult findByUserName(String username) {
		Optional<SysUser> userOptional = sysUserDao.findByUserName(username);
		userOptional.ifPresent(sysUser -> {
			if(sysUser.getRoleCodes()!=null){
				sysRoleDao.baseFindListInIds(sysUser.getRoleCodes()).ifPresent(roleList -> {
					sysUser.setRoleList(roleList);
					sysUser.setRoleNames(roleList.stream().map(SysRole::getName).reduce("",(s1,s2)-> s1+" "+s2));
				});
			}
		});
		return OperaResultUtil.findOne(userOptional);
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
		return OperaResultUtil.update(sysUserDao.baseUpdate(user));
	}

	@Override
	public OperaResult getUserInfo() {
		return OperaResultUtil.findOne(sysUserDao.baseFindById(MyShiroSessionUtil.getUserCodeFromSession()));
	}

	@Override
	public OperaResult updateUserInfo(SysUser sysUser) {
		if(sysUser.getCode().equals(MyShiroSessionUtil.getUserCodeFromSession())){
			return OperaResultUtil.update(sysUserDao.baseUpdate(sysUser));
		}
		return new OperaResult(false,"更新失败");
	}

	@Override
	public OperaResult register(String validateCode, SysUser sysUser) {
		//先校验验证码
		if(StringUtils.isBlank(validateCode) || !ValidateCodeCache.validate(validateCode)){
			return new OperaResult(false,"验证码校验失败");
		}
		//校验邮箱
		String regex = "\\w+@\\w+(.\\w+)+";
		if(!MyStringUtil.match(regex,sysUser.getEmail())){
			return new OperaResult(false,"邮箱校验失败");
		}
		//校验用户名
		if(!checkUserName(sysUser.getUsername()).isSuccess()){
			return new OperaResult(false,"用户名不可用");
		}

		//保存用户信息到数据库
		sysUser.setByWay("用户注册");
		sysRoleDao.findByRoleEncoding("ROLE_USER")
				.ifPresent(sysRole -> sysUser.setRoleCodes(Lists.newArrayList(sysRole.getCode())));

		OperaResult result = OperaResultUtil.insert(sysUserDao.baseInsert(sysUser));

		//注册成功后,推送信息到activeMq
		if(result.isSuccess()){
			try{
				mqProducer.pushToTopic(mqConstant.MAIL_MSG_TOPIC,new MailMessage(sysUser.getUsername(),sysUser.getEmail()));
			}catch (Exception e){
				// TODO
				//将用户的注册信息推送到activeMq失败,暂时先不处理,等完善好了异常处理系统再修改
				System.out.println("新用户的注册消息推送到activeMq失败!");
			}
		}

		return result;
	}
}
