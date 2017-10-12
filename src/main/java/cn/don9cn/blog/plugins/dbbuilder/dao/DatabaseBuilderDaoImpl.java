package cn.don9cn.blog.plugins.dbbuilder.dao;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.plugins.dbbuilder.model.DbTableChecker;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 数据库表格创建工具dao
 * @Create: 2017/10/12 11:02
 * @Modify:
 */
@Repository
public class DatabaseBuilderDaoImpl implements BaseDao<DbTableChecker> {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<String> tableExitsCheck(String databaseName) {
        return sqlSessionTemplate.selectList(getSqlName("checkTableExits"),databaseName);
    }

    public void createDbTable(String sql) {
        sqlSessionTemplate.update(getSqlName("createTableBySql"),sql);
    }
}
