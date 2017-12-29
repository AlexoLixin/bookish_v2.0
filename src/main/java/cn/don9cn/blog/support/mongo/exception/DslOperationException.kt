package cn.don9cn.blog.support.mongo.exception

/**
 * @Author: liuxindong
 * @Description:
 * @Create: don9 2017/12/24
 * @Modify:
 */
class DslOperationException: RuntimeException {
    constructor(msg:String):super(msg)
    constructor(msg: String,cause: Throwable):super(msg, cause)
}

class MongoClientFactoryInitException:RuntimeException{
    constructor(msg:String):super(msg)
    constructor(msg: String,cause: Throwable):super(msg, cause)
}