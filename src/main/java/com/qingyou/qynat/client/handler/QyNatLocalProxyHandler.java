package com.qingyou.qynat.client.handler;

import com.qingyou.qynat.commom.codec.NatProtoCodec;
import com.qingyou.qynat.commom.handler.QyNatCommonHandler;
import com.qingyou.qynat.commom.protocol.proto.NatProto;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

/**
 * @author whz
 * @date 2021/7/16 13:14
 **/
public class QyNatLocalProxyHandler extends QyNatCommonHandler {

    private final QyNatCommonHandler proxyHandler;
    private final String remoteChannelId;

    public QyNatLocalProxyHandler(QyNatCommonHandler proxyHandler, String remoteChannelId) {
        this.proxyHandler = proxyHandler;
        this.remoteChannelId = remoteChannelId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        byte[] data = (byte[]) msg;
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("channelId", remoteChannelId);
        NatProto.NatMessage message = NatProtoCodec.createNatMessage(0, NatProto.Type.DATA, metaData, data);
        proxyHandler.getCtx().writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("channelId", remoteChannelId);
        NatProto.NatMessage message = NatProtoCodec.createNatMessage(0, NatProto.Type.DISCONNECTED, metaData, null);
        proxyHandler.getCtx().writeAndFlush(message);
    }
}
