package cn.booklish.mongodsl.core

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.io.Serializable

class PageResult<T:Any>:Serializable{

    companion object {
        private const val serialVersionUID = 1L
    }

    constructor(nowPage:Int,pageSize:Int,orderBy:String?){
        this.nowPage = nowPage
        this.pageSize = pageSize
        this.skip = (nowPage - 1) * pageSize
        parseOrderBy(orderBy)
        this.pageRequest = PageRequest(nowPage-1,pageSize,this.orderTurn,this.orderField)
    }

    constructor(nowPage:Int,pageSize:Int,orderBy:String?,entity:T):this(nowPage, pageSize, orderBy){
        this.entity = entity
    }

    constructor(nowPage:Int,pageSize:Int,startTime:String?,endTime:String?,orderBy:String?,entity:T):this(nowPage, pageSize, orderBy, entity){
        this.startTime = startTime
        this.endTime = endTime
    }

    val nowPage:Int
    val pageSize:Int
    var entity:T? = null
    var startTime: String? = null                           //开始时间
    var endTime: String? = null                             //结束时间
    var skip: Int = 0                                       //跳过
    var totalCount: Long = 0                                //总记录数
    var total: Int = 0                                      //总页数
    var orderField: String = "createTime"                   //排序字段
    var orderTurn: Sort.Direction = Sort.Direction.DESC     //排序方式
    var rows: List<T> = emptyList()                         //查询结果集
    var pageRequest: PageRequest

    private fun parseOrderBy(orderBy: String?) {
        orderBy?.let {
            val split = orderBy.split(" ".toRegex())
            this.orderField = split[0]
            when(split[1]){
                "asc" -> this@PageResult.orderTurn = Sort.Direction.ASC
                "desc" -> this@PageResult.orderTurn = Sort.Direction.DESC
                else -> this@PageResult.orderTurn = Sort.Direction.DESC
            }
        }
    }

}
