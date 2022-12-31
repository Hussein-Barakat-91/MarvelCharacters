package com.example.marvelcharacters.Items;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.marvelcharacters.Objects.Comics.ComicItem;
import com.example.marvelcharacters.R;

public class InfoListItem {

    private View mainView;
    private TextView idTextView;
    private TextView descriptionTextView;
    private TextView titleTextView;
    private Context context;

    public InfoListItem(Context context, View mainView, ComicItem comicItem) {
        this.mainView = mainView;
        this.context = context;
        initializeViews();
        populateComicListItem(comicItem);
    }

    private void initializeViews() {
        idTextView = mainView.findViewById(R.id.id_text_view);
        titleTextView = mainView.findViewById(R.id.title_text_view);
        descriptionTextView = mainView.findViewById(R.id.description_text_view);
    }

    private void populateComicListItem(ComicItem comicItem) {
        idTextView.setText(String.valueOf(comicItem.id));
        titleTextView.setText(comicItem.title);
        if(comicItem.description != null && !comicItem.description.isEmpty()) {
            descriptionTextView.setText(comicItem.description);
        } else {
            descriptionTextView.setText(context.getResources().getString(R.string.character_no_data_found_string));
        }
    }
}
