package com.crud.firebase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvName;
    TextView tvBrand;
    TextView tvPrice;

    ItemModel itemModel;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Data");
        setSupportActionBar(toolbar);

        // initial firebase
        db = FirebaseDatabase.getInstance().getReference();

        // initial
        tvName  = (TextView) findViewById(R.id.tvName);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvPrice = (TextView) findViewById(R.id.tvPrice);

        // Receive data from adapter
        itemModel = (ItemModel) getIntent().getSerializableExtra("data");
        if (itemModel != null) {

            tvName.setText(itemModel.getName());
            tvBrand.setText(itemModel.getBrand());
            tvPrice.setText(itemModel.getPrice());
        }
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, DetailActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.edit) {

            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("key", itemModel.getKey());
            intent.putExtra("name", itemModel.getName());
            intent.putExtra("brand", itemModel.getBrand());
            intent.putExtra("price", itemModel.getPrice());
            startActivity(intent);
        }
        else {
            deleteBox();
            Log.d("onOptionsItemSelected", "onOptionsItemSelected: hello");
        }


        return super.onOptionsItemSelected(item);
    }

    public void deleteBox() {
        AlertDialog.Builder alert   = new AlertDialog.Builder(this);
        alert.setMessage("Are You sure Delete ? ");

        alert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                });
        alert.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void delete() {
        db.child("item")
                .child(itemModel.getKey())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailActivity.this, "Data Deleted!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    }
                });
    }
}
