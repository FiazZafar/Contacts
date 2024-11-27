package com.example.contacts;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    ArrayList<ContactsList> contactsListsArr;
    ContactdbHelper dbHelper;

    private final int IMAGE_REQUEST_CODE = 100;

    public CustomAdapter(Context context, ArrayList<ContactsList> arrayList,ContactdbHelper myDbHelper) {
        this.context = context;
        this.contactsListsArr = arrayList;
        this.dbHelper = myDbHelper;
    }
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ContactsList contactsList = contactsListsArr.get(position);
                holder.nameText.setText(contactsList.getName());
                holder.contactText.setText(contactsList.getNumber());
                if (contactsList.getImg()!=null){
                    holder.profilePic.setImageBitmap(contactsList.getImg());
                }else {
                    holder.profilePic.setImageResource(R.drawable.profile_img);
                }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete Contact");
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (contactsListsArr.get(position).id > 0){
                            dbHelper.deleteData(contactsList);
                            contactsListsArr.remove(position);

                        }

                        notifyItemRemoved(position);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddContact.class);
                intent.putExtra("contactName",contactsList.getName());
                intent.putExtra("contactNumber",contactsList.getNumber());
                intent.putExtra("contactId",contactsList.getId());
                intent.putExtra("UpdateContact",2);
                context.startActivity(intent);
                }
        });
    }
    @Override
    public int getItemCount() {
        return contactsListsArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText,contactText;
        ImageView profilePic;


        public  ViewHolder(View itemView){
            super(itemView);

            nameText = itemView.findViewById(R.id.contactName);
            contactText = itemView.findViewById(R.id.contactNumber);
            profilePic = itemView.findViewById(R.id.profileImg);
        }

    }
}
