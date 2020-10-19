package com.example.cryptotradingapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.adapters.WalletAdapter
import com.example.cryptotradingapp.databinding.FragmentAccountBinding
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.network.RetrofitInstance

import com.example.cryptotradingapp.viewmodels.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    private lateinit var viewModel: AccountViewModel





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO maybe start an intent here starts an activity for login.Then use shared preferences.

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_account, container, false
        )

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(AccountViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = WalletAdapter()
        binding.cryptocurrencyList.adapter = adapter

        Log.i("data",(viewModel.wallet.value).toString())


        viewModel.wallet.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.setLifecycleOwner(this)


        return binding.root
    }

    /*
    fun getData(){

        Log.i("MYMY", "HI")

        CoroutineScope(Dispatchers.IO).launch {
            val wallet = accountService.getWallet()
            val currencyListWallet = wallet.body()?.listIterator()
            if (currencyListWallet != null) {
                while (currencyListWallet.hasNext()) {
                    val currency = currencyListWallet.next()
                    Log.i("MYMY", currency.symbol)
                }
            }
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton: Button = view.findViewById(R.id.logoutBtn)

        var sp = activity?.getSharedPreferences(getString(R.string.user_session),Context.MODE_PRIVATE) ?: return
        var spEditor = sp?.edit()

        logoutButton.setOnClickListener{
            spEditor.putBoolean(getString(R.string.pref_key_login), false)
            spEditor.remove(getString(R.string.pref_key_username))
            spEditor.remove(getString(R.string.pref_key_password))
            spEditor.commit()
        }
        //todo make it transition to diff fragment

    }

}