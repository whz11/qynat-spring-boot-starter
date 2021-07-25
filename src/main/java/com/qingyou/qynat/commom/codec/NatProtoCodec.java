package com.qingyou.qynat.commom.codec;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.qingyou.qynat.commom.protocol.proto.NatProto;

import java.util.Map;

/**
 * @author whz
 * @date 2021/7/17 12:06
 **/
public class NatProtoCodec {
    public static byte[] encode(NatProto.NatMessage natMessage) {
        return natMessage.toByteArray();
    }

    public static NatProto.NatMessage decode(byte[] body) throws InvalidProtocolBufferException {
        return NatProto.NatMessage.parseFrom(body);
    }

    public static NatProto.NatMessage createNatMessage(int id, NatProto.Type type, Map<String, String> metaData, byte[] data) {
        NatProto.NatMessage.Builder builder = NatProto.NatMessage.newBuilder();
        if (metaData != null) {
            builder.putAllMetaData(metaData);
        }
        if (data != null) {
            builder.setData(ByteString.copyFrom(data));
        }
        builder.setType(type);
        builder.setId(id);
        return builder.build();
    }
}
