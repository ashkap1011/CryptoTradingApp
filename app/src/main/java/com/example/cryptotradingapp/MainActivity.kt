package com.example.cryptotradingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.cryptotradingapp.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val marketFragment = MarketFragment()
        val tradingFragment = TradingFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener(){
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_market -> makeCurrentFragment(marketFragment)
                R.id.ic_trading -> makeCurrentFragment(tradingFragment)
                R.id.ic_account ->  isLoggedIn()
            }

            true
        }


    }


    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper,fragment)
            commit()
        }
    }

    private fun isLoggedIn(){
        var isLoggedIn = false
        val userAccountFragment = AccountFragment()
        val loginFragment = LoginFragment()

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref.contains(getString(R.string.prefKeyLogin))){
            Log.i("pref", "contains key")
            isLoggedIn = sharedPref.getBoolean(getString(R.string.prefKeyLogin),false)
        }

        if(isLoggedIn){
            makeCurrentFragment(userAccountFragment)
        } else{
            makeCurrentFragment(loginFragment)
        }


    }






}