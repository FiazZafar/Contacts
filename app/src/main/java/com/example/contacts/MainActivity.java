package com.example.contacts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    CustomAdapter customAdapter;
    ContactsList contactsList;
    ContactdbHelper dbHelper ;
    ArrayList<ContactsList> contactsListArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new ContactdbHelper(this);
        contactsListArrayList = dbHelper.fetchData();

        backBtn = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.contactsRecycler);
        floatingActionButton = findViewById(R.id.floatingAddButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter customAdapter = new CustomAdapter(this,contactsListArrayList,dbHelper);
        recyclerView.setAdapter(customAdapter);
        backBtn.setOnClickListener(view -> finish());


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddContact.class);
                intent.putExtra("AddBtn",1);
                startActivity(intent);
                finish();
            }
        });

    }



}