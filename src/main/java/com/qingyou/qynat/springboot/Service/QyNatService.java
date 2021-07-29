package com.qingyou.qynat.springboot.Service;

import com.qingyou.qynat.client.client.QyNatClient;
import com.qingyou.qynat.client.handler.QyNatClientHandler;
import com.qingyou.qynat.commom.util.NetworkUtil;
import com.qingyou.qynat.commom.util.StringUtil;
import com.qingyou.qynat.springboot.property.QyNatServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Objects;

/**
 * @author whz
 * @date 2021/7/23 15:48
 **/
public class QyNatService {
    private static final Logger log = LoggerFactory.getLogger(QyNatService.class);

    private final String serverAddress;
    private final String serverPort;
    private final String token;
    private final String remotePort;
    private final String proxyAddress;
    private final String proxyPort;

    public QyNatService(QyNatServiceProperties properties) {
        this.serverAddress = Objects.requireNonNull(properties.getServerAddress(), "serverAddress must not be null");
        this.serverPort = Objects.requireNonNull(properties.getServerPort(), "serverPort must not be null");
        this.remotePort = Objects.requireNonNull(properties.getRemotePort(), "remotePort must not be null");
        this.token = properties.getToken();
        this.proxyAddress = properties.getProxyAddress() == null ? NetworkUtil.getCurrentIp() : properties.getProxyAddress();
        this.proxyPort = properties.getProxyPort();
    }

    public void connect() {
        try {
            new QyNatClient().connect(serverAddress, serverPort, remotePort, token,
                    proxyAddress, proxyPort, QyNatClientHandler.class);
            log.info("Started QyNatClient success mapping at:http://{}:{}", serverAddress, remotePort);
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

}
