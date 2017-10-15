package cn.don9cn.blog.exception;

/**
 * @Author: liuxindong
 * @Description: MyMongoOperator异常,用于定位mongoTemplate操作抛出的异常
 * @Create: don9 2017/10/15
 * @Modify:
 */
public class MyMongoOperatorException extends ExceptionWrapper{
    public MyMongoOperatorException(Exception e, String s) {
        super(e,s);
    }
}
