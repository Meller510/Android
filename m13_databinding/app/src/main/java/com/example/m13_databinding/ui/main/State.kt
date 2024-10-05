package com.example.m13_databinding.ui.main


sealed class State(open val text: String? = null, open val movie: Movie? = null) {

    data object Loading : State()
    data object Init : State(text = initTxt)
    data object Input : State(text = inputTxt)
    data class Success(override val movie: Movie) : State(movie = movie)
    data class Error(override val text: String) : State(text = text)
    data class FailedRequest(var fRequest: String) : State(text ="По запросу '$fRequest' ничего не найдено")
}

private const val initTxt =
    "Привет, вы можете вводить названия для фильмов на английском языке, если таковые есть в нашей базе, то выведется постер, название и год.(Alien,Titanic,Forrest,Hulk,Armageddon и др.)"

private const val inputTxt = "Должно быть не меньше трёх латинских символов !"