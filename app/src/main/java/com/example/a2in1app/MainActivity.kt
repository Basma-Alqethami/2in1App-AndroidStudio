package com.example.a2in1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var numbersGame_Button: Button
    private lateinit var guessThePhrase_Button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.title = "Home"

        numbersGame_Button = findViewById(R.id.numbers_Game_Button)
        guessThePhrase_Button = findViewById(R.id.guessThePhrase_Button)

        numbersGame_Button.setOnClickListener {
            val intent = Intent(this, NumbersGame_MainActivity::class.java)
            startActivity(intent)
        }

        guessThePhrase_Button.setOnClickListener {
            val intent = Intent(this, GuessThePhrase_MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("i", item.toString())

        when(item.itemId){
            numbersGame_Button.id -> {
                    val intent = Intent(this, NumbersGame_MainActivity::class.java)
                    startActivity(intent)
                return true
            }
            guessThePhrase_Button.id -> {
                    val intent = Intent(this, GuessThePhrase_MainActivity::class.java)
                    startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}