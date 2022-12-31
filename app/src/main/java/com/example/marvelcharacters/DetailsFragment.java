package com.example.marvelcharacters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marvelcharacters.AppUtils.CONSTANTS;
import com.example.marvelcharacters.Items.InfoListItem;
import com.example.marvelcharacters.Objects.CharacterInfo.Character;
import com.example.marvelcharacters.Responses.GetAllMarvelCharactersResponse;
import com.example.marvelcharacters.Responses.GetMarvelCharacterComicsResponse;
import com.facebook.drawee.view.SimpleDraweeView;

public class DetailsFragment extends Fragment implements View.OnClickListener {

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
    private LinearLayout progressbarContainer;
    private RelativeLayout characterInfoContainer;
    private RelativeLayout errorContainer;
    private TextView errorTextView;
    private Button errorRetryButton;
    private LinearLayout comicsListLinearLayout;
    private LinearLayout eventsListLinearLayout;
    private LinearLayout storiesListLinearLayout;
    private LinearLayout seriesListLinearLayout;
    private int characterId;

    private boolean detailsServiceFinished;
    private boolean comicsServiceFinished;
    private boolean storiesServiceFinished;
    private boolean seriesServiceFinished;
    private boolean eventsServiceFinished;
    private Context context;

    public DetailsFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(CONSTANTS.CHARACTER_ID_KEY)) {
            characterId = getArguments().getInt(CONSTANTS.CHARACTER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.character_details_item_layout, container, false);
        main = (MainActivity) getActivity().getApplicationContext();
        main.setDetailsFragment(this);
        initializeViews(rootView);
        handleServicesFlags(false);
        getMarvelCharacterInfo(characterId);
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
        progressbarContainer = rootView.findViewById(R.id.progressbar_Linear_container);
        characterInfoContainer = rootView.findViewById(R.id.character_info_relative_container);
        errorContainer = rootView.findViewById(R.id.error_relative_layout_container);
        errorTextView = rootView.findViewById(R.id.error_failure_text_view);
        errorRetryButton = rootView.findViewById(R.id.error_failure_retry_button);
        comicsListLinearLayout = rootView.findViewById(R.id.character_comics_expand_item_container);
        eventsListLinearLayout = rootView.findViewById(R.id.character_events_expand_item_container);
        seriesListLinearLayout = rootView.findViewById(R.id.character_series_expand_item_container);
        storiesListLinearLayout = rootView.findViewById(R.id.character_stories_expand_item_container);

        detailsContainer.setOnClickListener(this);
        comicsContainer.setOnClickListener(this);
        eventsContainer.setOnClickListener(this);
        storiesContainer.setOnClickListener(this);
        seriesContainer.setOnClickListener(this);
        errorRetryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.character_details_container:
                handleDetailsVisibility();
                break;
            case R.id.character_comics_container:
                handleComicsVisibility();
                break;
            case R.id.character_events_container:
                handleEventsVisibility();
                break;
            case R.id.character_stories_container:
                handleStoriesVisibility();
                break;
            case R.id.character_series_container:
                handleSeriesVisibility();
                break;
            case R.id.error_failure_retry_button:
                errorContainer.setVisibility(View.GONE);
                getMarvelCharacterInfo(characterId);
                break;
        }
    }

    private void getMarvelCharacterInfo(int characterId) {
        progressbarContainer.setVisibility(View.VISIBLE);
        characterInfoContainer.setVisibility(View.GONE);
        main.getMarvelCharacterDetails(characterId);
    }

    public void onGetMarvelCharacterDetailsSucceed(GetAllMarvelCharactersResponse getAllMarvelCharactersResponse) {
        setDetailsServiceFinished(true);
        if (getAllMarvelCharactersResponse.data != null) {
            Character characterItem = getAllMarvelCharactersResponse.data.results.get(0);
            Uri uri = Uri.parse(characterItem.thumbnail.path + "." + characterItem.thumbnail.extension);
            characterProfileImage.setImageURI(uri);
            characterNameTextView.setText(characterItem.name);
            characterIdTextView.setText(String.valueOf(characterId));
            if (characterItem.description != null && !characterItem.description.isEmpty()) {
                characterDescriptionTextView.setText(characterItem.description);
            } else {
                characterDescriptionTextView.setText(getString(R.string.character_no_data_found_string));
            }
            main.getMarvelCharacterComics(characterId);
            main.getMarvelCharacterEvents(characterId);
            main.getMarvelCharacterStories(characterId);
            main.getMarvelCharacterSeries(characterId);
        }
    }

    public void onGetMarvelCharacterDetailsFailed(String errorText) {
        setDetailsServiceFinished(true);
        progressbarContainer.setVisibility(View.GONE);
        characterInfoContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(errorText);
    }

    public void onGetMarvelCharacterComicsSucceed(GetMarvelCharacterComicsResponse getMarvelCharacterComicsResponse) {
        setComicsServiceFinished(true);
        if (getMarvelCharacterComicsResponse != null) {
            for (int i = 0; i < getMarvelCharacterComicsResponse.data.results.size() && i < 3; i++) {
                InfoListItem infoListItem = new InfoListItem(context, inflatePreviewView(comicsListLinearLayout), getMarvelCharacterComicsResponse.data.results.get(i));
            }
        }
    }

    public void onGetMarvelCharacterComicsFailed(String errorText) {
        setComicsServiceFinished(true);
    }

    public void onGetMarvelCharacterEventsSucceed(GetMarvelCharacterComicsResponse getMarvelCharacterComicsResponse) {
        setEventsServiceFinished(true);
        if (getMarvelCharacterComicsResponse != null) {
            for (int i = 0; i < getMarvelCharacterComicsResponse.data.results.size() && i < 3; i++) {
                InfoListItem infoListItem = new InfoListItem(context, inflatePreviewView(eventsListLinearLayout), getMarvelCharacterComicsResponse.data.results.get(i));
            }
        }
    }

    public void onGetMarvelCharacterEventsFailed(String errorText) {
        setEventsServiceFinished(true);
    }

    public void onGetMarvelCharacterStoriesSucceed(GetMarvelCharacterComicsResponse getMarvelCharacterComicsResponse) {
        setStoriesServiceFinished(true);
        if (getMarvelCharacterComicsResponse != null) {
            for (int i = 0; i < getMarvelCharacterComicsResponse.data.results.size() && i < 3; i++) {
                InfoListItem infoListItem = new InfoListItem(context, inflatePreviewView(storiesListLinearLayout), getMarvelCharacterComicsResponse.data.results.get(i));
            }
        }
    }

    public void onGetMarvelCharacterStoriesFailed(String errorText) {
        setStoriesServiceFinished(true);
    }

    public void onGetMarvelCharacterSeriesSucceed(GetMarvelCharacterComicsResponse getMarvelCharacterComicsResponse) {
        setSeriesServiceFinished(true);
        if (getMarvelCharacterComicsResponse != null) {
            for (int i = 0; i < getMarvelCharacterComicsResponse.data.results.size() && i < 3; i++) {
                InfoListItem infoListItem = new InfoListItem(context, inflatePreviewView(seriesListLinearLayout), getMarvelCharacterComicsResponse.data.results.get(i));
            }
            progressbarContainer.setVisibility(View.GONE);
            characterInfoContainer.setVisibility(View.VISIBLE);
        }
    }

    public void onGetMarvelCharacterSeriesFailed(String errorText) {
        setSeriesServiceFinished(true);
    }

    private void handleDetailsVisibility() {
        boolean detailsSelected = detailsContainer.isSelected();
        detailsContainer.setSelected(!detailsSelected);
        if (detailsContainer.isSelected()) {
            detailsImageView.setRotation(180);
            detailsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            detailsImageView.setRotation(0);
            detailsLinearLayout.setVisibility(View.GONE);
        }
    }

    private void handleComicsVisibility() {
        boolean comicsSelected = comicsContainer.isSelected();
        comicsContainer.setSelected(!comicsSelected);
        if (comicsContainer.isSelected()) {
            comicsImageView.setRotation(180);
            comicsListLinearLayout.setVisibility(View.VISIBLE);
        } else {
            comicsImageView.setRotation(0);
            comicsListLinearLayout.setVisibility(View.GONE);
        }
    }

    private void handleEventsVisibility() {
        boolean eventsSelected = eventsContainer.isSelected();
        eventsContainer.setSelected(!eventsSelected);
        if (eventsContainer.isSelected()) {
            eventsImageView.setRotation(180);
            eventsListLinearLayout.setVisibility(View.VISIBLE);
        } else {
            eventsImageView.setRotation(0);
            eventsListLinearLayout.setVisibility(View.GONE);
        }
    }

    private void handleStoriesVisibility() {
        boolean storiesSelected = storiesContainer.isSelected();
        storiesContainer.setSelected(!storiesSelected);
        if (storiesContainer.isSelected()) {
            storiesImageView.setRotation(180);
            storiesListLinearLayout.setVisibility(View.VISIBLE);
        } else {
            storiesImageView.setRotation(0);
            storiesListLinearLayout.setVisibility(View.GONE);
        }
    }

    private void handleSeriesVisibility() {
        boolean seriesSelected = seriesContainer.isSelected();
        seriesContainer.setSelected(!seriesSelected);
        if (seriesContainer.isSelected()) {
            seriesImageView.setRotation(180);
            seriesListLinearLayout.setVisibility(View.VISIBLE);
        } else {
            seriesImageView.setRotation(0);
            seriesListLinearLayout.setVisibility(View.GONE);
        }
    }

    private void handleServicesFlags(boolean isFinished) {
        setDetailsServiceFinished(isFinished);
        setComicsServiceFinished(isFinished);
        setStoriesServiceFinished(isFinished);
        setSeriesServiceFinished(isFinished);
        setEventsServiceFinished(isFinished);
    }

    private View inflatePreviewView(LinearLayout mainView) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View selectedItemView = inflater.inflate(R.layout.info_list_item_layout, mainView, false);
        mainView.addView(selectedItemView);
        return selectedItemView;
    }

    private boolean checkIfAllServicesFinished() {
        return detailsServiceFinished && comicsServiceFinished && storiesServiceFinished && seriesServiceFinished && eventsServiceFinished;
    }

    public void setDetailsServiceFinished(boolean detailsServiceFinished) {
        this.detailsServiceFinished = detailsServiceFinished;
    }

    public void setComicsServiceFinished(boolean comicsServiceFinished) {
        this.comicsServiceFinished = comicsServiceFinished;
    }

    public void setStoriesServiceFinished(boolean storiesServiceFinished) {
        this.storiesServiceFinished = storiesServiceFinished;
    }

    public void setSeriesServiceFinished(boolean seriesServiceFinished) {
        this.seriesServiceFinished = seriesServiceFinished;
    }

    public void setEventsServiceFinished(boolean eventsServiceFinished) {
        this.eventsServiceFinished = eventsServiceFinished;
    }
}
