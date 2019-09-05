package com.jac.caravela.scenes

import android.content.Context
import com.jac.caravela.R
import com.jac.caravela.core.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

interface Scene{
    interface View {
        fun getContext(): Context?
        fun unsuccessfulCall(msgRef: Int?) {
            if (msgRef == R.string.error_invalid_token) {
                getContext()?.let { App.restartApp(it) }
            }
        }
    }

    abstract class Presenter: CoroutineScope {
        protected var job: Job = Job()
        override val coroutineContext: CoroutineContext = Dispatchers.Main + job

        fun kill() {
            job.cancel()
        }
    }
}