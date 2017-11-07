package cn.don9cn.blog.test.guava;


import com.google.common.base.Throwables;

/**
 * @author Don9
 * @create 2017-11-03-9:34
 **/
public class ObjectsTest {

    public static void main(String[] args) {
       try {
           int i = 5/0;
       }catch (Exception e){
           String cause = Throwables.getStackTraceAsString(e);
           System.out.println(cause);
       }
    }


}
