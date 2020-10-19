package com.example.cryptotradingapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.databinding.FragmentLoginBinding
import com.example.cryptotradingapp.viewmodels.LoginViewModel


class LoginFragment : Fragment() {


    private lateinit var viewModel : LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )


        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(LoginViewModel::class.java)

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener() {
            viewModel.isSignIn = binding.signupOrSignin.isChecked
            val isSuccessful = viewModel.postCredentials()
            Log.i("LOGIN", "isSuccessful string: " + isSuccessful.toString())
            if (isSuccessful) {
                activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fl_wrapper, AccountFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }

    }


}


/*
        name = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        saveButton = view.findViewById(R.id.save_button)


        val signUpOrSignInSwitch: SwitchCompat = view.findViewById(R.id.signup_or_signin)

        saveButton.setOnClickListener(){
            var nameString = name.text.toString()
            var passwordString = password.text.toString()

            val isSignIn:Boolean = signUpOrSignInSwitch.isChecked
            val credentialsString = nameString + ":" + passwordString
            val authHeader = "Basic " + Base64.encodeToString(credentialsString.toByteArray(), Base64.NO_WRAP)

            var response: ResponseMessage

                CoroutineScope(Dispatchers.IO).launch {
                    if(isSignIn){
                         response = accountService.postLoginCredentials(authHeader)
                    }else{
                         response = accountService.postSignUpCredentials(authHeader)
                    }
                    if(response.isSuccessful){
                        Log.i("login", "successful")
                        spEditor.putBoolean(getString(R.string.prefKeyLogin), true)
                        spEditor.putString(getString(R.string.prefKeyUsername), nameString)
                        spEditor.putString(getString(R.string.prefKeyPassword), passwordString)
                        spEditor.commit()
                        Log.i("login", "comitted changes")

                        activity!!.supportFragmentManager.beginTransaction().apply{
                            replace(R.id.fl_wrapper,AccountFragment())
                            addToBackStack(null);
                            commit()
                        }
                        Log.i("login", "changed fragment")

                    } else{
                        Log.i("login", "unsuccessful")
                    }
                }
            //Toast.makeText(activity, response.message, Toast.LENGTH_SHORT).show()


*/