package com.crud.firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    public String key;

    private DatabaseReference db;
    private TextView etItemName;
    private TextView etItemBrand;
    private TextView etItemPrice;
    private Button btnUpdate;

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, EditActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit");

        // Receive Data from intent
        Intent intent = getIntent();
        key             = intent.getStringExtra("key");
        String name     = intent.getStringExtra("name");
        String brand    = intent.getStringExtra("brand");
        String price    = intent.getStringExtra("price");

        // initial firebase
        db = FirebaseDatabase.getInstance().getReference();

        // initial
        etItemName      = (TextView) findViewById(R.id.etItemName);
        etItemBrand     = (TextView) findViewById(R.id.etItemBrand);
        etItemPrice     = (TextView) findViewById(R.id.etItemPrice);
        btnUpdate       = (Button) findViewById(R.id.btnUpdate);

        // setText
        etItemName.setText(name);
        etItemBrand.setText(brand);
        etItemPrice.setText(price);

        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpdate) {
            updateItem();
        }
    }

    public void updateItem() {
        String name     = etItemName.getText().toString();
        String brand    = etItemBrand.getText().toString();
        String price    = etItemPrice.getText().toString();

        ItemModel itemModel = new ItemModel(name, brand, price);
        db.child("item")
                .child(key)
                .setValue(itemModel)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditActivity.this, "Success Update Data", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                    }
                });
    }
}
