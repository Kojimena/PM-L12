package com.jimena.pm_l12.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel : ViewModel() {

    private val _logged = MutableStateFlow<loggedStatus>(loggedStatus.notLogged)
    val logged: StateFlow<loggedStatus> = _logged

    private val _emaillog = MutableStateFlow("")
    val username: StateFlow<String> = _emaillog

    private val _passwordlog = MutableStateFlow("")
    val password: StateFlow<String> = _passwordlog

    private val _validAuthToken = MutableStateFlow(false)
    val validAuthToken: StateFlow<Boolean> = _validAuthToken

    private lateinit var job: Job


    sealed class loggedStatus {
        object notLogged: loggedStatus()
        object logged: loggedStatus()
        object checkinglogin: loggedStatus()
        object failure: loggedStatus()
    }

    fun setDataUserLogin(username: String, password: String){
        _emaillog.value = username
        _passwordlog.value = password

        _logged.value = loggedStatus.checkinglogin
        viewModelScope.launch {
            //delay de 2 segundos para simular el tiempo de respuesta del servidor
            delay(2000)
            if (username == "Her21199@uvg.edu.gt" && password == "Her21199@uvg.edu.gt"){
                _logged.value = loggedStatus.logged
                _validAuthToken.value = true
            }else{
                _logged.value = loggedStatus.failure
            }
        }
    }

    fun waitingTime(){
       job = viewModelScope.launch { //asignamos la tarea a una variable para poder cancelarla
            //delay de 30 segundos para simular el tiempo de espera del servidor
            delay(30000)
            _validAuthToken.value = false
            _logged.value = loggedStatus.notLogged
        }
    }

    fun keepSessionActive(){
        if (this::job.isInitialized && job.isActive) { //si la tarea esta activa la cancelamos
            job.cancel()
            _logged.value = loggedStatus.notLogged //si se cancela la tarea, se vuelve a poner el estado a notLogged
            _validAuthToken.value = true
        }


    }

}