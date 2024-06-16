package com.inovacaomab.whatsgo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
//nova tentativa 14:27
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Permite JavaScript
        webSettings.setDomStorageEnabled(true); // Habilita DOM storage API

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false; // Deixe o WebView carregar a URL
                }

                if (url.startsWith("whatsapp://")) {
                    try {
                        // Intent para abrir a URL no app correspondente
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true; // Indica que você lidou com a URL
                    } catch (Exception e) {
                        // Se algo der errado, por exemplo, app não instalado
                        Log.e("IntentError", "App para URL não encontrado: " + url, e);
                        return true; // Indica que você lidou com a URL
                    }
                }

                return true; // Para URLs não tratadas, bloqueie a carga
            }
        });

        myWebView.loadUrl("https://www.inovacaomab.com/");
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack(); // Vai para a página anterior do WebView se possível
        } else {
            myWebView.loadUrl("https://www.inovacaomab.com/");  // Carrega a página inicial se não houver mais histórico para voltar
            // super.onBackPressed(); // Comentado para evitar sair do app
        }
    }
}

