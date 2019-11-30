package com.example.iq180.game

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.iq180.BackgroundSoundService
import com.example.iq180.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private lateinit var svc: Intent
    private var music = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var name = intent.getStringExtra("name")
        var name2 = intent.getStringExtra("name2")
        var requestCode = intent.getIntExtra("requestCode", -1)
        var roomName = intent.getStringExtra("roomName")

        svc = Intent(this, BackgroundSoundService::class.java)
        startService(svc)

        if (savedInstanceState == null) {
            when (requestCode) {
                1 -> {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.gameContentContainer, GameFragment.newInstance(roomName, name, name2, requestCode), "GameFragment")
                            .commit()
                }
                2 -> {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.gameContentContainer, GameMultiplayFragment.newInstance(roomName, name, name2), "GameMultiplayFragment")
                            .commit()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (music) {
            stopService(svc)
            music = false
        }
        else {
            startService(svc)
            music = true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        startService(svc)
    }

    override fun onPause() {
        super.onPause()
        stopService(svc)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(svc)
    }

}
