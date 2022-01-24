package br.com.gmt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.gmt.R
import br.com.gmt.util.Func.startActivity
import kotlinx.coroutines.*


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            delay(1000)
            startActivity(MainActivity())
            finishAffinity()
        }
    }
}