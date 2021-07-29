package com.qingyou.qynat.springboot.config;

import com.qingyou.qynat.commom.util.StringUtil;
import com.qingyou.qynat.springboot.Service.QyNatService;
import com.qingyou.qynat.springboot.property.QyNatServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author whz
 * @date 2021/7/23 15:55
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(QyNatServiceProperties.class)
public class QyNatAutoConfigure {
    @Autowired
    private QyNatServiceProperties properties;
    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnMissingBean(QyNatService.class)
    QyNatService qyNatService() {
        String currentPort = env.getProperty("server.port") == null ? "8080" : env.getProperty("server.port");
        properties.setProxyPort(properties.getProxyPort() == null ? currentPort : properties.getProxyPort());
        QyNatService qyNatService = new QyNatService(properties);
        qyNatService.connect();
        return qyNatService;
    }
}


//        System.out.println(properties.getServerAddress());
//                System.out.println(properties.getServerPort());
//                System.out.println(properties.getToken());
//                System.out.println(properties.getRemotePort());
//                System.out.println(properties.getProxyAddress()==null);
//                System.out.println(properties.getProxyAddress().equals(""));
//                System.out.println(properties.getProxyPort());