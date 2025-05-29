package com.example.mari

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val myWeb = findViewById<WebView>(R.id.MW)
        myWeb.webViewClient = WebViewClient()
        myWeb.apply {
            loadUrl("https://mari.ge/login")
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

        }
    }
}