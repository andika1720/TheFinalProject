package com.example.thefinalproject.ui.fragment.forgotpass

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentOtpCodeBinding
import com.example.thefinalproject.databinding.FragmentResetPasswordBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassRequest
import com.example.thefinalproject.network.model.user.resetpassword.ChangePasswordRequest
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject

class ResetPassword : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private val viewmodel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            val fragmet = InputEmail()
            moveFragment(fragmet)

        }
        val emailForgot = arguments?.getString("emailForgotPass")
        Log.e("emailForgot",emailForgot!!)
        binding.btnChangePassword.setOnClickListener {
            val passBaru = binding.etOldPass.text.toString()
            val validNewPass = binding.etNewPass.text.toString()
            if(passBaru.length < 8 || passBaru.length > 12){
                binding.etOldPass.error = "Password harus terdiri 8-12 karakter"

            }else if (passBaru != validNewPass){
                binding.etOldPass.error = "Password tidak sama"
                binding.etNewPass.error = "Password tidak sama"
            }else{
                val mbundle = Bundle()
//                mbundle.putString("passwordForgot",passBaru)
//                mbundle.putString("emailnext",emailForgot)
                forgotPassword(emailForgot,passBaru)
            }
        }
    }
    fun forgotPassword(email:String,password:String){
        viewmodel.forgotPassword(PostForgotPassRequest(email)).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"OTP Dikirim Ke email", Toast.LENGTH_SHORT).show()
                    val fragment = OtpForgotPassword()
                    val bundle = Bundle()
                    bundle.putString("emailForgot1",email)
                    bundle.putString("passwordForgot",password)
                    fragment.arguments = bundle
                    val containerId = R.id.container3
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(containerId, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
//                    moveFragment(fragment)
                }
                Status.ERROR -> {
                    val errorMessage = it.message ?: "Error Occurred!"
                    Log.d("errorOTP", errorMessage)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    Log.d("load", "Loading")
                }
            }
        }
    }
    fun moveFragment(fragment:Fragment){
        val bundle = Bundle()
//    Ganti dengan nama fragment tujuan
        val nextFragment = fragment
        nextFragment.arguments = bundle
//    Ganti dengan ID kontainer di mana Anda ingin menambahkan atau mengganti fragment
        val containerId = R.id.container3
//    Lakukan transaksi fragment
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, nextFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}