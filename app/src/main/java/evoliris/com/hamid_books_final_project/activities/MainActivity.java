package evoliris.com.hamid_books_final_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import evoliris.com.hamid_books_final_project.R;


public class MainActivity extends ActionBarActivity {


    private Button btnJsonAddBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJsonAddBook=(Button)findViewById(R.id.btn_main_AddBook);
        btnJsonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddJsonBook= new Intent(MainActivity.this, CreateBookActivity.class);
                startActivity(intentAddJsonBook);
            }
        });
    }



}
