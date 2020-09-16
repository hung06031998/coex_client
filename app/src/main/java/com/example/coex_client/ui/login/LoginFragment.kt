package com.example.coex_client.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.coex_client.R
import com.example.coex_client.databinding.FragmentLoginBinding
import com.example.coex_client.viewmodel.login.LoginViewModel

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login, container, false
        )

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.toSignup.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.btnForgotPass.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_forgotPassFragment)
        }
        binding.btnLogin.setOnClickListener {
            activity?.let { it1 ->
                loginViewModel.signInAuthorize(
                    binding.loginEmail.text.toString(), binding.loginPassword.text.toString(),
                    it1
                )
            }


        }

        loginViewModel.loginSuccesFully.observe(
            viewLifecycleOwner,
            Observer<Boolean> { hasLoginSuccessfully ->
                if (!hasLoginSuccessfully) {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController(this)
                        .navigate(R.id.action_loginFragment_to_mainActivity2)
                }

            })
        return binding.root
    }

}