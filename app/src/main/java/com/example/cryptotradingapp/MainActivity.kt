package com.example.cryptotradingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cryptotradingapp.fragments.AccountFragment
import com.example.cryptotradingapp.fragments.HomeFragment
import com.example.cryptotradingapp.fragments.MarketFragment
import com.example.cryptotradingapp.fragments.TradingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val marketFragment = MarketFragment()
        val tradingFragment = TradingFragment()
        val accountFragment = AccountFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener(){
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_market -> makeCurrentFragment(marketFragment)
                R.id.ic_trading -> makeCurrentFragment(tradingFragment)
                R.id.ic_account -> makeCurrentFragment(accountFragment)
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







}