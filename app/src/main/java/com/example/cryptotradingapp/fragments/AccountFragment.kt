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
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.databinding.FragmentAccountBinding
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.network.RetrofitInstance

import com.example.cryptotradingapp.viewmodels.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    private lateinit var viewModel: AccountViewModel
    private val accountService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO maybe start an intent here starts an activity for login.Then use shared preferences.

        //getData()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_account, container, false
        )

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(AccountViewModel::class.java)

        return binding.root
    }


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
    }

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

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}