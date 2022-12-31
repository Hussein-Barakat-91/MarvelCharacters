package com.example.marvelcharacters;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marvelcharacters.Objects.Character;
import com.example.marvelcharacters.Responses.GetAllMarvelCharactersResponse;
import com.facebook.drawee.view.SimpleDraweeView;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    public static final String CHARACTER_ID = "CHARACTER_ID";

    private MainActivity main;
    private SimpleDraweeView characterProfileImage;
    private TextView characterNameTextView;

    private RelativeLayout detailsContainer;
    private RelativeLayout comicsContainer;
    private RelativeLayout eventsContainer;
    private RelativeLayout storiesContainer;
    private RelativeLayout seriesContainer;

    private ImageView detailsImageView;
    private ImageView comicsImageView;
    private ImageView eventsImageView;
    private ImageView storiesImageView;
    private ImageView seriesImageView;

    private LinearLayout detailsLinearLayout;
    private TextView characterIdTextView;
    private TextView characterDescriptionTextView;
    private ScrollView characterScrollView;
    private int characterId;

    public DetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.character_details_item_layout, container, false);
        // Show the dummy content as text in a TextView.
        initializeViews(rootView);
        return rootView;
    }

    private void initializeViews(View rootView) {
        characterProfileImage = (SimpleDraweeView) rootView.findViewById(R.id.character_profile_image_view);
        characterNameTextView = rootView.findViewById(R.id.character_name_text_view);
        detailsContainer = rootView.findViewById(R.id.character_details_container);
        comicsContainer = rootView.findViewById(R.id.character_comics_container);
        eventsContainer = rootView.findViewById(R.id.character_events_container);
        storiesContainer = rootView.findViewById(R.id.character_stories_container);
        seriesContainer = rootView.findViewById(R.id.character_series_container);
        detailsLinearLayout = rootView.findViewById(R.id.character_details_linear_layout);
        characterIdTextView = rootView.findViewById(R.id.character_id_text_view);
        characterDescriptionTextView = rootView.findViewById(R.id.character_description_text_view);
        detailsImageView = rootView.findViewById(R.id.character_info_expand_image_view);
        comicsImageView = rootView.findViewById(R.id.character_comics_expand_image_view);
        eventsImageView = rootView.findViewById(R.id.character_events_expand_image_view);
        storiesImageView = rootView.findViewById(R.id.character_stories_expand_image_view);
        seriesImageView = rootView.findViewById(R.id.character_series_expand_image_view);

        detailsContainer.setOnClickListener(this);
        comicsContainer.setOnClickListener(this);
        eventsContainer.setOnClickListener(this);
        storiesContainer.setOnClickListener(this);
        seriesContainer.setOnClickListener(this);
    }

    private void getMarvelCharacterDetails(int characterId) {
        main.getMarvelCharacterDetails(characterId);
    }

    public void onGetMarvelCharacterDetailsSucceed(GetAllMarvelCharactersResponse getAllMarvelCharactersResponse) {
        if (getAllMarvelCharactersResponse.data != null) {
            characterScrollView.setVisibility(View.VISIBLE);
            Character characterItem = getAllMarvelCharactersResponse.data.results.get(0);
            Uri uri = Uri.parse(characterItem.thumbnail.path + "." + characterItem.thumbnail.extension);
            characterProfileImage.setImageURI(uri);
            characterNameTextView.setText(characterItem.name);
            characterIdTextView.setText(String.valueOf(characterId));
            if(characterItem.description != null && !characterItem.description.isEmpty()) {
                characterDescriptionTextView.setText(characterItem.description);
            } else {
                characterDescriptionTextView.setText(getString(R.string.character_no_data_found_string));
            }
            main.getMarvelCharacterComics(characterId);
        }
    }

    public void onGetMarvelCharacterDetailsFailed(String errorText) {
        characterScrollView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.character_details_container:
                handleDetailsVisibility();
                break;
            case R.id.error_failure_retry_button:
                getMarvelCharacterDetails(characterId);
                break;
        }
    }

    private void handleDetailsVisibility() {
        boolean detailsSelected = detailsContainer.isSelected();
        detailsContainer.setSelected(!detailsSelected);
        if(detailsContainer.isSelected()) {
            detailsImageView.setRotation(180);
            detailsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            detailsImageView.setRotation(0);
            detailsLinearLayout.setVisibility(View.GONE);
        }
    }
}
