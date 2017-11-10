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

package ru.endlesscode.chatter.model.messages

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import ru.endlesscode.chatter.data.messages.MessageIn
import ru.endlesscode.chatter.data.messages.MessageOut
import ru.endlesscode.chatter.data.messages.MessagesRepository
import ru.endlesscode.chatter.entity.local.Message

class MessagesInteractorImpl(
        private val repository: MessagesRepository
) : MessagesInteractor {

    override fun sendMessage(message: MessageOut): Channel<Message> {
        val channel = Channel<Message>()

        launch {
            if (message.text.isEmpty()) {
                channel.close(IllegalArgumentException("Message shouldn't be empty!"))
                return@launch
            }

            channel.offer(message)
        }

        return channel
    }

    override fun setMessageListener(listener: (MessageIn) -> Unit) {
        //        repository.setMessageListener(listener)
    }

    override fun finish(): Job = launch {
        coroutineContext.cancelChildren()
        repository.finish()
    }
}
