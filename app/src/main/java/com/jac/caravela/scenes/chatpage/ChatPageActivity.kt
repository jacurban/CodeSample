package com.jac.caravela.scenes.chatpage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Chat
import com.jac.caravela.model.Message
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.toolbar.*

class ChatPageActivity : AppCompatActivity(), ChatPage.View {

    private lateinit var presenter: ChatPage.Presenter
    private val messages: MutableList<Message> by lazy { mutableListOf<Message>() }
    private var messageAdapter: MessageAdapter? = null

    private lateinit var chat: Chat
    private var contactId = 0
    private var contactName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)

        presenter = ChatPagePresenter(this)
        messageAdapter = MessageAdapter(messages, this)

        getArgs()
        setupToolbar()
        setupViewElements()
    }

    private fun getArgs() {
        chat = intent.getParcelableExtra(EXTRA_CHAT)
    }

    private fun setupToolbar() {
        if (chat.userOneId == App.user?.id) {
            contactId = chat.userTwoId
            contactName = chat.userTwoName ?: getString(R.string.deleted_user)
        } else {
            contactId = chat.userOneId
            contactName = chat.userTwoName ?: getString(R.string.deleted_user)
        }
        toolbar_title?.text = contactName
    }

    private fun setupViewElements() {
        chat_RCV?.adapter = messageAdapter
        chat_RCV?.layoutManager = LinearLayoutManager(this)
        chat_RCV?.scrollToPosition(messages.size - 1)

        chat_send_BTN?.setOnClickListener {
            val description = chat_text_EDT?.text.toString()
            if (description.isNotEmpty()) {
                App.user?.id?.let { presenter.sendMessage(chat.id, it, contactId, description) }
                messageAdapter?.notifyDataSetChanged()
                chat_text_EDT?.text?.clear()
                toolbar_progressbar?.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        chat.id?.let { chatId ->
            toolbar_progressbar?.visibility = View.VISIBLE
            presenter.getAllMessagesByChat(chatId)
        }
    }

    override fun successfulGetAll(messages: List<Message>?) {
        this.messages.clear()
        messages?.let { this.messages.addAll(it) }
        messageAdapter?.notifyDataSetChanged()
        toolbar_progressbar?.visibility = View.GONE
        chat_RCV?.scrollToPosition(this.messages.size - 1)
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        toolbar_progressbar?.visibility = View.GONE
        unsuccessfulCall(msgRef)
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_CHAT = "EXTRA_CHAT"
    }
}
