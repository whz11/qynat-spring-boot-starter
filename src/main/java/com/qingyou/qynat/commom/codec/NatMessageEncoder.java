package com.qingyou.qynat.commom.codec;

import com.qingyou.qynat.commom.protocol.proto.NatProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author whz
 * @date 2021/7/15 19:45
 **/
public class NatMessageEncoder extends MessageToByteEncoder<NatProto.NatMessage> {
//
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NatProto.NatMessage natMessage, ByteBuf byteBuf) throws Exception {
        System.out.println("encode:"+natMessage);
        byte[] body =NatProtoCodec.encode(natMessage);
        byteBuf.writeInt(body.length);
        byteBuf.writeBytes(body);
    }

//    @Override
//    protected void encode(ChannelHandlerContext ctx, NatMessage msg, ByteBuf out) throws Exception {
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try (DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
//
//            NatMessageType natxMessageType = msg.getMessageHeader().getNatMessageType();
//            dataOutputStream.writeInt(natxMessageType.getCode());
//
//            JSONObject metaDataJson = new JSONObject(msg.getMetaData());
//            byte[] metaDataBytes = metaDataJson.toString().getBytes(CharsetUtil.UTF_8);
//            dataOutputStream.writeInt(metaDataBytes.length);
//            dataOutputStream.write(metaDataBytes);
//
//            if (msg.getData() != null && msg.getData().length > 0) {
//                dataOutputStream.write(msg.getData());
//            }
//
//            byte[] data = byteArrayOutputStream.toByteArray();
//            out.writeInt(data.length);
//            out.writeBytes(data);
//        }
//
//    }

}
