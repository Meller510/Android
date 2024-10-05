package com.example.m15_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dictionaryDao: DictionaryDao) : ViewModel() {
    private val _entries = MutableStateFlow<List<Word>>(emptyList())
    val entries: StateFlow<List<Word>> get() = _entries.asStateFlow()

    private var switchLimit = false

    init {
        viewModelScope.launch {
            dictionaryDao.getLimitedEntries().stateIn(viewModelScope)
                .collect { limitedEntries -> _entries.value = limitedEntries }
        }
    }

    fun onAddBtn(word: String) {
        viewModelScope.launch {
            val wordList = entries.first()
            val existingWord = wordList.find { it.word == word }

            if (existingWord != null) dictionaryDao.update(existingWord.copy(counter = existingWord.counter + 1))
            else dictionaryDao.insert(Word(word = word, counter = 1))

            updateEntriesAllDisplayed() //строго не судите ))
        }
    }

    fun onClearBtnAsync() {
        viewModelScope.launch { dictionaryDao.delete() }
    }

    fun switchLimitedEntries(switch: Boolean) {
        viewModelScope.launch {
            val limitedEntriesList =
                if (switch) dictionaryDao.getLimitedEntries(dictionaryDao.getWordCount()).first()
                else dictionaryDao.getLimitedEntries().first()

            switchLimit = switch
            _entries.value = limitedEntriesList
        }
    }

    private suspend fun updateEntriesAllDisplayed() { //Я сделал этот кусок кода просто что бы при отображении всех слов из бд  записи  обновлялись так же как и при 5  словах.
        if (switchLimit) {
            _entries.value = dictionaryDao.getLimitedEntries().first()
            _entries.value =
                dictionaryDao.getLimitedEntries(dictionaryDao.getWordCount()).first()
        }                        //не смог разобраться как сделать все красиво)  но думаю что дальше все будет
    }
}