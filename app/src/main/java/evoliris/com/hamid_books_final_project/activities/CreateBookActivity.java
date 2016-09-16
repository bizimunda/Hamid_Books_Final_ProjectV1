package evoliris.com.hamid_books_final_project.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import evoliris.com.hamid_books_final_project.R;
import evoliris.com.hamid_books_final_project.tasks.AsynctaskCreateBooks;

/**
 * Created by temp on 15/09/2016.
 */
public class CreateBookActivity extends ActionBarActivity implements AsynctaskCreateBooks.GetAsyncTaskCreateBookCallback, View.OnClickListener {


    private Button btnShowBooks, buttonUpload, buttonChoose;
    private EditText editTextName;
    private ImageView imageView;


    private AsynctaskCreateBooks taskCreateBooks;
    private ConnectivityManager cm;
    NetworkInfo activeNetwork;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL_IMAGE = "http://192.168.1.120/volley/upload.php";
    private String UPLOAD_URL_TITLE = "http://192.168.1.120:1337/book/create?";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    Uri filePath;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);


        editTextName = (EditText) findViewById(R.id.et_createBook_title);
        btnShowBooks = (Button) findViewById(R.id.btn_create_book_showBooks);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.iv_createBook);
        buttonChoose= (Button) findViewById(R.id.buttonChoose);



        btnShowBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAllBooksIntent = new Intent(CreateBookActivity.this, ItemListActivity.class);
                startActivity(showAllBooksIntent);

            }
        });

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }

        if (v == buttonUpload) {
            //uploadTitle();
            uploadImage();

        }
    }

    //region Description
/* buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextName.getText().toString();

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
                editTextName.setText("");

            }
        });
    }*/
    //endregion


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.i("CreateBook", filePath.toString());
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //Showing the progress dialog

        title = editTextName.getText().toString();
        String imageName=String.valueOf((imageView.getTag()));

        Log.i("CreateBook", imageName);

        String ipAddress="http://192.168.1.120/volley/upload.php";
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL_IMAGE,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Log.e("Image Added", s);
                        Toast.makeText(CreateBookActivity.this, s, Toast.LENGTH_LONG).show();
                        editTextName.setText("");
                        imageView.setImageResource(android.R.color.transparent);
                        uploadTitle();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.e("CreateBook", volleyError.toString());

                        //Showing toast
                        Toast.makeText(CreateBookActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = editTextName.getText().toString().trim();

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }




    private void uploadTitle() {



        cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            taskCreateBooks = new AsynctaskCreateBooks(CreateBookActivity.this);
            taskCreateBooks.execute(UPLOAD_URL_TITLE+ "title=" + title);
            //Toast.makeText(CreateBookActivity.this, "Book created: " + title, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CreateBookActivity.this, "No data connection", Toast.LENGTH_SHORT).show();
        }
        editTextName.setText("");
    }

    @Override
    public void onPostGetCreateBook(String s) {

        switch (s) {
            case "false":
                Toast.makeText(CreateBookActivity.this, "Error: "+ s, Toast.LENGTH_SHORT).show();
                break;
            case "true":

                Toast.makeText(CreateBookActivity.this, "Created: " + s, Toast.LENGTH_SHORT).show();
                break;

        }
        Log.i("Books create", s);
    }


}
