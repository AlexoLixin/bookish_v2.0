package cn.don9cn.blog.plugins.dbbuilder.util;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbTable;
import cn.don9cn.blog.plugins.daohelper.util.FieldParserUtil;
import cn.don9cn.blog.plugins.dbbuilder.model.ColumnMaker;
import cn.don9cn.blog.plugins.dbbuilder.model.DbTableChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Author: liuxindong
 * @Description: 数据库表格创建工具
 * @Create: 2017/10/12 10:38
 * @Modify:
 */
@Component
@Configuration
public class DatabaseBuilderUtil {

    @Value("${dbBuilder.databaseName}")
    private String databaseName;

    @Value("${dbBuilder.modelPackage}")
    private String modelPackage;

    /**
     * 读取数据库生成工具的配置文件，加载数据库名称和model包
     * @return
     */
    public Map<String, String> loadBuilderConfig(){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("databaseName",databaseName);
        resultMap.put("modelPackage",modelPackage);
        return resultMap;
    }

    /**
     * 生成建表sql语句
     * @param checker
     * @return
     */
    public String getCreateSqlStr(DbTableChecker checker){
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append("CREATE TABLE "+checker.getTableName()+"(");
        LinkedList<Field> fieldList = new LinkedList<Field>();
        boolean reverseAdd = false;
        getDbcolumnFields(checker.getModelClazz(),fieldList,reverseAdd);
        for(Field field:fieldList){
            ColumnMaker columnMaker = new ColumnMaker(checker.getDatabaseName(),checker.getTableName(),field);
            sqlSB.append(columnMaker.getColumnName()+" "+columnMaker.getType()+columnMaker.getLength()+" "+
                    columnMaker.getPrimaryKey()+" "+columnMaker.getAllowNull()+" COMMENT "+"\'"+columnMaker.getContent()+"\'"+",");
        }
        String resultSql = sqlSB.toString();
        resultSql = resultSql.substring(0,resultSql.length()-1)+")";
        return resultSql;
    }



    /**
     * 得到所有需要创建表的 DbTableChecker
     * @param allClasses
     * @return
     */
    public List<DbTableChecker> getAllTableNames(List<Class> allClasses,String databaseName){
        List<DbTableChecker> resultList = new ArrayList<>();
        for(Class clazz:allClasses){
            String tableName = FieldParserUtil.parse2DbName(clazz.getSimpleName());
            DbTableChecker dbChecker = new DbTableChecker(databaseName,tableName,clazz);
            resultList.add(dbChecker);
        }
        return resultList;
    }

    /**
     * 获得所有需要创建成表的model类
     * @param allClasses
     * @return
     */
    public List<Class> getAllDbtableClasses(List<Class> allClasses){
        List<Class> resultList = new ArrayList<Class>();
        for(Class clazz:allClasses){
            DbTable annotation = (DbTable) clazz.getAnnotation(DbTable.class);
            if(annotation!=null){
                resultList.add(clazz);
            }
        }
        return resultList;
    }

    /**
     * 递归获取class中及其父类所有需要生成表中列的字段
     * @param clazz
     * @param fieldsList
     */
    public void getDbcolumnFields(Class clazz,LinkedList<Field> fieldsList,boolean reverseAdd){
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            DbColumn annotation = field.getAnnotation(DbColumn.class);
            if(annotation!=null){
                if(reverseAdd){
                    fieldsList.addFirst(field);
                }else{
                    fieldsList.add(field);
                }
            }
        }
        if(clazz.getSuperclass()!=null){
            reverseAdd = true;
            getDbcolumnFields(clazz.getSuperclass(),fieldsList,reverseAdd);
        }
    }

    /**
     * 读取指定package下的所有class文件
     * @param packageName
     */
    public List<Class> loadAllClassesByPackage(String packageName) {

        //存放所有class的list集合
        List<Class> classList = new ArrayList<Class>();

        // 是否循环搜索子包
        boolean recursive = true;

        //将包名转化为包路径
        String packagePath = packageName.replaceAll("\\.","/");

        //System.out.println(packagePath);

        Enumeration<URL> dir;

        try {
            //加载当前的包路径文件
            dir = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while(dir.hasMoreElements()){
                //获取加载的url
                URL url = dir.nextElement();
                //得到当前资源的类型,file或者jar
                String protocol = url.getProtocol();
                //如果是file,则加载的是当前的package包
                if(protocol.equals("file")){
                    //获取文件路径
                    String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                    //遍历package下的所有class,包括其子包下的,添加到list中
                    findClassInPackage(packageName,filePath,recursive,classList);
                }
            }
        } catch (IOException e) {
            System.out.println("******* 加载包失败 ! *******");
            throw new RuntimeException(e);
        }

        return classList;
    }

    /**
     *  遍历package下的所有calss,包括其子包下的,添加到list中
     * @param packageName
     * @param filePath
     * @param recursive
     * @param classList
     */
    private void findClassInPackage(String packageName, String filePath, final boolean recursive, List<Class> classList) {
        File dir = new File(filePath);
        //如果file不存在或者不是目录,直接跳出
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] listFiles = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                //过滤出目录和class文件
                boolean acceptDir = recursive && file.isDirectory();
                boolean acceptClass = recursive && file.getName().endsWith(".class");
                return acceptDir || acceptClass;
            }
        });
        for(File file:listFiles){
            if(file.isDirectory()){
                //如果还是目录,继续递归
                findClassInPackage(packageName+"."+file.getName(),file.getAbsolutePath(),recursive,classList);
            }else{
                //如果是class文件,获取文件名称,去掉后缀
                String className = file.getName().substring(0,file.getName().length()-6);
                try {
                    //加载class文件,添加到list中
                    classList.add(Thread.currentThread().getContextClassLoader().loadClass(packageName+"."+className));
                } catch (ClassNotFoundException e) {
                    System.out.println("******* 加载class文件失败 ! *******");
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
