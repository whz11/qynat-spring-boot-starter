package com.qingyou.qynat.client.handler;

import com.qingyou.qynat.client.client.TcpConnection;
import com.qingyou.qynat.commom.codec.NatProtoCodec;
import com.qingyou.qynat.commom.exception.QyNatException;
import com.qingyou.qynat.commom.handler.QyNatCommonHandler;
import com.qingyou.qynat.commom.protocol.proto.NatProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author whz
 * @date 2021/7/16 01:08
 **/
public class QyNatClientHandler extends QyNatCommonHandler {
    private static final Logger log = LoggerFactory.getLogger(QyNatClientHandler.class);

    private final String port;
    private final String token;
    private final String proxyAddress;
    private final String serverAddress;
    private final String proxyPort;

    private ConcurrentHashMap<String, QyNatCommonHandler> channelHandlerMap = new ConcurrentHashMap<>();
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public QyNatClientHandler(String port, String token, String proxyAddress, String serverAddress, String proxyPort) {
        this.port = port;
        this.token = token;
        this.proxyAddress = proxyAddress;
        this.serverAddress = serverAddress;
        this.proxyPort = proxyPort;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // register client information
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("port", port);
        if (token != null) {
            metaData.put("token", token);
        }
        metaData.put("addr", serverAddress);
        NatProto.NatMessage natMessage = NatProtoCodec.createNatMessage(0, NatProto.Type.REGISTER, metaData, null);
        ctx.writeAndFlush(natMessage);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NatProto.NatMessage natMessage = (NatProto.NatMessage) msg;
        NatProto.Type type = natMessage.getType();
        if (type == NatProto.Type.REGISTER_RESULT) {
            handleRegisterResult(natMessage);
        } else if (type == NatProto.Type.CONNECTED) {
            handleConnected(natMessage);
        } else if (type == NatProto.Type.DISCONNECTED) {
            handleDisconnected(natMessage);
        } else if (type == NatProto.Type.DATA) {
            handleData(natMessage);
        } else if (type == NatProto.Type.KEEPALIVE) {
            // 心跳包, 不处理
        } else {
            throw new QyNatException("Unknown type: " + type);
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.close();
        log.warn("Loss connection to QyNat, Please restart!");
    }

    /**
     * 注册返回
     */
    private void handleRegisterResult(NatProto.NatMessage natMessage) {
        if (Boolean.parseBoolean(natMessage.getMetaDataMap().get("success"))) {
            log.info("Register to qynat");
        } else {
            //ctx.close();
            log.error(("Register qynat fail: " + natMessage.getMetaDataMap().get("errMsg")));
            System.exit(-1);
        }
    }

    /**
     * 连接建立
     */
    private void handleConnected(NatProto.NatMessage natMessage) throws Exception {

        try {
            QyNatClientHandler thisHandler = this;
            TcpConnection localConnection = new TcpConnection();
            localConnection.connect(proxyAddress, proxyPort, new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    QyNatLocalProxyHandler localProxyHandler = new QyNatLocalProxyHandler(thisHandler, natMessage.getMetaData().get("channelId").toString());
                    ch.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), localProxyHandler);
                    channelHandlerMap.put(natMessage.getMetaDataMap().get("channelId"), localProxyHandler);
                    channelGroup.add(ch);
                }
            });
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<>();
            metaData.put("channelId", natMessage.getMetaDataMap().get("channelId"));
            NatProto.NatMessage message = NatProtoCodec.createNatMessage(0, NatProto.Type.DISCONNECTED, metaData, null);
            ctx.writeAndFlush(message);
            ctx.close();
            channelHandlerMap.remove(natMessage.getMetaDataMap().get("channelId"));
            throw e;
        }
    }

    /**
     * 连接断开
     */
    private void handleDisconnected(NatProto.NatMessage natMessage) {
        String channelId = natMessage.getMetaDataMap().get("channelId");
        QyNatCommonHandler handler = channelHandlerMap.get(channelId);
        if (handler != null) {
            handler.getCtx().close();
            channelHandlerMap.remove(channelId);
        }
    }

    /**
     * 传输byte[]
     */
    private void handleData(NatProto.NatMessage natMessage) {
        String channelId = natMessage.getMetaDataMap().get("channelId");
        QyNatCommonHandler handler = channelHandlerMap.get(channelId);
        if (handler != null) {
            ChannelHandlerContext ctx = handler.getCtx();
            ctx.writeAndFlush(natMessage.getData().toByteArray());
        }
    }

}
