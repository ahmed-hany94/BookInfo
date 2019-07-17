package com.arkdev.bookinfo.ItemView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arkdev.bookinfo.R;

public class BookItemView extends RecyclerView.ViewHolder{

    private TextView bookName, bookPrice;

    public BookItemView(@NonNull View itemView) {
        super(itemView);

        bookName = itemView.findViewById(R.id.book_name);
        bookPrice = itemView.findViewById(R.id.book_price);
    }

    public void bindView(String name, String price){
        bookName.setText(name);
        bookPrice.setText(price);
    }

}
