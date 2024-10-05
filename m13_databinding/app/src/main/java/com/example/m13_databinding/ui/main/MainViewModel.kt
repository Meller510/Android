package com.example.m13_databinding.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@OptIn(FlowPreview::class)
class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Init)
    val state = _state.asStateFlow()

    val searchString = MutableStateFlow("")

    private val regex = Pattern.compile("^[a-zA-Z0-9\\s]+$")
    private var lastSearchValue: String? = ""

    private var getMovieJob: Job? = null

    init {
        viewModelScope.launch { startSearch() }
    }

    private suspend fun startSearch() {
        searchString.debounce(200).distinctUntilChanged().collectLatest {

            if (checkChangesSearchString(it)) {
                restartSearch()
                delay(600)
                _state.value = State.Loading
            }

            if (checkInputSearchString(it)) _state.value = State.Input
            else if (state.value != State.Init) onSearchMovies(it)
            lastSearchValue = it
        }
    }

    private fun checkChangesSearchString(str: String) =
        str != lastSearchValue.toString() && str.isNotEmpty()

    private fun checkInputSearchString(str: String): Boolean {
        val checkEmptySearch = str < lastSearchValue.toString() && str.isEmpty()

        var checkChars = true
        if (str.isNotEmpty()) checkChars = regex.matcher(str).matches()

        return checkEmptySearch || !checkChars || str.length in 1..2
    }

    private suspend fun onSearchMovies(searchStr: String) {
        _state.value = State.Loading
        getMovieJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val movie = repository.getMovie(searchStr)
                if (movie.isEmpty()) _state.value = State.FailedRequest(searchStr)
                else _state.value = State.Success(movie)
            } catch (t: Throwable) {
                _state.value = State.Error(t.message.toString())
            }
        }
    }

    fun stateResultToString(s: State): String {
        return when (s) {
            is State.Init -> s.text.toString()
            is State.Loading -> "Загрузка..."
            is State.Success -> s.movie.title + " " + s.movie.year
            is State.FailedRequest -> s.text.toString()
            is State.Input -> "Ввод текста..."
            is State.Error -> {
                Log.d("State.Error", "stateResultToString: ${s.text} ")
                "Поиск отменен!"
            }
        }
    }

    fun restartSearch() {
        getMovieJob?.cancel()
    }
}