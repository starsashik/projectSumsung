package com.example.project.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.RecyclerAdapter;
import com.example.project.OurData;
import com.example.project.R;
import com.example.project.UniversityModel;
import com.example.project.databinding.ActivityMain2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity2 extends AppCompatActivity implements RecyclerAdapter.SelectedUser {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMain2Binding binding;
    private BottomNavigationView navigationView;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<UniversityModel> universityModelList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolBar);
        setContentView(binding.getRoot());


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        setData("Home");
        recyclerAdapter = new RecyclerAdapter(universityModelList, this);
        recyclerView.setAdapter(recyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        navigationView = findViewById(R.id.bottom_navigation_menu);
        navigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    changePosition("Home");
                    break;
                case R.id.favorite:
                    changePosition("Favourites");
                    break;
            }
            return true;
        });
    }


    public void setData(String st) {
        universityModelList.clear();
        if (st.equals("Home"))
            for (int i = 0; i < OurData.picturePath.length; i++) {
                universityModelList.add(new UniversityModel(OurData.picturePath[i], OurData.title[i], OurData.uids[i]));
            }
        else if (st.equals("Favourites")) {
            String s = OurData.favor.get(user.getUid());
            Integer[] s2 = new Integer[0];
            if (s.length() > 0) {
                String[] s1 = s.split(";");
                s2 = new Integer[s1.length];
                for (int i = 0; i < s1.length; i++) {
                    s2[i] = Integer.parseInt(s1[i]) - 1;
                }
            }
            List<Integer> intList = new ArrayList<>(Arrays.asList(s2));
            for (int i = 0; i < OurData.picturePath.length; i++) {
                if (intList.contains(i)) {
                    universityModelList.add(new UniversityModel(OurData.picturePath[i], OurData.title[i], OurData.uids[i]));
                }
            }
        }

    }

    public void changePosition(String st) {
        setData(st);
        recyclerAdapter = new RecyclerAdapter(universityModelList, this);
        recyclerView.setAdapter(recyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            OurData.favor.remove(user.getUid());
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_LONG).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectedUser(UniversityModel universityModel) {
        startActivity(new Intent(MainActivity2.this, MainActivity3.class).putExtra("data", universityModel));
    }
}
