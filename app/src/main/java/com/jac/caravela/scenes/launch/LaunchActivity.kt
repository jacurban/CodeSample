package com.jac.caravela.scenes.launch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.core.App
import com.jac.caravela.scenes.login.LoginActivity
import com.jac.caravela.scenes.main.MainActivity

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = if (App.user != null)
            Intent(this, MainActivity::class.java)
        else
            Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
