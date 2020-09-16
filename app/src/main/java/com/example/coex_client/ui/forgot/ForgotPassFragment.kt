package com.example.coex_client.ui.forgot

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.coex_client.R
import com.example.coex_client.databinding.FragmentForgotPassBinding
import com.example.coex_client.viewmodel.forgot.ForgotPassViewModel


class ForgotPassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentForgotPassBinding>(
            inflater,
            R.layout.fragment_forgot_pass,
            container,
            false
        )
        val sharedPref = context?.getSharedPreferences("ForgotPass", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        val forgotPassViewModel = ViewModelProvider(this).get(ForgotPassViewModel::class.java)




        binding.btnSend.setOnClickListener {
            forgotPassViewModel.forgotPass(binding.forgotEmail.text.toString())
            editor?.putString("email", binding.forgotEmail.text.toString())?.apply()
        }



        forgotPassViewModel.onSendSuccessfully.observe(
            viewLifecycleOwner,
            Observer<Boolean> { hasSendSuccessfully ->
                if (hasSendSuccessfully) {
                    findNavController(this).navigate(R.id.action_forgotPassFragment_to_resetPassFragment)
                    Toast.makeText(
                        context,
                        "Please check you email to get OTP code!",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(context, "Invalid email!", Toast.LENGTH_SHORT).show()
                }
            })



        return binding.root
    }

}