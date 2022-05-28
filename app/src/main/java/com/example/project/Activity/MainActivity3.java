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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding binding;
    private UniversityModel universityModel;
    private WebView webView;
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolBar2);
        Intent intent = getIntent();
        universityModel = (UniversityModel) intent.getSerializableExtra("data");


        setContentView(binding.getRoot());
        OpenWeb();
    }

    void OpenWeb() {
        webView = binding.webviewId;
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

        webView.loadUrl(OurData.urls[Integer.parseInt(universityModel.getUid()) - 1]);
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
            String s = OurData.favor.get(user.getUid());
            if (s.length() > 0) {
                String[] s1 = s.split(";");
                if (!s.equals(universityModel.getUid() + ";")) {
                    int[] s2 = new int[s1.length + 1];
                    for (int i = 0; i < s1.length; i++) {
                        s2[i] = Integer.parseInt(s1[i]);
                    }
                    s2[s1.length] = Integer.parseInt(universityModel.getUid());
                    Arrays.sort(s2, 0, s2.length - 1);
                    String s3 = "";
                    for (int j : s2) {
                        s3 += "" + j + ";";
                    }
                    OurData.favor.remove(user.getUid());
                    OurData.favor.put(user.getUid(), s3);
                } else {
                    int[] s2 = new int[s1.length];
                    for (int i = 0; i < s1.length; i++) {
                        s2[i] = Integer.parseInt(s1[i]);
                    }
                    Arrays.sort(s2, 0, s2.length - 1);
                    String s3 = "";
                    for (int j : s2) {
                        if (j != Integer.parseInt(universityModel.getUid())) {
                            s3 += "" + j + ";";
                        }
                    }
                    OurData.favor.remove(user.getUid());
                    OurData.favor.put(user.getUid(), s3);
                }
            }else{
                OurData.favor.remove(user.getUid());
                OurData.favor.put(user.getUid(), universityModel.getUid() + ";");
            }
            return true;
        } else if (id == R.id.logout_2) {
            OurData.favor.remove(user.getUid());
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