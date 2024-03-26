package com.example.foodsapp.ui.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodsapp.R
import com.example.foodsapp.databinding.FragmentRegisterBinding
import com.example.foodsapp.ui.viewmodel.AuthViewModel
import com.example.foodsapp.util.UIState
import com.example.foodsapp.util.hide
import com.example.foodsapp.util.show
import com.example.foodsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        register()
        observeRegister()
        return binding.root
    }

    private fun register() {
        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.register(email, password)
        }
    }
    private fun observeRegister() {
        viewModel.auth.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBarRegister.show()
                }
                is UIState.Success -> {
                    toast("Kayıt Başarılı")
                    binding.progressBarRegister.hide()
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                    requireActivity().finish()
                }
                is UIState.Failure -> {
                    binding.progressBarRegister.hide()
                    toast(state.error)
                }
            }
        }
    }
}