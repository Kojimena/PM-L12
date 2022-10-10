package com.jimena.pm_l12.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jimena.pm_l12.R
import com.jimena.pm_l12.databinding.FragmentLoginBinding
import com.jimena.pm_l12.viewmodels.SessionViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding  //El binding es el que se encarga de enlazar la vista con el fragmento
    private val viewModel: SessionViewModel by activityViewModels() //El viewmodel es el que se encarga de la lógica de negocio

    override fun onCreateView( //Aqui se crea la vista
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root //Devuelve la vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.iniciosesion.setOnClickListener{
            binding.apply {
                val correo = correoLogin.editText!!.text.toString()
                val password = passworduserLogin.editText!!.text.toString()
                viewModel.setDataUserLogin(correo, password)
            }
        }
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted{ //Se encarga de recoger los datos del viewmodel
            viewModel.logged.collectLatest {
                val status = viewModel.logged.value
                when (status) {
                    is  SessionViewModel.loggedStatus.logged -> {
                        binding.apply {
                            viewModel.waitingTime()
                            requireView().findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }
                    is SessionViewModel.loggedStatus.notLogged -> {
                        binding.apply {
                            progressUsers.visibility = View.GONE
                            iniciosesion.visibility = View.VISIBLE
                        }
                    }
                    is SessionViewModel.loggedStatus.failure -> {
                        binding.apply {
                            progressUsers.visibility = View.GONE
                            iniciosesion.visibility = View.VISIBLE
                        }
                        Toast.makeText(requireContext(), "Error de autenticación", Toast.LENGTH_SHORT).show()
                    }
                    is SessionViewModel.loggedStatus.checkinglogin -> {
                        binding.apply {
                            progressUsers.visibility = View.VISIBLE
                            iniciosesion.visibility = View.GONE
                        }

                    }

                }
            }
        }
    }


}