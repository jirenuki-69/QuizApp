package com.example.quizapp.Clases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Un Factory method que se usa en los viewModels para instanciar View Models que necesiten de un
 * parámetro
 */

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>):T = f() as T
    }