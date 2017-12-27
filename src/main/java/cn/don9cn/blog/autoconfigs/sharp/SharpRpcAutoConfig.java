package cn.don9cn.blog.autoconfigs.sharp;

import cn.don9cn.blog.autoconfigs.spring.SpringUtil;
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

    /*@Bean
    public SharpRpcConfig sharpRpcConfig(){
        return SharpRpcConfig.getInstance().load("sharp.properties");
    }

    @Bean
    public SharpAutoConfigureCenter sharpAutoConfigureCenter(SharpRpcConfig sharpRpcConfig){
        SharpAutoConfigureCenter configureCenter = new SharpAutoConfigureCenter(sharpRpcConfig, new ServiceBeanFactory() {
            @Override
            public Object getServiceBean(Class<?> clazz) {
                return SpringUtil.getBean(clazz);
            }
        });
        configureCenter.autoConfigure();
        return configureCenter;
    }*/

}
