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

package ru.endlesscode.chatter.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.endlesscode.chatter.data.messages.MessageOut
import ru.endlesscode.chatter.entity.local.Message
import ru.endlesscode.chatter.model.messages.MessagesInteractor
import ru.endlesscode.chatter.presentation.view.ChatView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@InjectViewState
class ChatPresenter @Inject constructor(
        private val interactor: MessagesInteractor
) : MvpPresenter<ChatView>() {

    private val messages = mutableListOf<Message>()

    init {
        interactor.setMessageListener(this::onMessageReceived)
    }

    override fun attachView(view: ChatView?) {
        super.attachView(view)

        viewState.initMessages(messages)
    }

    fun onSendPressed(text: String) {
        viewState.clearInput()
        showLastMessage()

        val message = MessageOut(text)
        messages.add(message)
        interactor.sendMessage(message, this::onError)
    }

    private fun onError(errorMessage: String) {
        viewState.showError(errorMessage)
    }

    private fun onMessageReceived(message: Message) {
        messages.add(message)
        showLastMessage()
    }

    private fun showLastMessage() {
        val index = messages.lastIndex
        viewState.showNewMessage(index)
        viewState.scrollTo(index)
    }
}
