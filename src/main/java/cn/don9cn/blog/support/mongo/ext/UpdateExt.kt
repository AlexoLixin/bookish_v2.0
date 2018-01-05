package cn.don9cn.blog.support.mongo.ext

import org.springframework.data.mongodb.core.query.Update


object update{
    operator fun invoke(updateMapping: UpdateMapping):Update = Update().apply { checkMappingType(updateMapping) }
}

fun Update.and(updateMapping: UpdateMapping):Update = apply { checkMappingType(updateMapping) }

fun Update.checkMappingType(updateMapping: UpdateMapping):Update = apply {
    when(updateMapping.updateType){
        UpdateType.SET -> set(updateMapping.key,updateMapping.value)
        UpdateType.PUSH -> push(updateMapping.key,updateMapping.value)
        UpdateType.PULL -> pull(updateMapping.key,updateMapping.value)
    }
}

data class UpdateMapping(val key:String,val value:Any,val updateType: UpdateType)

enum class UpdateType{
    SET,PUSH,PULL
}

infix fun String.set(value: Any): UpdateMapping = UpdateMapping(this, value, UpdateType.SET)

infix fun String.push(value: Any): UpdateMapping = UpdateMapping(this, value, UpdateType.PUSH)

infix fun String.pull(value: Any): UpdateMapping = UpdateMapping(this, value, UpdateType.PULL)

