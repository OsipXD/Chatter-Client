/*
 * This file is part of Chatter, licensed under the MIT License (MIT).
 *
 * Copyright (c) Osip Fatkullin <osip.fatkullin@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.endlesscode.chatter.di.module

import dagger.Module
import dagger.Provides
import ru.endlesscode.chatter.data.json.DataBytesConverter
import ru.endlesscode.chatter.data.network.ServerConnection
import ru.endlesscode.chatter.data.network.UdpConnection
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideServerConnection(
            address: String,
            port: Int,
            converter: DataBytesConverter
    ): ServerConnection =
            UdpConnection(address, port, converter)

    @Provides
    @Singleton
    fun provideAddress(): String = "localhost" // TODO: Real address

    @Provides
    @Singleton
    fun providePort(): Int = 4242 // TODO: Real port
}
