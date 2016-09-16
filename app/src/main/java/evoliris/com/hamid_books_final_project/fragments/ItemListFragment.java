package evoliris.com.hamid_books_final_project.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import evoliris.com.hamid_books_final_project.AppController;
import evoliris.com.hamid_books_final_project.adapter.AdapterBook;
import evoliris.com.hamid_books_final_project.model.Book;
import evoliris.com.hamid_books_final_project.tasks.AsynctaskShowBooks;

/**
 * Created by temp on 15/09/2016.
 */
public class ItemListFragment extends ListFragment implements AsynctaskShowBooks.GetAsyncTaskCallback{


    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private List<Book> bookList = new ArrayList<>();
    private String url= "http://192.168.1.120:1337/book";

    private AsynctaskShowBooks taskShowBooks;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;


    public interface Callbacks {
        public void onItemSelected(long id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
        }
    };

    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cm = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            taskShowBooks = new AsynctaskShowBooks(ItemListFragment.this);
            taskShowBooks.execute("http://192.168.1.120:1337/book/");
        } else {
            Toast.makeText(this.getContext(), "No data connection", Toast.LENGTH_SHORT).show();
        }

        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("ItemListFragment", response.toString());


                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Book book = new Book();
                                book.setTitle(obj.getString("title"));
                                book.setImage(obj.getString("photo"));

                                // adding book to movies array
                                bookList.add(book);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ItemListFragment", "Error: " + error.getMessage());


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }

    @Override
    public void onPostGet(String s) {
        Gson gson = new Gson();
        JSONArray jsonArray;
        ArrayList<Book> books = new ArrayList<>();

        try {
            jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                books.add(gson.fromJson(jsonArray.get(i).toString(), Book.class));
                Log.i("BOOK", books.get(i).getTitle());
                AdapterBook adapterBook= new AdapterBook(this.getContext(), books);
                setListAdapter(adapterBook);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Book selectedBook = ((AdapterBook)listView.getAdapter()).getItem(position);
        mCallbacks.onItemSelected(selectedBook.getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
    }
}
