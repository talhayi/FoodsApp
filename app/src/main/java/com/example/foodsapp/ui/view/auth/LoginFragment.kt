package com.example.foodsapp.ui.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodsapp.R
import com.example.foodsapp.databinding.FragmentLoginBinding
import com.example.foodsapp.ui.viewmodel.AuthViewModel
import com.example.foodsapp.util.UIState
import com.example.foodsapp.util.hide
import com.example.foodsapp.util.show
import com.example.foodsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.registerLabel.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        login()
        observeLogin()
        currentUser()
        return binding.root
    }

    private fun login() {
        binding.buttonLogin.setOnClickListener {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        viewModel.login(email, password)
    }}

    private fun observeLogin() {
        viewModel.auth.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBarLogin.show()
                }

                is UIState.Success -> {
                    toast("Giriş Başarılı")
                    binding.progressBarLogin.hide()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    requireActivity().finish()
                }

                is UIState.Failure -> {
                    binding.progressBarLogin.hide()
                    toast(state.error)
                }
            }
        }
    }

    private fun currentUser(){
        val currentUser = viewModel .currentUser()
        if (currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            requireActivity().finish()
        }

    }
}