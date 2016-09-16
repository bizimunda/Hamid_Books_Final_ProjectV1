package evoliris.com.hamid_books_final_project.tasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by temp on 15/09/2016.
 */
public class AsynctaskCreateBooks extends AsyncTask<String, Void, String> {


    private GetAsyncTaskCreateBookCallback callback;

    public AsynctaskCreateBooks(GetAsyncTaskCreateBookCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        if (params.length != 1) {
            throw new IllegalArgumentException("GetAsyncTask can only be executed with a string");
        }

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            return  e.getMessage();
            //return "blop";
        }



    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        callback.onPostGetCreateBook(s);
    }

    public interface GetAsyncTaskCreateBookCallback {

        void onPostGetCreateBook(String s);

    }
}
