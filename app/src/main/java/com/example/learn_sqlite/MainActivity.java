package com.example.learn_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    Button add_data;
    ArrayList<Object> list;

    SQLiteDatabase database;
    Cursor cursor;
    BiodataTable biodataTable;
    Custome_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.listview);
        add_data = findViewById(R.id.add_data);

        biodataTable = new BiodataTable(getApplicationContext());

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Add_data.class));
            }
        });
        get_data();
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_data();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

    void get_data()
    {
        list = new ArrayList<Object>();
        cursor = biodataTable.show_data();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                list.add(new Object(
                        id,
                        name,
                        address
                ));
            }
            adapter = new Custome_adapter(getApplicationContext(), list, MainActivity.this);
            listview.setAdapter(adapter);
        }
    }
}

class Custome_adapter extends BaseAdapter {

    Activity activity;
    BiodataTable biodataTable;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Object> model;

    public Custome_adapter(Context context, ArrayList<Object> list, Activity activity) {
        this.context = context;
        this.model = list;
        this.activity = activity;
        layoutInflater = LayoutInflater.from(context);
        biodataTable = new BiodataTable(context);
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public java.lang.Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView id, name, address;
    Button edit, delete;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View view1 = layoutInflater.inflate(R.layout.list_data, viewGroup, false);

        id = view1.findViewById(R.id.id);
        name = view1.findViewById(R.id.name);
        address = view1.findViewById(R.id.address);

        id.setText(model.get(position).getId());
        name.setText(model.get(position).getName());
        address.setText(model.get(position).getAddress());

        edit = view1.findViewById(R.id.edit);
        delete = view1.findViewById(R.id.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_data.class);
                intent.putExtra("id", model.get(position).getId());
                intent.putExtra("name", model.get(position).getName());
                intent.putExtra("address", model.get(position).getAddress());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Warning");
                builder.setMessage("Are you sure want to delete this?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        biodataTable.delete_data(model.get(position).getId());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view1;
    }
}