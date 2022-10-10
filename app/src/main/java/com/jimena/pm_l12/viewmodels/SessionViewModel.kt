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
        viewModelScope.launch {
            delay(20000L)
            _validAuthToken.value = false
            _logged.value = loggedStatus.notLogged
        }
    }

    fun keepSessionActive(){
        if (this::job.isInitialized && job.isActive) {
            job.cancel()
            _logged.value = loggedStatus.notLogged

        }
    }

}