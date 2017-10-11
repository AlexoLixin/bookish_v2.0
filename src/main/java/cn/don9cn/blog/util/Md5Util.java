package cn.don9cn.blog.util;

import java.security.MessageDigest;

/**
 * @author Don9
 * @create 2017-08-18-9:43
 **/
public class Md5Util {

    /**
     * 生成md5加密字符
     * @param string
     * @return
     */
    public static String getMD5(String string){
        StringBuilder sb=new StringBuilder();
        try {
            MessageDigest messageDigest= MessageDigest.getInstance("md5");
            byte[] bytes= messageDigest.digest(string.getBytes());
            for(int i=0;i<bytes.length;i++){
                int tempInt=bytes[i]&0xff;
                if(tempInt<16){
                    sb.append(0);
                }
                sb.append(Integer.toHexString(tempInt));
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }

}
