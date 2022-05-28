package com.example.project.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.OurData;
import com.example.project.R;
import com.example.project.UniversityModel;
import com.example.project.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding binding;
    private UniversityModel universityModel;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolBar2);
        Intent intent = getIntent();
        universityModel = (UniversityModel) intent.getSerializableExtra("data");

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity3.this, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        webView.loadUrl(OurData.urls[Integer.parseInt(universityModel.getUid())]);
        setContentView(binding.getRoot());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.favor) {
            Toast.makeText(this, universityModel.getUid(), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.logout_2) {
            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(intent);
            this.finish();
            Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.back) {
            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            startActivity(intent);
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}