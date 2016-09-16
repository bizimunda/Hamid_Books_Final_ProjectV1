package evoliris.com.hamid_books_final_project.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import evoliris.com.hamid_books_final_project.R;
import evoliris.com.hamid_books_final_project.tasks.AsynctaskCreateBooks;
import evoliris.com.hamid_books_final_project.tasks.AsynctaskShowBooks;

/**
 * Created by temp on 15/09/2016.
 */
public class CreateBookActivity extends ActionBarActivity implements  AsynctaskCreateBooks.GetAsyncTaskCreateBookCallback {

    private Button btnShowBooks, btnCreateNewBook;
    private EditText etTitle;
    private ListView listView;

    private AsynctaskShowBooks taskShowBooks;
    private AsynctaskCreateBooks taskCreateBooks;
    private ConnectivityManager cm;
    NetworkInfo activeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);



        etTitle = (EditText) findViewById(R.id.et_create_book_title);
        btnShowBooks = (Button) findViewById(R.id.btn_create_book_showBooks);
        btnCreateNewBook = (Button) findViewById(R.id.btn_create_book_add);
        listView= (ListView) findViewById(R.id.lv_create_book);

        btnShowBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAllBooksIntent= new Intent(CreateBookActivity.this, ItemListActivity.class);
                startActivity(showAllBooksIntent);

            }
        });

        btnCreateNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();

                cm = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    taskCreateBooks = new AsynctaskCreateBooks(CreateBookActivity.this);
                    taskCreateBooks.execute("http://192.168.1.120:1337/book/create?title=" + title);
                    Toast.makeText(CreateBookActivity.this, "Book created: " + title, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateBookActivity.this, "No data connection", Toast.LENGTH_SHORT).show();
                }
                etTitle.setText("");

            }
        });
    }


    @Override
    public void onPostGetCreateBook(String s) {

        switch (s){
            case "false":
                Toast.makeText(CreateBookActivity.this, "Error: ", Toast.LENGTH_SHORT).show();
                break;
            case "true":
                Toast.makeText(CreateBookActivity.this, "Created: "+ s,Toast.LENGTH_SHORT).show();
                break;

        }
        Log.i("Books create", s);
    }


}
