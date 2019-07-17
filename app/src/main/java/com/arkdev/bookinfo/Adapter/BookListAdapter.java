package com.arkdev.bookinfo.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arkdev.bookinfo.ItemView.BookItemView;
import com.arkdev.bookinfo.Model.Book;
import com.arkdev.bookinfo.R;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookItemView>{

    List<Book> booksList = new ArrayList<>();

    public BookListAdapter(List<Book> booksList) {
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BookItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_item, viewGroup, false);
        return new BookItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemView bookItemView, int i) {
        String bookName = booksList.get(i).getBookName();
        String bookPrice = booksList.get(i).getBookPrice();
        bookItemView.bindView(bookName, bookPrice);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}
