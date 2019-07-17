package com.arkdev.bookinfo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.arkdev.bookinfo.Adapter.BookListAdapter;
import com.arkdev.bookinfo.Model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BookListAdapter bookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new BookApiRetriever().execute("http://jsonstub.com/books");
    }


    class BookApiRetriever extends AsyncTask <String, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            List<Book> books = new ArrayList<>();

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("JsonStub-User-Key", "a5a3a085-c9bd-462a-83c5-77620f963745");
                urlConnection.setRequestProperty("JsonStub-Project-Key", " 612f90bf-ac5d-4ad7-9a41-f59460cd8d1c");

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String responseString = readStream(urlConnection.getInputStream());
                    books = parseBookData(responseString);
                }else{
                    Log.v("BookApiRetriever", "Response code:"+ responseCode);
                    Log.v("BookApiRetriever", "Response message:"+ responseMessage);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }
            return books;

        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            bookListAdapter = new BookListAdapter(books);
            recyclerView.setAdapter(bookListAdapter);
        }
    }

    private String readStream(InputStream in){
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();

        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                response.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return response.toString();
    }

    private List<Book> parseBookData(String jString){
        List<Book> bookList = new ArrayList<Book>();
        try {
            JSONObject jObj = new JSONObject(jString);
            JSONArray booksList = jObj.getJSONArray("books");
            for(int i = 0; i < booksList.length(); i++){
                String name = booksList.getJSONObject(i).getString("name");
                String price = booksList.getJSONObject(i).getString("price");

                Book book = new Book(name, price);
                bookList.add(book);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bookList;
    }
}
