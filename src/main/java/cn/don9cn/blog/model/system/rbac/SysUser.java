package cn.don9cn.blog.model.system.rbac;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.util.MyStringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 用户实体
 * @Create: 2017/10/12 8:53
 * @Modify:
 */
@DbCollection
public class SysUser extends BaseModel {


    @DbColumn(content = "用户名")
    private String username;

    @DbColumn(content = "密码")
    private String password;

    @DbColumn(content = "邮箱")
    private String email;

    @DbColumn(content = "手机号码")
    private String phone;

    @DbColumn(content = "真实姓名")
    private String realName;

    @DbColumn(content = "年龄")
    private Integer age;

    @DbColumn(content = "性别")
    private String gender;

    @DbColumn(content = "创建方式")
    private String byWay;

    /**
     * 角色主键
     */
    private List<String> roleCodes;

    /**
     * 角色实体集合
     */
    private List<SysRole> roleList;

    /**
     * 角色名称,用于前端显示
     */
    private String roleNames;

    public SysUser(){

    }

    public SysUser(String code){
        this.setCode(code);
    }

    public SysUser(String code, String roleCodes){
        this.setCode(code);
        this.roleCodes = MyStringUtil.codesStr2List(roleCodes);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getByWay() {
        return byWay;
    }

    public void setByWay(String byWay) {
        this.byWay = byWay;
    }
}