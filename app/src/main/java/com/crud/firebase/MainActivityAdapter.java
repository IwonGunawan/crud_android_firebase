package com.crud.firebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private ArrayList<ItemModel> listItem;
    private Context context;

    public MainActivityAdapter(ArrayList<ItemModel> items, Context ctx) {
        /*
        inisiasi data dan variable yang akan digunakan
         */
        listItem = items;
        context = ctx;
    }

    @NonNull
    @Override
    public MainActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        inisiasi viewholder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);

        /* set ukuran view, margin, padding dan parameter lainnya */
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapter.ViewHolder holder, final int position) {
        /*
        menampilkan data pada view
         */
        final String name   = listItem.get(position).getName();
        final String price  = listItem.get(position).getPrice();

        holder.itemName.setText(name);
        holder.itemPrice.setText(price);
        holder.ltListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // untuk detail data
                context.startActivity(DetailActivity.getActIntent((Activity) context).putExtra("data", listItem.get(position)));
            }
        });

        holder.cvListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(DetailActivity.getActIntent((Activity) context).putExtra("data", listItem.get(position)));
            }
        });

    }

    @Override
    public int getItemCount() {
        /* mengembalikan jumlah item pada barang */
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
        inisiasi view
        untuk variable saat ini data hanya menggunakan strian untuk tiap item
        dan view hanya memiliki 1 textview
         */
        CardView cvListing;
        LinearLayout ltListing;
        TextView itemName;
        TextView itemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvListing   = (CardView) itemView.findViewById(R.id.cv_listing);
            ltListing   = (LinearLayout) itemView.findViewById(R.id.layoutListing);
            itemName    = (TextView) itemView.findViewById(R.id.item_name);
            itemPrice   = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
}
