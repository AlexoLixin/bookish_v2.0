package cn.don9cn.blog.plugins.dbbuilder.model;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.plugins.daohelper.util.FieldParserUtil;

import java.lang.reflect.Field;

/**
 * @Author: liuxindong
 * @Description: 数据库建表插件辅助类-用于生成column的生成sql
 * @Create: 2017/10/12 9:39
 * @Modify:
 */
public class ColumnMaker {

    private String databaseName = "";

    private String tableName = "";

    private String columnName = "";

    private String allowNull = "";

    private String primaryKey = "";

    private String type = "";

    private String length = "";

    private String content = "";

    public ColumnMaker(String databaseName,String tableName,Field field){
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.columnName = FieldParserUtil.parse2DbName(field.getName());
        DbColumn annotation = field.getAnnotation(DbColumn.class);
        //通过字段注解信息构建实体
        if(annotation!=null){
            //如果是主键,直接设置,不用管是否允许空
           if(annotation.primaryKey()){
                this.primaryKey = "PRIMARY KEY";
           }else{
               //不是主键,根据注解信息设置是否允许空
                if(annotation.allowNull()){
                    this.allowNull = "NULL";


                }else{
                    this.allowNull = "NOT NULL";
                }
           }
           //设置长度
           if(annotation.hasLength()){
               this.length = "("+ annotation.length() +")";
           }
           //设置字段类型
           if(annotation.type()!=null){
                this.type = annotation.type();
           }
           //设置列说明
           if(annotation.content()!=null){
                this.content = annotation.content();
           }
        }
    }

    @Override
    public String toString() {
        return "tableName="+this.tableName +" columnName="+ this.columnName +" primaryKey="+ this.primaryKey +" type="+  this.type
                +" length="+ this.length +" allowNull="+  this.allowNull +" content="+  this.content;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getAllowNull() {
        return allowNull;
    }

    public void setAllowNull(String allowNull) {
        this.allowNull = allowNull;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
