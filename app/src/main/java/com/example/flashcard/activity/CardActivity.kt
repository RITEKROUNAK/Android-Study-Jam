package com.example.flashcard.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.adapter.DeckAdapter
import com.example.flashcard.api.ApiClient
import com.example.flashcard.api.modal.Word
import com.example.flashcard.databinding.ActivityCardBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardActivity : AppCompatActivity() {

    companion object {
        const val WORDLIST = "words"
    }

    private lateinit var binding: ActivityCardBinding
    private lateinit var loadingDialog: AlertDialog
    private val rawWords = arrayListOf<String>()
    private val words = arrayListOf<Word>()
    private var loadedWords = 0
    private val api = ApiClient.getApiClient()

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rawWords.addAll(intent.getStringArrayListExtra(WORDLIST) ?: arrayListOf())

        binding.imgCorrect.setOnClickListener {
            binding.cardDeck.swipe()
            score++
            binding.txScore.text = "Your Score: $score/${words.size}"
        }

        binding.imgCross.setOnClickListener {
            binding.cardDeck.swipe()
        }

        showDialog()
        fetchMeanings()
    }

    private fun showDialog() {
        loadingDialog = MaterialAlertDialogBuilder(this).apply {
            setTitle("Loading...")
            setMessage("Fetching meaning of all the words, please wait.")
            setCancelable(false)
        }.create()
        loadingDialog.show()
    }

    private fun hideDialog() {
        loadingDialog.cancel()
    }

    private fun loadCards() {
        binding.cardDeck.apply {
            adapter = DeckAdapter(words)
            layoutManager = CardStackLayoutManager(applicationContext).apply {
                setStackFrom(StackFrom.Bottom)
                setVisibleCount(3)
                setScaleInterval(0.90F)
                setCanScrollVertical(false)
                setCanScrollHorizontal(false)
            }
        }
    }

    private fun fetchMeanings() {
        rawWords.forEach { word ->
            CoroutineScope(Dispatchers.IO).launch {
                val temp = api.meaning(word)
                words.add(Word(word, temp[0].meanings[0].partOfSpeech, temp[0].meanings[0].definitions[0].definition))
                loadedWords++
                Log.d("WORD", temp.toString())
                if (loadedWords == rawWords.size) {
                    runOnUiThread {
                        hideDialog()
                        loadCards()
                    }
                }
            }
        }
    }
}