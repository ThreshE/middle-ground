/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.cloud.notification.tuna.netty.handler.codec.bytes;

import com.cloud.notification.tuna.netty.buffer.ByteBuf;
import com.cloud.notification.tuna.netty.buffer.Unpooled;
import com.cloud.notification.tuna.netty.channel.ChannelHandler.Sharable;
import com.cloud.notification.tuna.netty.channel.ChannelHandlerContext;
import com.cloud.notification.tuna.netty.channel.ChannelPipeline;
import com.cloud.notification.tuna.netty.handler.codec.LengthFieldBasedFrameDecoder;
import com.cloud.notification.tuna.netty.handler.codec.LengthFieldPrepender;
import com.cloud.notification.tuna.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Encodes the requested array of bytes into a {@link ByteBuf}.
 * A typical setup for TCP/IP would be:
 * <pre>
 * {@link ChannelPipeline} pipeline = ...;
 *
 * // Decoders
 * pipeline.addLast("frameDecoder",
 *                  new {@link LengthFieldBasedFrameDecoder}(1048576, 0, 4, 0, 4));
 * pipeline.addLast("bytesDecoder",
 *                  new {@link ByteArrayDecoder}());
 *
 * // Encoder
 * pipeline.addLast("frameEncoder", new {@link LengthFieldPrepender}(4));
 * pipeline.addLast("bytesEncoder", new {@link ByteArrayEncoder}());
 * </pre>
 * and then you can use an array of bytes instead of a {@link ByteBuf}
 * as a message:
 * <pre>
 * void channelRead({@link ChannelHandlerContext} ctx, byte[] bytes) {
 *     ...
 * }
 * </pre>
 */
@Sharable
public class ByteArrayEncoder extends MessageToMessageEncoder<byte[]> {
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        out.add(Unpooled.wrappedBuffer(msg));
    }
}
