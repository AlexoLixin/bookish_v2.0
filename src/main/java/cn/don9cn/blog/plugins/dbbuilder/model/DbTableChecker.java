package cn.don9cn.blog.plugins.dbbuilder.model;


import cn.don9cn.blog.annotation.MapperNameSpace;

import java.io.Serializable;

/**
 * @Author: liuxindong
 * @Description: 数据库表格检查
 * @Create: 2017/10/12 9:40
 * @Modify:
 */
@MapperNameSpace(namespace = "cn.don9cn.blog.plugins.dbbuilder.model.DbTableChecker.mapper")
public class DbTableChecker implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    private String databaseName;

    private String tableName;

    private String result;

    private Class modelClazz;

    public DbTableChecker(String databaseName,String tableName,Class modelClazz){
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.modelClazz = modelClazz;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Class getModelClazz() {
        return modelClazz;
    }

    public void setModelClazz(Class modelClazz) {
        this.modelClazz = modelClazz;
    }

    @Override
    public String toString() {
        return this.databaseName + " " + this.tableName + " " + this.modelClazz.getSimpleName();
    }
}
