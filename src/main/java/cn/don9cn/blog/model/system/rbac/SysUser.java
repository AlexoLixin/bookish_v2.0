package cn.don9cn.blog.model.system.rbac;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbTable;
import cn.don9cn.blog.annotation.MapperNameSpace;
import cn.don9cn.blog.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 用户实体
 * @Create: 2017/10/12 8:53
 * @Modify:
 */
@DbTable
@MapperNameSpace(namespace = "cn.don9cn.blog.model.system.rbac.SysUser.mapper")
public class SysUser extends BaseModel implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    @DbColumn(allowNull = false,content = "用户名")
    private String username;
    /**
     * 密码
     */
    @DbColumn(allowNull = false,content = "密码")
    private String password;
    /**
     * 邮箱
     */
    @DbColumn(allowNull = false,content = "邮箱")
    private String email;
    /**
     * 手机号码
     */
    @DbColumn(content = "手机号码")
    private String phone;
    /**
     * 真实姓名
     */
    @DbColumn(content = "真实姓名")
    private String realName;
    /**
     * 年龄
     */
    @DbColumn(length = "10",content = "年龄")
    private Integer age;
    /**
     * 性别
     */
    @DbColumn(length = "10",content = "性别")
    private String dicGender;
    /**
     * 识别码,用于忘记密码后的帐号找回
     */
    @DbColumn(content = "识别码")
    private String icNum;

    /**
     * 角色集合
     */
    private List<SysRole> roleList ;

    /**
     * 角色名称,用于前端显示
     */
    private String rolesStr;


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

    public String getDicGender() {
        return dicGender;
    }

    public void setDicGender(String dicGender) {
        this.dicGender = dicGender;
    }

    public String getIcNum() {
        return icNum;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public String getRolesStr() {
        return rolesStr;
    }

    public void setRolesStr(String rolesStr) {
        this.rolesStr = rolesStr;
    }
}
