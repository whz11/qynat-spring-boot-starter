package com.qingyou.qynat.springboot.config;

import com.qingyou.qynat.springboot.Service.QyNatService;
import com.qingyou.qynat.springboot.property.QyNatServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whz
 * @date 2021/7/23 15:55
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(QyNatServiceProperties.class)
public class QyNatAutoConfigure {
    @Autowired
    private QyNatServiceProperties properties;

    @Bean
    @ConditionalOnMissingBean(QyNatService.class)
    QyNatService qyNatService() {
        QyNatService qyNatService = new QyNatService(properties.getServerAddress(), properties.getServerPort(), properties.getToken(),
                properties.getRemotePort(), properties.getProxyAddress(), properties.getProxyPort());
        qyNatService.connect();
        return qyNatService;
    }
}
