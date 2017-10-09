package cn.don9cn.blog.plugins.actionmsg.core;

import java.io.Serializable;

/**
 * @author Don9
 * @create 2017-08-08-16:22
 **/
public class ActionMsg implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    private final boolean success;

    private final String message;

    private Object obj;

    public ActionMsg(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public ActionMsg setObj(Object obj){
        this.obj = obj;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }


    public String getMessage() {
        return message;
    }


    public Object getObj() {
        return obj;
    }
}
