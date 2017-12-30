package cn.don9cn.blog.autoconfigure.sharp;


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
