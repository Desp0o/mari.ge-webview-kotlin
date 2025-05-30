package com.example.mari
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat

// Custom WebView that properly handles performClick for accessibility
class AccessibleWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val myWeb = findViewById<AccessibleWebView>(R.id.MW)

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(myWeb, true)

        myWeb.webViewClient = WebViewClient()
        myWeb.apply {
            loadUrl("https://mari.ge/login")
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }

        // Use GestureDetector for swipe detection
        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                e1?.let { startEvent ->
                    val deltaX = e2.x - startEvent.x

                    if (deltaX > 100) {
                        if (myWeb.canGoBack()) {
                            myWeb.goBack()
                            return true
                        }
                    }
                }
                return false
            }
        })

        myWeb.setOnTouchListener { view, event ->
            val gestureHandled = gestureDetector.onTouchEvent(event)

            // Call performClick for accessibility compliance on ACTION_UP
            if (event.action == MotionEvent.ACTION_UP && !gestureHandled) {
                view.performClick()
            }

            gestureHandled
        }
    }
}