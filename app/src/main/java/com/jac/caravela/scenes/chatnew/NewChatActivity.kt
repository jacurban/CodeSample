package com.jac.caravela.scenes.chatnew

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Chat
import com.jac.caravela.scenes.chatlist.ChatAdapter
import com.jac.caravela.scenes.main.MainActivity
import kotlinx.android.synthetic.main.activity_list_chat.*
import kotlinx.android.synthetic.main.toolbar.*

class NewChatActivity : AppCompatActivity(), NewChat.View, ChatAdapter.Listener {

    private lateinit var presenter: NewChat.Presenter

    private val chats = mutableListOf<Chat>()
    private var chatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_chat)

        presenter = NewChatPresenter(this)

        chatAdapter = ChatAdapter(chats, this)

        toolbar_title?.text = getString(R.string.new_chat)
        list_chat_RCV?.adapter = chatAdapter
        list_chat_RCV?.layoutManager = LinearLayoutManager(this)
    }

    override fun successfulGetAll(chats: List<Chat>) {
        this.chats.clear()
        this.chats.addAll(chats)
        chatAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified) ?: ""
        showShortMessage(msg)
        hideProgressBar()
        unsuccessfulCall(msgRef)
    }

    private fun showProgressBar() {
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllUsers()
        showProgressBar()
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    override fun getChatType() = ChatAdapter.ChatType.NEW_CHAT

    companion object {
        const val FRAGMENT_TAG = "mensagens.MessageListFragment"
    }
}