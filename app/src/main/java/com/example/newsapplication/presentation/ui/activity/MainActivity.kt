package com.example.newsapplication.presentation.ui.activity

import LoginFragment
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.newsapplication.R
import com.example.newsapplication.presentation.ui.fragment.NoInternetFragment
import com.example.newsapplication.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            lifecycleScope.launch(Dispatchers.IO) {
                handleNetworkAvailable()
            }
        }

        override fun onLost(network: Network) {
            lifecycleScope.launch(Dispatchers.IO) {
                handleNetworkLost()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNetworkMonitoring()
        checkInitialConnection()
    }

    private fun setupNetworkMonitoring() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    private fun checkInitialConnection() {
        lifecycleScope.launch(Dispatchers.IO) {
            val isConnected = NetworkUtils.isInternetAvailable(this@MainActivity)
            withContext(Dispatchers.Main) {
                if (isConnected) navigateToLoginFragment()
                else showNoInternetFragment()
            }
        }
    }

    private suspend fun handleNetworkAvailable() {
        withContext(Dispatchers.Main) {
            navigateToLoginFragment()
        }
    }

    private suspend fun handleNetworkLost() {
        withContext(Dispatchers.Main) {
            showNoInternetFragment()
        }
    }

    private fun navigateToLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    private fun showNoInternetFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NoInternetFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}