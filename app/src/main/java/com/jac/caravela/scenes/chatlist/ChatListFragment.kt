package com.jac.caravela.scenes.chatlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Chat
import com.jac.caravela.scenes.chatnew.NewChatActivity
import com.jac.caravela.scenes.main.MainActivity
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class ChatListFragment : Fragment(), ChatList.View, ChatAdapter.Listener {

    private lateinit var presenter: ChatList.Presenter
    private val chats: MutableList<Chat> by lazy { mutableListOf<Chat>() }
    private var chatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ChatListPresenter(this)

        chatAdapter = ChatAdapter(chats, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_fab_RCV?.adapter = chatAdapter
        list_fab_RCV?.layoutManager = LinearLayoutManager(context)

        list_fab_BTN?.setOnClickListener {
            val i = Intent(activity, NewChatActivity::class.java)
            startActivity(i)
        }
    }

    override fun getChatType() = ChatAdapter.ChatType.CHAT

    override fun successfulGetAll(chats: List<Chat>) {
        this.chats.clear()
        this.chats.addAll(chats)
        chatAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        hideProgressBar()
        unsuccessfulCall(msgRef)
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllChatsByUser()
        showProgressBar()
    }

    private fun showProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val FRAGMENT_TAG = "mensagens.MessageListFragment"

        fun newInstance() = ChatListFragment()
    }
}
