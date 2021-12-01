package com.example.a2in1app

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class NumbersGame_MainActivity : AppCompatActivity() {
    private lateinit var myRV: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var messageTextField: EditText
    private lateinit var numbersMain: ConstraintLayout

    private val messages = ArrayList<String>()
    private var count = 3
    private var answer = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers_game_main)

        this.title = "Numbers Game"

        myRV = findViewById(R.id.rvNumbers)
        submitButton = findViewById(R.id.submitButton)
        messageTextField = findViewById(R.id.messageText)
        numbersMain = findViewById(R.id.numbersMain)
        myRV.adapter = numbersGameRecyclerView(messages)
        myRV.layoutManager = LinearLayoutManager(this)

        answer = Random.nextInt(0, 11)
        submitButton.setOnClickListener { AddTOList(answer) }
    }



    override fun recreate() {
        super.recreate()
        answer = Random.nextInt(10)
        count = 3
        messages.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("answer", answer)
        outState.putInt("count", count)
        outState.putStringArrayList("messages", messages)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        answer = savedInstanceState.getInt("answer", 0)
        count = savedInstanceState.getInt("count", 0)
        messages.addAll(savedInstanceState.getStringArrayList("messages")!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(1)
        item.title = "Guess The Phrase"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_restart_game -> {
                showAlertDialog("Are you sure you want to abandon the current game?")
                return true
            }
            R.id.other_game -> {
                val intent = Intent(this, GuessThePhrase_MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun AddTOList(answer: Int) {
        var message = messageTextField.text.toString()

        if (message.isNotEmpty()) {
            try {
                if(count > 0){

                    if(message.toInt() == answer){
                        messages.add("-----------------------------------------")
                        messages.add("You win!\nThe correct answer was $answer\nGame Over")
                        disableEntry()
                        showAlertDialog("You win!\n\nPlay again?")
                    }else{
                        count--
                        messages.add("You guessed $message\nYou have $count guesses left")
                    }

                    if(count == 4){
                        messages.add("-----------------------------------------")
                        messages.add("You lose\nThe correct answer was $answer\nGame Over")
                        disableEntry()
                        showAlertDialog("You lose...\nThe correct answer was $answer.\n\nPlay again?")
                    }
                }
                messageTextField.text.clear()
                myRV.adapter?.notifyDataSetChanged()

            } catch (e: Exception) {
                Snackbar.make(numbersMain, "Please enter number only", Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(numbersMain, "Please enter some text", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun disableEntry() {
        submitButton.isEnabled = false
        submitButton.isClickable = false
        messageTextField.isEnabled = false
        messageTextField.isClickable = false
    }

    private fun showAlertDialog(title: String) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(title)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Game Over")
        alert.show()
    }
}