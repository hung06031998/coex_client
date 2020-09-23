package com.example.coex_client.ui.signup

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
import com.example.coex_client.R
import com.example.coex_client.databinding.FragmentSignupBinding
import com.example.coex_client.viewmodel.signup.SignUpViewModel

class SignupFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater,
            R.layout.fragment_signup, container, false
        )

        val signupViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.txtLogin.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener { view: View ->
            signupViewModel.signUp(
                binding.edtFullName.text.toString(),
                binding.edtEmail.text.toString(),
                binding.edtPhoneNumber.text.toString(),
                binding.edtPassword.text.toString()
            )
            signupViewModel.onSignUpSucssessfully.observe(
                viewLifecycleOwner,
                Observer<Boolean> { hasSignUpSuccessfully ->
                    if (hasSignUpSuccessfully) {
                        Toast.makeText(
                            context,
                            "Sign Up Successfully! Please check your email to verify you account",
                            Toast.LENGTH_SHORT
                        ).show()
                        view.findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                        signupViewModel.onSignUpComplete()
                    } else {
                        when{
                            binding.edtPassword.text.toString() != binding.edtConfirmPassword.text.toString() -> Toast.makeText(context,"Password does not match !",Toast.LENGTH_SHORT).show()
                            binding.edtEmail.text.toString() == "" -> Toast.makeText(context,"Please enter email !",Toast.LENGTH_SHORT).show()
                            binding.edtPassword.text.toString() == "" -> Toast.makeText(context,"Please enter password !",Toast.LENGTH_SHORT).show()
                            binding.edtConfirmPassword.text.toString() == "" -> Toast.makeText(context,"Please enter password confirm !",Toast.LENGTH_SHORT).show()
                            binding.edtFullName.text.toString() == "" -> Toast.makeText(context,"Please enter your name !",Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }

        return binding.root
    }

}