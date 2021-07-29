package com.qingyou.qynat.springboot.property;

import com.qingyou.qynat.commom.util.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author whz
 * @date 2021/7/23 15:54
 **/
@ConfigurationProperties(prefix = "qynat")
public class QyNatServiceProperties {
    private String serverAddress;
    private String serverPort;
    private String token;
    private String remotePort;
    private String proxyAddress;
    private String proxyPort;


    public String getToken() {
        return StringUtil.checkEmpty(token);
    }

    public String getProxyAddress() {
        return StringUtil.checkEmpty(proxyAddress);
    }

    public String getProxyPort() {
        return StringUtil.checkEmpty(proxyPort);
    }

    public String getRemotePort() {
        return StringUtil.checkEmpty(remotePort);
    }

    public String getServerAddress() {
        return StringUtil.checkEmpty(serverAddress);
    }

    public String getServerPort() {
        return StringUtil.checkEmpty(serverPort);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}
