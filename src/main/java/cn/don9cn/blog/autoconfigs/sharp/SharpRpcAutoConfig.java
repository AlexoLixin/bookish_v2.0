package cn.don9cn.blog.autoconfigs.sharp;

import cn.don9cn.blog.autoconfigs.spring.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Don9
 * @create 2017-12-11-13:44
 **/
/*@Configuration
@AutoConfigureAfter(SpringUtil.class)*/
public class SharpRpcAutoConfig {

   /* @Bean
    public SharpRpcConfig sharpRpcConfig(){
        SharpRpcConfig config = new SharpRpcConfig(SpringUtil::getBean);
        config.loadProperties("sharp.properties");
        config.configure();
        return config;
    }*/

}
