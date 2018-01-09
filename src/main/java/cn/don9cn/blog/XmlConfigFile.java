package cn.don9cn.blog;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Don9
 * @create 2017-12-28-10:08
 **/
@Configuration
@ImportResource(locations = {"classpath:dubbo-provider.xml"})
public class XmlConfigFile {
}
