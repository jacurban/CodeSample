package com.jac.caravela.scenes.turmatimeline

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Post
import com.jac.caravela.scenes.turmacadastro.TurmaCadastroActivity
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.toolbar.*

class TurmaTimelineActivity : AppCompatActivity(), TurmaTimeline.View {

    private lateinit var presenter: TurmaTimeline.Presenter
    private val timelineList: MutableList<Post> by lazy { mutableListOf<Post>() }
    private var timelineAdapter: PostAdapter? = null
    private var classInfo: Classe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        presenter = TurmaTimelinePresenter(this)
        classInfo = intent.getParcelableExtra(TurmaCadastroActivity.EXTRA_CLASS_INFO)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_title?.text = classInfo?.name
    }

    private fun setupRecycleView() {
        timelineAdapter = PostAdapter(timelineList, this)
        chat_RCV?.adapter = timelineAdapter
        chat_RCV?.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewElements() {
        chat_send_BTN?.setOnClickListener{
            val subject = chat_text_EDT?.text.toString()

            presenter.postSendPost(App.user?.id, subject, classInfo?.id)

            chat_text_EDT?.text = null
        }
    }

    override fun successfulGetAll(postList: List<Post>) {
        timelineList.clear()
        timelineList.addAll(postList)
        timelineAdapter?.notifyDataSetChanged()
        chat_RCV?.scrollToPosition(timelineList.size - 1)
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        toolbar_progressbar?.visibility = View.GONE
        unsuccessfulCall(msgRef)
    }

    override fun onResume() {
        super.onResume()
        toolbar_progressbar?.visibility = View.VISIBLE
        presenter.getAllPostsByClass(classInfo?.id)
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_CLASS_INFO = "EXTRA_CLASS_INFO"
    }
}