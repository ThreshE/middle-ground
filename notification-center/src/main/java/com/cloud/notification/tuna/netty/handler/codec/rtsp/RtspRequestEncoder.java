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
package com.cloud.notification.tuna.netty.handler.codec.rtsp;

import com.cloud.notification.tuna.netty.buffer.ByteBuf;
import com.cloud.notification.tuna.netty.handler.codec.http.FullHttpRequest;
import com.cloud.notification.tuna.netty.handler.codec.http.HttpRequest;
import com.cloud.notification.tuna.netty.util.CharsetUtil;

import static com.cloud.notification.tuna.netty.handler.codec.http.HttpConstants.*;

/**
 * Encodes an RTSP request represented in {@link FullHttpRequest} into
 * a {@link ByteBuf}.

 */
public class RtspRequestEncoder extends RtspObjectEncoder<HttpRequest> {
    private static final byte[] CRLF = { CR, LF };

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return msg instanceof FullHttpRequest;
    }

    @Override
    protected void encodeInitialLine(ByteBuf buf, HttpRequest request)
            throws Exception {
        encodeAscii(request.getMethod().toString(), buf);
        buf.writeByte(SP);
        buf.writeBytes(request.getUri().getBytes(CharsetUtil.UTF_8));
        buf.writeByte(SP);
        encodeAscii(request.getProtocolVersion().toString(), buf);
        buf.writeBytes(CRLF);
    }
}
