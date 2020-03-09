package com.crud.firebase;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    FloatingActionButton fabCreate;

    private DatabaseReference db;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemModel> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Listing Data");
        setSupportActionBar(toolbar);

        // inisisasi RecycleView dan komponen
        rvView      = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        listData();

        // create
        fabCreate = findViewById(R.id.fabCreate);
        fabCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == fabCreate) {
            startActivity(new Intent(this, CreateActivity.class));
        }
    }

    public void listData() {
        /* inisiasi dan mengambil firebase database reference */
        db = FirebaseDatabase.getInstance().getReference();

        /* mengambil data dari firebase realtime database */
        db.child("item").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* data ada */
                listItem = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /*
                    MAPPING data pada DataSnapshot ke dalam object item
                    dan juga menyimpan primary key pada object item
                    untuk keperluan update dan delete data
                     */

                    ItemModel itemModel = noteDataSnapshot.getValue(ItemModel.class);
                    itemModel.setKey(noteDataSnapshot.getKey());

                    /*
                    Menambanhkan objek item yang sudah di mapping
                    ke dalam arrayList
                     */
                    listItem.add(itemModel);
                }

                Log.d("onDataChange", listItem.toString());
                /*
                inisiasi adapter dan data barang dalam bentuk arrayList
                dan mengeset adapter ke dalam RecycleView
                 */
                adapter = new MainActivityAdapter(listItem, MainActivity.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelledList", databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
