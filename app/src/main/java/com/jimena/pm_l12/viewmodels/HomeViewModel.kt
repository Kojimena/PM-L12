package com.jimena.pm_l12.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){


    private val _status = MutableStateFlow<Status>(Status.default(Constants.DEFAULT_MESSG)) //Definimos el estado inicial del mensaje
    val status : StateFlow<Status> = _status //Definimos el estado como un flujo de estado


    private val _progessBar = MutableStateFlow<Boolean>(false) // Acá se define el estado de la barra de progreso
    val progressBar : StateFlow<Boolean> = _progessBar

    private val _timer = MutableStateFlow<Int>(Constants.DEFAULT_TIMER) // Acá se define el estado del timer
    val timer : StateFlow<Int> = _timer


    sealed class Status{ // Sealed class para manejar los estados de la pantalla
        class default(val message: String): Status()
        class succes(val message: String): Status()
        class failure(val message: String): Status()
        class empty(val message: String): Status()
    }

    class Constants { //Definimos los mensajes que se mostrarán en la pantalla
        companion object{
            const val DEFAULT_TIMER = 1
            const val DEFAULT_MESSG = "Selecciona una opción"
            const val SUCCES_MESSG = "¡Operación exitosa!"
            const val FAILURE_MESSG = "¡Operación fallida!"
            const val EMPTY_MESSG = "Sin resultados"
        }
    }

    fun startTimer(){ //Función para iniciar el timer
        viewModelScope.launch {
            //delay de 10 segundos
            delay(10000)
            _timer.value = 0
        }
    }

    private fun progressBarActualization(){
        viewModelScope.launch {
            _progessBar.value = true //Se muestra la barra de progreso
            delay(2000L) //Se espera 2 segundos
            _progessBar.value = false //Se oculta la barra de progreso
        }
    }

    fun defaultfunc(){ //Función que se ejecuta al presionar el botón de "Default"
        progressBarActualization()
        _status.value = Status.default(Constants.DEFAULT_MESSG)
    }

    fun succesfunc(){ //Función que se ejecuta al presionar el botón de "Succes"
        progressBarActualization()
        _status.value = Status.succes(Constants.SUCCES_MESSG)
    }

    fun failurefunc(){ //Función que se ejecuta al presionar el botón de "Failure"
        progressBarActualization()
        _status.value = Status.failure(Constants.FAILURE_MESSG)
    }

    fun emptyfunc(){ //Función que se ejecuta al presionar el botón de "Empty"
        progressBarActualization()
        _status.value = Status.empty(Constants.EMPTY_MESSG)
    }


}