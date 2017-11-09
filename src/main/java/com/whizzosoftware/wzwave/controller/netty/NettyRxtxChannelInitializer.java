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

import com.whizzosoftware.wzwave.channel.ZWaveChannelListener;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxDeviceAddress;

/**
 * A RxTx serial port initializer
 *
 * @author Ziver Koc
 */
public class NettyRxtxChannelInitializer extends AbstractNettyChannelInitializer<RxtxChannel> {


    public NettyRxtxChannelInitializer(String serialPort, ZWaveChannelListener listener) {
        super(serialPort, listener);

        bootstrap.channel(RxtxChannel.class);
    }

    @Override
    protected void doInitChannel(RxtxChannel channel) throws Exception {
        channel.config().setBaudrate(115200);
        channel.config().setDatabits(RxtxChannelConfig.Databits.DATABITS_8);
        channel.config().setParitybit(RxtxChannelConfig.Paritybit.NONE);
        channel.config().setStopbits(RxtxChannelConfig.Stopbits.STOPBITS_1);
    }


    @Override
    public RxtxDeviceAddress getSocketAddress() {
        return new RxtxDeviceAddress(serialPort);
    }
}
