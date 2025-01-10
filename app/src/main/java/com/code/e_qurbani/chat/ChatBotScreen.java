package com.code.e_qurbani.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.code.e_qurbani.R;
import com.code.e_qurbani.databinding.ActivityChatBotScreenBinding;

public class ChatBotScreen extends AppCompatActivity {

    ActivityChatBotScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBotScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                binding.progressBar.setVisibility(android.view.View.GONE);
            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Show the progress bar and update its progress
                binding.progressBar.setVisibility(android.view.View.VISIBLE);
                binding.progressBar.setProgress(newProgress);
            }
        });

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.loadUrl("https://mediafiles.botpress.cloud/d313bbf2-8d94-42ac-8c95-4858b4fdaba2/webchat/bot.html");


    }
}