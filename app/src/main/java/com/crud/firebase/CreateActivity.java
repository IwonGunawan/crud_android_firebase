package com.crud.firebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    // refers variable Firebase Realtime Database
    private DatabaseReference db;

    private EditText itemName;
    private EditText itemBrand;
    private EditText itemPrice;
    private Button btnCreate;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // toolbar
        toolbar = findViewById(R.id.tlCreate);
        toolbar.setTitle("Create Data");

        // initialize
        itemName    = (EditText) findViewById(R.id.etItemName);
        itemBrand   = (EditText) findViewById(R.id.etItemBrand);
        itemPrice   = (EditText) findViewById(R.id.etItemPrice);
        btnCreate   = (Button) findViewById(R.id.btnCreate);
        db          = FirebaseDatabase.getInstance().getReference();

        btnCreate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnCreate) {
            save();
        }
    }

    public void save() {
        String name     = itemName.getText().toString();
        String brand    = itemBrand.getText().toString();
        String price    = itemPrice.getText().toString();

        if (!isEmpty(name) && !isEmpty(brand) && !isEmpty(price))
        {
            /**
             * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
             * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
             * ketika data berhasil ditambahkan
             */
            ItemModel itemModel = new ItemModel(name, brand, price);

            db.child("item").push().setValue(itemModel).addOnSuccessListener(this, new OnSuccessListener<Void>() {

                @Override
                public void onSuccess(Void aVoid) {
                    itemName.setText("");
                    itemBrand.setText("");
                    itemPrice.setText("");

                    Toast.makeText(CreateActivity.this, "Success Save Data", Toast.LENGTH_LONG).show();
                    finish();
                }

            } );
        }
        else
        {
            Toast.makeText(this, "Data Field can't be empty", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isEmpty(String s) {
        // check empty data
        return TextUtils.isEmpty(s);
    }
}
