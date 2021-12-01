package com.example.a2in1app

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class GuessThePhrase_MainActivity : AppCompatActivity() {

    private lateinit var GuessRV: RecyclerView
    private lateinit var guessButton: Button
    private lateinit var textFieldMessage: EditText
    private lateinit var textViewGuess: TextView
    private lateinit var textViewLetters: TextView
    private lateinit var guessMain: ConstraintLayout

    private val answers = ArrayList<String>()
    private var answer = "i am a trainee at saudi digital academy"
    private var hidTexe = ""
    private var count = 0
    private var guessedLetters = ""
    private var switchGuess = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_phrase_main)
        this.title = "Guess The Phrase"

        GuessRV = findViewById(R.id.rvGuess)
        guessButton = findViewById(R.id.sendButton)
        textFieldMessage = findViewById(R.id.guessesField)
        textViewGuess = findViewById(R.id.textGuess)
        textViewLetters = findViewById(R.id.guessedLetters)

        guessMain = findViewById(R.id.guessMain)
        GuessRV.adapter = guessPhraseRecyclerView(answers)
        GuessRV.layoutManager = LinearLayoutManager(this)
        for (i in answer.indices) {
            if (answer[i] == ' ') {
                hidTexe += ' '
            } else {
                hidTexe += '*'
            }
        }
        updateText()
        guessButton.setOnClickListener { addGuess() }
    }

    override fun recreate() {
        super.recreate()
        answer = "i am a trainee at saudi digital academy"
        hidTexe = ""
        count = 0
        guessedLetters = ""
        switchGuess = true
        answers.clear()

        for (i in answer.indices) {
            if (answer[i] == ' ') {
                hidTexe += ' '
            } else {
                hidTexe += '*'
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("answer", answer)
        outState.putString("hidTexe", hidTexe)
        outState.putInt("count", count)
        outState.putBoolean("switchGuess", switchGuess)
        outState.putString("guessedLetters", guessedLetters)
        outState.putStringArrayList("answers", answers)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        answer = savedInstanceState.getString("answer", "nothing here")
        hidTexe = savedInstanceState.getString("hidTexe", "")
        count = savedInstanceState.getInt("count", 0)
        switchGuess = savedInstanceState.getBoolean("switchGuess", true)
        guessedLetters = savedInstanceState.getString("guessedLetters", "")
        answers.addAll(savedInstanceState.getStringArrayList("answers")!!)
        updateText()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(1)
        item.title = "Numbers Game"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_restart_game -> {
                showAlertDialog("Are you sure you want to abandon the current game?")
                return true
            }
            R.id.other_game -> {
                val intent = Intent(this, NumbersGame_MainActivity::class.java)
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

    private fun addGuess() {
        var userGuess = textFieldMessage.text.toString()
        userGuess = userGuess.lowercase()
        if (count != 10) {
            if (switchGuess) {
                if (userGuess == answer) {
                    disableEntry()
                    showAlertDialog("You win!\n\nPlay again?")
                    answers.add("-----------------------------------------")
                    answers.add("You win!\nThe correct answer was: [ $answer ]\n\nGame Over")

                } else {
                    switchGuess = false
                    updateText()
                    answers.add("Wrong guess: $userGuess")
                }
            } else {
                if (userGuess.isNotEmpty() && userGuess.length == 1) {
                    switchGuess = true
                    checkLetters(userGuess[0])
                } else {
                    Snackbar.make(guessMain, "Please enter one letter only", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        } else {
            disableEntry()
            showAlertDialog("You lose!\n\nPlay again?")
            answers.add("-----------------------------------------")
            answers.add("You lose\nThe correct answer was: [ $answer ] \n\nGame Over")
        }
        GuessRV.scrollToPosition(answers.size - 1)
        textFieldMessage.text.clear()
        GuessRV.adapter?.notifyDataSetChanged()
    }

    private fun updateText() {
        textViewGuess.text = hidTexe.toUpperCase()

        var allLetters = guessedLetters.toCharArray()
        textViewLetters.text = "Guessed Letters:  " + allLetters.joinToString()
        if (switchGuess) {
            textFieldMessage.hint = "Guess the full phrase"
        } else {
            textFieldMessage.hint = "Guess a letter"
        }
    }

    private fun checkLetters(guessedLetter: Char) {
        var allStars = hidTexe.toCharArray()
        var listOfIndex = ArrayList<Int>()
        var temText = ""
        count++
        // find all index
        for (i in answer.indices) {
            if (answer[i] == guessedLetter) {
                listOfIndex.add(i)
            }
        }
        if (listOfIndex.isNotEmpty()) {
            for (item in listOfIndex) {
                allStars[item] = guessedLetter
            }

            for (i in allStars) {
                if (i == ' ') {
                    temText += ' '
                } else if (i == '*') {
                    temText += '*'
                } else {
                    temText += i
                }
            }
            hidTexe = temText
            guessedLetters += guessedLetter
            answers.add("Found ${listOfIndex.size} ${guessedLetter.toUpperCase()}(s)\n${10 - count} guesses remaining")
        } else {
            answers.add("No ${guessedLetter.toUpperCase()}s found\n${10 - count} guesses remaining")
        }
        updateText()
    }

    private fun disableEntry() {
        guessButton.isEnabled = false
        guessButton.isClickable = false
        textFieldMessage.isEnabled = false
        textFieldMessage.isClickable = false
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
