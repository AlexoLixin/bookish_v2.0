package cn.don9cn.blog.util

import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

/**
 * java日期类的扩展
 */

private val datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")!!

val Int.days: Period
    get() = Period.ofDays(this)

val Period.ago: LocalDateTime
    get() = LocalDateTime.now() - this

val Period.fromNow:LocalDateTime
    get() = LocalDateTime.now() + this

val LocalDateTime.pattern: String
    get() = this.format(datePattern)

fun getNowDate() = LocalDateTime.now().format(datePattern)
