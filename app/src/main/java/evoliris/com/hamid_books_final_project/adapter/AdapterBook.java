package evoliris.com.hamid_books_final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import evoliris.com.hamid_books_final_project.R;
import evoliris.com.hamid_books_final_project.app.LoginApplication;
import evoliris.com.hamid_books_final_project.model.Book;

/**
 * Created by temp on 15/09/2016.
 */
public class AdapterBook extends ArrayAdapter<Book> {

    ImageLoader imageLoader = LoginApplication.getInstance().getImageLoader();


    public AdapterBook(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Book model = getItem(position);


        imageLoader = LoginApplication.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) listItemView
                .findViewById(R.id.iv_listItem);
        thumbNail.setImageUrl(model.getPhoto(), imageLoader);



        TextView titleTextView = (TextView) listItemView.findViewById(R.id.tv_listItem_title);
        titleTextView.setText(String.valueOf(model.getId()));

        return listItemView;
    }
}
