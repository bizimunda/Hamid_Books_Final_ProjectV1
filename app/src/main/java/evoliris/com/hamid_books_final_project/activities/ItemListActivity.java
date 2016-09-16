package evoliris.com.hamid_books_final_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import evoliris.com.hamid_books_final_project.R;
import evoliris.com.hamid_books_final_project.fragments.ItemDetailFragment;
import evoliris.com.hamid_books_final_project.fragments.ItemListFragment;

/**
 * Created by temp on 15/09/2016.
 */
public class ItemListActivity extends FragmentActivity implements ItemListFragment.Callbacks {




    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }


    }

    @Override
    public void onItemSelected(long id) {
        if (mTwoPane) {

            Bundle arguments = new Bundle();
            arguments.putLong(ItemDetailFragment.ARG_ITEM_ID, id);

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
