package com.qingyou.qynat.springboot.Service;

import com.qingyou.qynat.client.client.QyNatClient;
import com.qingyou.qynat.client.handler.QyNatClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public QyNatService(String serverAddress,
                        String serverPort,
                        String token,
                        String remotePort,
                        String proxyAddress,
                        String proxyPort) {
        this.serverAddress = Objects.requireNonNull(serverAddress, "serverAddress must not be null");
        this.serverPort = Objects.requireNonNull(serverPort, "serverPort must not be null");
        this.remotePort = Objects.requireNonNull(remotePort, "remotePort must not be null");
        this.token = token;
        this.proxyAddress = proxyAddress == null ? "localhost" : proxyAddress;
        this.proxyPort = proxyPort == null ? "8080" : proxyPort;
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
