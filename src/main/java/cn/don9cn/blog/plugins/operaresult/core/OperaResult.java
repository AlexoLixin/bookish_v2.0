package cn.don9cn.blog.plugins.operaresult.core;


import java.io.Serializable;

/**
 * @author Don9
 * @create 2017-08-08-16:22
 **/
public class OperaResult implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    private boolean success = false;

    private String message;

    private Object obj;

    public OperaResult(){
    }

    public OperaResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public OperaResult setObj(Object obj){
        this.obj = obj;
        return this;
    }

    public Object getObj() {
        return obj;
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
}
