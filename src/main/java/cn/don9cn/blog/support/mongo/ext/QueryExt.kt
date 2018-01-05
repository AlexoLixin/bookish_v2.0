package cn.don9cn.blog.support.mongo.ext

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

object query{
    operator fun invoke(criteria:Criteria): Query = Query().apply { addCriteria(criteria) }
}

fun Query.and(criteria:Criteria):Query = apply { addCriteria(criteria) }

infix fun String.eq(value: Any):Criteria = Criteria(this).`is`(value)

infix fun String.inThe(list: List<Any>):Criteria = Criteria(this).`in`(list)

infix fun String.gt(value: Any):Criteria = Criteria(this).gt(value)

infix fun String.lt(value: Any):Criteria = Criteria(this).lt(value)

infix fun String.like(value: String):Criteria = Criteria(this).regex(value)
