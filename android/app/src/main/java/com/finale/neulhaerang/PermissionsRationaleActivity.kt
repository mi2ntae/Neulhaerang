package com.finale.neulhaerang

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

class PermissionsRationaleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWebView()
        }
    }
}

@Composable
fun MyWebView() {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl("https://k9a502.p.ssafy.io/privacy-policy")
        }
    }

    AndroidView(modifier = Modifier.padding(8.dp).fillMaxSize(), factory = { webView })
}
