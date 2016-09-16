package evoliris.com.hamid_books_final_project.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import evoliris.com.hamid_books_final_project.R;
import evoliris.com.hamid_books_final_project.fragments.ItemDetailFragment;

/**
 * Created by temp on 15/09/2016.
 */
public class ItemDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putLong(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(ItemDetailFragment.ARG_ITEM_ID, 0));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }


}
