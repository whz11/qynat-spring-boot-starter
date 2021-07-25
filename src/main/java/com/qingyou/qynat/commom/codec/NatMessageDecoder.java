package com.qingyou.qynat.commom.codec;

import com.qingyou.qynat.commom.protocol.proto.NatProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author whz
 * @date 2021/7/15 19:45
 **/
public class NatMessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final Integer REQUEST_LENGTH = 4;

    //    @Override
//    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
//        if (byteBuf.readableBytes() < REQUEST_LENGTH) {
//            System.out.println("REQUEST_LENGTH：not enough");
//            return;
//        }
//        byteBuf.markReaderIndex();
//        int head = byteBuf.readInt();
//        if (head < 0) {
//            channelHandlerContext.close();
//        }
//        if (byteBuf.readableBytes() < head) {
//            byteBuf.resetReaderIndex();
//            return;
//        }
//        byte[] body = new byte[head];
//        byteBuf.readBytes(body);
//        NatMessage natMessage = ProtostuffUtil.deserialize(body, NatMessage.class);
//        System.out.println("decode:"+natMessage);
//        out.add(natMessage);
//    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {

//    int type = msg.readInt();
//    NatMessageType natMessageType = NatMessageType.valueOf(type);
//
//    int metaDataLength = msg.readInt();
//    CharSequence metaDataString = msg.readCharSequence(metaDataLength, CharsetUtil.UTF_8);
//    JSONObject jsonObject = new JSONObject(metaDataString.toString());
//    Map<String, Object> metaData = jsonObject.toMap();
//
//    byte[] data = null;
//    if (msg.isReadable()) {
//        data = ByteBufUtil.getBytes(msg);
//    }
//
//    NatMessage natxMessage = new NatMessage();
//    natxMessage.setMessageHeader(new NatMessageHeader(natMessageType));
//    natxMessage.setMetaData(metaData);
//    natxMessage.setData(data);
        int length = msg.readInt();
        byte[] data = ByteBufUtil.getBytes(msg);
        NatProto.NatMessage natMessage = NatProtoCodec.decode(data);
        out.add(natMessage);
    }
}
