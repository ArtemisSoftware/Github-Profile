package com.artemissoftware.githubprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.artemissoftware.data.ff

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val lolo = ff()

        lifecycleScope.launchWhenResumed {

            lolo.getlolo()


        }

    }


}