package cn.don9cn.blog.exception;

/**
 * @Author: liuxindong
 * @Description: 异常包装类,封装catch中抛出的异常,并且附带自定义的异常信息
 * @Create: 2017/10/9 10:11
 * @Modify:
 */
public class ExceptionWrapper extends RuntimeException {

    private String info;

    private Exception exception;

    public ExceptionWrapper(){
        super();
    }

    public ExceptionWrapper(Exception exception,String info) {
        this.info = info;
        this.exception = exception;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return info + " " + exception.getMessage();
    }
}
