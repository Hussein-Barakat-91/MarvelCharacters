package com.example.marvelcharacters;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marvelcharacters.Adapters.HomeCharactersListAdapter;
import com.example.marvelcharacters.AppUtils.CONSTANTS;
import com.example.marvelcharacters.Objects.Character;
import com.example.marvelcharacters.Responses.GetAllMarvelCharactersResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class CharacterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivity main;

    private ImageView backImageView;
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
    private RelativeLayout errorRelativeContainer;
    private TextView errorTextView;
    private Button retryButton;
    private ScrollView characterScrollView;
    private int characterId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_details_layout);
        main = (MainActivity) getApplicationContext();
        main.setCharacterDetailsActivity(this);
        Intent intentOptions = getIntent();
        if (intentOptions.hasExtra(CONSTANTS.CHARACTER_ID_KEY)) {
            characterId = intentOptions.getIntExtra(CONSTANTS.CHARACTER_ID_KEY, -1);
        }
        initializeViews();
        getMarvelCharacterDetails(characterId);
    }

    private void initializeViews() {
        backImageView = findViewById(R.id.back_image_view);
        characterProfileImage = findViewById(R.id.character_profile_image_view);
        characterNameTextView = findViewById(R.id.character_name_text_view);
        detailsContainer = findViewById(R.id.character_details_container);
        comicsContainer = findViewById(R.id.character_comics_container);
        eventsContainer = findViewById(R.id.character_events_container);
        storiesContainer = findViewById(R.id.character_stories_container);
        seriesContainer = findViewById(R.id.character_series_container);
        detailsLinearLayout = findViewById(R.id.character_details_linear_layout);
        characterIdTextView = findViewById(R.id.character_id_text_view);
        characterDescriptionTextView = findViewById(R.id.character_description_text_view);
        detailsImageView = findViewById(R.id.character_info_expand_image_view);
        comicsImageView = findViewById(R.id.character_comics_expand_image_view);
        eventsImageView = findViewById(R.id.character_events_expand_image_view);
        storiesImageView = findViewById(R.id.character_stories_expand_image_view);
        seriesImageView = findViewById(R.id.character_series_expand_image_view);
        characterScrollView = findViewById(R.id.character_scroll_view);
        errorRelativeContainer = findViewById(R.id.error_relative_layout_container);
        errorTextView = findViewById(R.id.error_failure_text_view);
        retryButton = findViewById(R.id.error_failure_retry_button);

        backImageView.setOnClickListener(this);
        detailsContainer.setOnClickListener(this);
        comicsContainer.setOnClickListener(this);
        eventsContainer.setOnClickListener(this);
        storiesContainer.setOnClickListener(this);
        seriesContainer.setOnClickListener(this);
        retryButton.setOnClickListener(this);
    }

    private void getMarvelCharacterDetails(int characterId) {
        showLoader();
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
        hideLoader();
    }

    public void onGetMarvelCharacterDetailsFailed(String errorText) {
        characterScrollView.setVisibility(View.GONE);
        errorRelativeContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(errorText);
        hideLoader();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image_view:
                onBackPressed();
                break;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        main.setCharacterDetailsActivity(null);
        finish();
    }

    private Dialog loaderDialog;

    private void showLoader() {
        if (loaderDialog == null || !loaderDialog.isShowing()) {
            loaderDialog = new Dialog(this);
            loaderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loaderDialog.setContentView(R.layout.loader_layout);
            loaderDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.loader_dialog_background);
            loaderDialog.getWindow().setDimAmount(0.0f);
            loaderDialog.setCanceledOnTouchOutside(false);
            loaderDialog.setCancelable(false);
            loaderDialog.show();
        }

    }

    private void hideLoader() {
        if (loaderDialog != null) {
            loaderDialog.dismiss();
        }
    }
}
