package cn.don9cn.blog.plugins.dbbuilder.service;

import cn.don9cn.blog.plugins.dbbuilder.dao.DatabaseBuilderDaoImpl;
import cn.don9cn.blog.plugins.dbbuilder.model.DbTableChecker;
import cn.don9cn.blog.plugins.dbbuilder.util.DatabaseBuilderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxindong
 * @Description: 数据库表格创建工具service
 * @Create: 2017/10/12 10:38
 * @Modify:
 */
@Service
@Transactional
public class DatabaseBuilderServiceImpl{

    @Autowired
    private DatabaseBuilderDaoImpl databaseBuilderDaoImpl;

    @Autowired
    private DatabaseBuilderUtil builderUtil;

    public Map<String, Integer> checkAndBuildDb(List<DbTableChecker> checkerList) {
        Map<String,Integer> resultMap = new HashMap<String, Integer>();
        resultMap.put("checkCount",checkerList.size());
        resultMap.put("result",0);
        List<String> exitsTableNames = databaseBuilderDaoImpl.tableExitsCheck(checkerList.get(0).getDatabaseName());
        for(int i=0;i<exitsTableNames.size();i++){
            exitsTableNames.set(i,exitsTableNames.get(i).toLowerCase());
        }
        int buildCount = 0;
        for(DbTableChecker checker:checkerList){
            if(exitsTableNames.contains(checker.getTableName().toLowerCase())){
                System.out.println(">>>>>>>>>> 数据库表检查结果：" + checker.getTableName().toLowerCase() +
                        " 已存在");
            }else{
                System.out.println(">>>>>>>>>> 数据库表检查结果：" + checker.getTableName().toLowerCase() +
                        " 不存在，开始创建表格");
                buildCount++;
                String sql = builderUtil.getCreateSqlStr(checker);
                //System.out.println(sql);
                databaseBuilderDaoImpl.createDbTable(sql);
                System.out.println(">>>>>>>>>> 数据库表创建结果：" + checker.getTableName() +
                        " 创建成功！");
            }
        }
        resultMap.put("buildCount",buildCount);
        resultMap.put("result",1);
        return resultMap;
    }
}
