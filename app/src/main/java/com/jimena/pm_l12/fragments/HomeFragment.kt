package com.jimena.pm_l12.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.jimena.pm_l12.R
import com.jimena.pm_l12.databinding.FragmentHomeBinding
import com.jimena.pm_l12.viewmodels.HomeViewModel
import com.jimena.pm_l12.viewmodels.SessionViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val sessionViewModel: SessionViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView( //Creamos la vista
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false) //Asignamos el binding a la vista
        return binding.root //Devolvemos la vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()

    }

    private fun setObservers() {
        //Aqui se observa el livedata del viewmodel y se actualiza la UI
        homeViewModel.startTimer()
        lifecycleScope.launch{
            lifecycleScope.launch{
                sessionViewModel.validAuthToken.collectLatest { valueToken ->
                    if (!valueToken && homeViewModel.timer.value == 0) {
                        requireView().findNavController().navigate(
                            R.id.action_homeFragment_to_loginFragment
                        )
                    }
                }
            }
                /*homeViewModel.timer.collectLatest {
                    if (it == 0 && !sessionViewModel.validAuthToken.value!!) {
                        requireView().findNavController()
                            .navigate(R.id.action_homeFragment_to_loginFragment)
                    }
                }*/

        }

        lifecycleScope.launch {
            homeViewModel.status.collectLatest { status ->
                when (status) {
                    is HomeViewModel.Status.default -> {
                        lifecycleScope.launch {
                            homeViewModel.progressBar.collectLatest { progressValue ->
                                if (!progressValue) {
                                    binding.progressHomeLayout.visibility = View.GONE
                                    binding.imageHomeLayout.visibility = View.VISIBLE
                                    binding.textStatusHomeLayout.visibility = View.VISIBLE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = true
                                        buttonSuccesHomeLayout.isEnabled = true
                                        buttonFailureHomeLayout.isEnabled = true
                                        buttonEmptyHomeLayout.isEnabled = true
                                        imageHomeLayout.setImageDrawable(
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.ic_defaultimg,
                                                null
                                            )
                                        )
                                        textStatusHomeLayout.text = status.message
                                    }
                                }
                                else {
                                    binding.progressHomeLayout.visibility = View.VISIBLE
                                    binding.imageHomeLayout.visibility = View.GONE
                                    binding.textStatusHomeLayout.visibility = View.GONE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = true
                                        buttonSuccesHomeLayout.isEnabled = false
                                        buttonFailureHomeLayout.isEnabled = false
                                        buttonEmptyHomeLayout.isEnabled = false
                                    }
                                }
                            }
                        }
                    }
                    is HomeViewModel.Status.succes -> {
                        lifecycleScope.launch {
                            homeViewModel.progressBar.collectLatest { progressValue ->
                                if (!progressValue) {
                                    binding.progressHomeLayout.visibility = View.GONE
                                    binding.imageHomeLayout.visibility = View.VISIBLE
                                    binding.textStatusHomeLayout.visibility = View.VISIBLE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = true
                                        buttonSuccesHomeLayout.isEnabled = true
                                        buttonFailureHomeLayout.isEnabled = true
                                        buttonEmptyHomeLayout.isEnabled = true
                                        imageHomeLayout.setImageDrawable(
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.ic_succesimg,
                                                null
                                            )
                                        )
                                        textStatusHomeLayout.text = status.message
                                    }
                                }
                                else {
                                    binding.progressHomeLayout.visibility = View.VISIBLE
                                    binding.imageHomeLayout.visibility = View.GONE
                                    binding.textStatusHomeLayout.visibility = View.GONE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = false
                                        buttonSuccesHomeLayout.isEnabled = true
                                        buttonFailureHomeLayout.isEnabled = false
                                        buttonEmptyHomeLayout.isEnabled = false
                                    }
                                }
                            }
                        }
                    }
                    is HomeViewModel.Status.failure -> {
                        lifecycleScope.launch {
                            homeViewModel.progressBar.collectLatest { progressValue ->
                                if (!progressValue) {
                                    binding.progressHomeLayout.visibility = View.GONE
                                    binding.imageHomeLayout.visibility = View.VISIBLE
                                    binding.textStatusHomeLayout.visibility = View.VISIBLE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = true
                                        buttonSuccesHomeLayout.isEnabled = true
                                        buttonFailureHomeLayout.isEnabled = true
                                        buttonEmptyHomeLayout.isEnabled = true
                                        imageHomeLayout.setImageDrawable(
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.ic_failure,
                                                null
                                            )
                                        )
                                        textStatusHomeLayout.text = status.message
                                    }
                                }
                                else {
                                    binding.progressHomeLayout.visibility = View.VISIBLE
                                    binding.imageHomeLayout.visibility = View.GONE
                                    binding.textStatusHomeLayout.visibility = View.GONE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = false
                                        buttonSuccesHomeLayout.isEnabled = false
                                        buttonFailureHomeLayout.isEnabled = true
                                        buttonEmptyHomeLayout.isEnabled = false
                                    }
                                }
                            }
                        }
                    }
                    is HomeViewModel.Status.empty -> {
                        lifecycleScope.launch {
                            homeViewModel.progressBar.collectLatest { progressValue ->
                                if (!progressValue) {
                                    binding.progressHomeLayout.visibility = View.GONE
                                    binding.imageHomeLayout.visibility = View.VISIBLE
                                    binding.textStatusHomeLayout.visibility = View.VISIBLE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = true
                                        buttonSuccesHomeLayout.isEnabled = true
                                        buttonFailureHomeLayout.isEnabled = true
                                        buttonEmptyHomeLayout.isEnabled = true
                                        imageHomeLayout.setImageDrawable(
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.ic_empty,
                                                null
                                            )
                                        )
                                        textStatusHomeLayout.text = status.message
                                    }
                                }
                                else {
                                    binding.progressHomeLayout.visibility = View.VISIBLE
                                    binding.imageHomeLayout.visibility = View.GONE
                                    binding.textStatusHomeLayout.visibility = View.GONE
                                    binding.apply {
                                        buttonDefaultHomeLayout.isEnabled = false
                                        buttonSuccesHomeLayout.isEnabled = false
                                        buttonFailureHomeLayout.isEnabled = false
                                        buttonEmptyHomeLayout.isEnabled = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private fun setListeners() {

        binding.apply {
            //Acá se asignan los listeners a los botones
            buttonSesionActivaHomeLayout.setOnClickListener {
                //hacer un toast mensaje de sesion activa
                Toast.makeText(requireContext(), "La sesión se mantendrá activa", Toast.LENGTH_SHORT).show()
                //mantener la sesión activa
                sessionViewModel.keepSessionActive()
            }
            buttonCerrarSesionHomeLayout.setOnClickListener {
                //Cerrar sesión
                sessionViewModel.keepSessionActive()
                requireView().findNavController()
                    .navigate(R.id.action_homeFragment_to_loginFragment)
            }
            buttonDefaultHomeLayout.setOnClickListener{
                homeViewModel.Defaultfunc()
            }

            buttonSuccesHomeLayout.setOnClickListener {
                homeViewModel.Succesfunc()
            }

            buttonFailureHomeLayout.setOnClickListener {

                homeViewModel.Failurefunc()
            }

            buttonEmptyHomeLayout.setOnClickListener {

                homeViewModel.Emptyfunc()
            }

        }

    }




}