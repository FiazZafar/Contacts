package com.example.contacts;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.URI;

public class AddContact extends AppCompatActivity {
    private final int CAMER_IMAGE_REQUEST_CODE = 100;
    private final int GALLERY_IMAGES_REQUEST_CODE = 200;
    ImageView profilePic;
    FloatingActionButton add_btn;
    EditText nametxt,numberTxt;
    Button ad_update_btn;
    Bitmap bitmap;
    ContactdbHelper dbHelper ;
    int addContactClicked ;
    int updateContactClicked ;
    String contactName;
    String contactNumber;
    int contactId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profilePic = findViewById(R.id.profile_image);
        add_btn = findViewById(R.id.fab_add);
        nametxt = findViewById(R.id.nametxt);
        numberTxt = findViewById(R.id.numberTxt);
        ad_update_btn = findViewById(R.id.ad_update_btn);



        Intent intent = getIntent();
        contactName = intent.getStringExtra("contactName");
        contactNumber = intent.getStringExtra("contactNumber");
        contactId = intent.getIntExtra("contactId",0);

     addContactClicked = intent.getIntExtra("AddBtn",0);
     updateContactClicked = intent.getIntExtra("UpdateContact",0);

        if (addContactClicked == 1){
            ad_update_btn.setText("Add_Contact");
        }else {
            ad_update_btn.setText("Update_Contact");
            nametxt.setText(contactName);
            numberTxt.setText(contactNumber);
        }
        ad_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name =  nametxt.getText().toString().trim();
               String phone_no = numberTxt.getText().toString().trim();
                ContactsList contactsList = new ContactsList();
                contactsList.name = name;
                contactsList.number = phone_no;
                dbHelper = new ContactdbHelper(AddContact.this);
                Intent intent  = new Intent(AddContact.this,MainActivity.class);

                if (addContactClicked == 1){
                    if (name.isEmpty() || phone_no.isEmpty()){
                        Toast.makeText(AddContact.this, "Credentials are empty", Toast.LENGTH_SHORT).show();
                    }else {

                        dbHelper.insertData(bitmap,contactsList);
                        Toast.makeText(AddContact.this, "Data Inserted successfully", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                }else {


                    if (name.isEmpty() || phone_no.isEmpty()){
                        Toast.makeText(AddContact.this, "Credentials are empty", Toast.LENGTH_SHORT).show();
                    }else {
                        contactsList.id = contactId;
                        dbHelper.updateDatas(bitmap, contactsList);

                        Toast.makeText(AddContact.this, "Data Updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }

                }

                           }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddContact.this);
                    builder.setTitle("Choose Image Source");
                    builder.setItems(new String[]{"Camera", "Gallery"}, (dialog, which) -> {
                        if (which == 0) {
                            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent1,CAMER_IMAGE_REQUEST_CODE);
                        } else if (which == 1) {
                            // User chose Gallery
                            Intent intent2 = new Intent(Intent.ACTION_PICK);
                            intent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent2,GALLERY_IMAGES_REQUEST_CODE);

                        }
                    });
                    builder.show();
                }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK ){
            if (requestCode == CAMER_IMAGE_REQUEST_CODE){
                bitmap =(Bitmap) data.getExtras().get("data");
                profilePic.setImageBitmap(bitmap);
                Log.d("TAG", "Results id  " + bitmap);
            } else if (requestCode == GALLERY_IMAGES_REQUEST_CODE) {
                Uri selectedImage = data.getData();

                try {
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                    profilePic.setImageURI(selectedImage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}