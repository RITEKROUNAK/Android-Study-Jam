package com.example.flashcard.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcard.adapter.WordAdapter
import com.example.flashcard.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val wordList = arrayListOf<String>()
    private val wordAdapter = WordAdapter(wordList)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvWords.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        binding.etAddWord.setOnEditorActionListener { tx, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val w = tx.text.toString()
                if (w.isNotEmpty()) {
                    wordList.add(w)
                    wordAdapter.notifyItemInserted(wordList.size - 1)
                    binding.etAddWord.setText("")
                }
                true
            }
            false
        }

        binding.fabAction.setOnClickListener {
            if (wordList.isEmpty()) {
                Snackbar.make(binding.root, "Please add some words!", Snackbar.LENGTH_SHORT).show()
            }
            else {
                startActivity(Intent(applicationContext, CardActivity::class.java).apply {
                    putExtra(CardActivity.WORDLIST, wordList)
                })
            }
        }
    }
}