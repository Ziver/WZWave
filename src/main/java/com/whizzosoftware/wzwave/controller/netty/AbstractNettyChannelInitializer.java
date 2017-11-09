/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Ziver Koc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/*******************************************************************************
 * Copyright (c) 2013 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.wzwave.controller.netty;

import com.whizzosoftware.wzwave.channel.*;
import com.whizzosoftware.wzwave.codec.ZWaveFrameDecoder;
import com.whizzosoftware.wzwave.codec.ZWaveFrameEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.oio.OioEventLoopGroup;

import java.net.SocketAddress;

/**
 * A abstract initializer
 *
 * @author Ziver Koc
 */
abstract class AbstractNettyChannelInitializer<T extends Channel> extends ChannelInitializer<T> {

    protected String serialPort;
    private ZWaveChannelInboundHandler inboundHandler;

    protected Bootstrap bootstrap;
    protected Channel channel;


    public AbstractNettyChannelInitializer(String serialPort, ZWaveChannelListener listener) {
        this.serialPort = serialPort;
        this.inboundHandler = new ZWaveChannelInboundHandler(listener);

        bootstrap = new Bootstrap();
        bootstrap.group(new OioEventLoopGroup());
        bootstrap.handler(this);
    }



    @Override
    protected void initChannel(T channel) throws Exception {
        this.channel = channel;
        doInitChannel(channel);

        // Setup general channel handlers and coders
        channel.pipeline().addLast("decoder", new ZWaveFrameDecoder());
        channel.pipeline().addLast("ack", new ACKInboundHandler());
        channel.pipeline().addLast("encoder", new ZWaveFrameEncoder());
        channel.pipeline().addLast("writeQueue", new FrameQueueHandler());
        channel.pipeline().addLast("transaction", new TransactionInboundHandler());
        channel.pipeline().addLast("handler", inboundHandler);
    }


    public Bootstrap getBootstrap() {
        return bootstrap;
    }
    public Channel getChannel() {
        return channel;
    }
    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public abstract SocketAddress getSocketAddress();

    protected abstract void doInitChannel(T channel) throws Exception;
}
