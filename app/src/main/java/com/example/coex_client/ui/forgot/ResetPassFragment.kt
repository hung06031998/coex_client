package com.example.coex_client.ui.forgot

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.coex_client.databinding.FragmentResetPassBinding
import com.example.coex_client.viewmodel.forgot.ResetPassViewModel


class ResetPassFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentResetPassBinding>(inflater,R.layout.fragment_reset_pass,container,false)

        val resetPassViewModel = ViewModelProvider(this).get(ResetPassViewModel::class.java)

        val sharedPref = context?.getSharedPreferences("ForgotPass", Context.MODE_PRIVATE)
        val email : String?  = sharedPref?.getString("email","")

        binding.btnReset.setOnClickListener {
            resetPassViewModel.resetPass(email!!,binding.edtOTP.text.toString(),binding.edtNewPass.text.toString(),binding.edtConfNewPass.text.toString())
        }

        resetPassViewModel.onResetSuccessfully.observe(viewLifecycleOwner, Observer<Boolean> { hasResetSucessfully ->
            if(!hasResetSucessfully){
                Toast.makeText(context,"Failure!",Toast.LENGTH_SHORT).show()
                findNavController(this).navigate(R.id.action_resetPassFragment_to_loginFragment)

            }
            else{
                Toast.makeText(context,"Reset Password Successfully!",Toast.LENGTH_SHORT).show()
                findNavController(this).navigate(R.id.action_resetPassFragment_to_loginFragment)
            }
        })
        Log.d("Reset", "email: $email")
        return binding.root
    }

}