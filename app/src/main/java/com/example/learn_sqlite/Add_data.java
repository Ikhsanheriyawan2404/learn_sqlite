package com.example.learn_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_data extends AppCompatActivity {

    BiodataTable biodataTable;
    EditText name, address;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        save = findViewById(R.id.save);

        biodataTable = new BiodataTable(getApplicationContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_data();
            }
        });
    }

    void save_data()
    {
        biodataTable.save_data(
                name.getText().toString(),
                address.getText().toString()
        );
    }
}