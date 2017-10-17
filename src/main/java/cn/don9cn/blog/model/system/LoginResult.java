package cn.don9cn.blog.model.system;


/**
 * @Author: liuxindong
 * @Description: 登陆结果返回实体
 * @Create: 2017/10/17 16:49
 * @Modify:
 */
public class LoginResult {

    private boolean success;

    private String message;

    private String token;

    private boolean admin;

    private Object user;

    public LoginResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public LoginResult setToken(String token) {
        this.token = token;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public LoginResult setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }


    public Object getUser() {
        return user;
    }

    public LoginResult setUser(Object user) {
        this.user = user;
        return this;
    }

}
