package com.example.learn_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit_data extends AppCompatActivity {

    EditText name, address;
    Button update;
    BiodataTable biodataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        update = findViewById(R.id.update);
        getSupportActionBar().setTitle("Edit Data " + getIntent().getStringExtra("name"));
        biodataTable = new BiodataTable(getApplicationContext());
        name.setText(getIntent().getStringExtra("name"));
        address.setText(getIntent().getStringExtra("address"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biodataTable.update_data(
                        getIntent().getStringExtra("id"),
                        name.getText().toString(),
                        address.getText().toString()
                );
                finish();
            }
        });
    }
}