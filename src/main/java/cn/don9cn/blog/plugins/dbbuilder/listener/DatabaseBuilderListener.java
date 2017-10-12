package cn.don9cn.blog.plugins.dbbuilder.listener;

import cn.don9cn.blog.plugins.dbbuilder.model.DbTableChecker;
import cn.don9cn.blog.plugins.dbbuilder.service.DatabaseBuilderServiceImpl;
import cn.don9cn.blog.plugins.dbbuilder.util.DatabaseBuilderUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxindong
 * @Description: 数据库表格创建工具监听器,程序启动时自动检查数据库表格
 * @Create: 2017/10/12 9:50
 * @Modify:
 */
public class DatabaseBuilderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext servletContext = servletContextEvent.getServletContext();

        System.out.println("----------- 开始检查数据库表格 -----------");

        long starTime=System.currentTimeMillis();

        //检查数据库表
        Map<String, Integer> resultMap = mainHandle(servletContext);

        long endTime=System.currentTimeMillis();

        System.out.println("----------- 数据库表格检查及创建耗时："
                +(endTime-starTime)+" ms, 检查表数: "+resultMap.get("checkCount")+", 新建表数: "+resultMap.get("buildCount")
                +" -----------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * 主要业务处理流程
     */
    private Map<String, Integer> mainHandle(ServletContext sc){

        // 获取spring容器
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);

        // 获取工具类
        DatabaseBuilderUtil databaseBuilderUtil = (DatabaseBuilderUtil) ac.getBean("databaseBuilderUtil");

        // 获取service
        DatabaseBuilderServiceImpl dbBuilderService = (DatabaseBuilderServiceImpl) ac.getBean("databaseBuilderServiceImpl");

        // 加载配置文件
        Map<String, String> builderConfig = databaseBuilderUtil.loadBuilderConfig();

        // 加载modelPackage下的所有model
        List<Class> allClassesByPackage = databaseBuilderUtil.loadAllClassesByPackage(builderConfig.get("modelPackage"));

        // 获取需要建表的model
        List<Class> allDbtableClasses = databaseBuilderUtil.getAllDbtableClasses(allClassesByPackage);

        // 创建tableChecker实体
        List<DbTableChecker> allDbTableChecker = databaseBuilderUtil.getAllTableNames(allDbtableClasses,builderConfig.get("databaseName"));

        // 检查并创建表格
        Map<String, Integer> resultMap = dbBuilderService.checkAndBuildDb(allDbTableChecker);

        if(resultMap.get("result")==1){
            System.out.println("----------- 数据库表格检查及创建完成！-----------");
        }else{
            System.out.println("----------- 数据库表格检查及创建失败，请检查错误！-----------");
        }
        return resultMap;
    }
}
