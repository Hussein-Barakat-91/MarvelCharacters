package com.example.marvelcharacters;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marvelcharacters.Adapters.HomeCharactersListAdapter;
import com.example.marvelcharacters.AppUtils.CONSTANTS;
import com.example.marvelcharacters.Objects.CharacterInfo.Character;
import com.example.marvelcharacters.Responses.GetAllMarvelCharactersResponse;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView charactersListView;
    private MainActivity main;

    private RelativeLayout errorRelativeContainer;
    private TextView errorTextView;
    private Button retryButton;
    private LinearLayout copyrightContainer;
    private TextView copyrightTextView;
    private LinearLayout characterListContainer;
    private HomeCharactersListAdapter charactersListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        main = (MainActivity) getApplicationContext();
        main.setHomeActivity(this);
        initializeViews();
        getAllMarvelCharacters();
    }

    private void initializeViews() {
        charactersListView = findViewById(R.id.characters_list_view);
        errorRelativeContainer = findViewById(R.id.error_relative_layout_container);
        errorTextView = findViewById(R.id.error_failure_text_view);
        copyrightContainer = findViewById(R.id.marvel_copyright_container);
        copyrightTextView = findViewById(R.id.marvel_copyright_text_view);
        characterListContainer = findViewById(R.id.character_list_container);
        retryButton = findViewById(R.id.error_failure_retry_button);
        retryButton.setOnClickListener(this);
    }

    private void getAllMarvelCharacters() {
        showLoader();
        main.getAllMarvelCharacters();
    }

    public void onGetAllMarvelCharactersSucceed(GetAllMarvelCharactersResponse getAllMarvelCharactersResponse) {
        if (getAllMarvelCharactersResponse.data != null) {
            copyrightContainer.setVisibility(View.VISIBLE);
            charactersListView.setVisibility(View.VISIBLE);
            characterListContainer.setVisibility(View.VISIBLE);
            errorRelativeContainer.setVisibility(View.GONE);
            charactersListAdapter = new HomeCharactersListAdapter(this, R.layout.home_character_item_list_layout);
            charactersListAdapter.addAll(getAllMarvelCharactersResponse.data.results);
            charactersListView.setAdapter(charactersListAdapter);
            charactersListView.setOnItemClickListener(this);
            copyrightTextView.setText(getAllMarvelCharactersResponse.attributionText);
        }
        hideLoader();
    }

    public void onGetAllMarvelCharactersFailed(String errorText) {
        copyrightContainer.setVisibility(View.GONE);
        charactersListView.setVisibility(View.GONE);
        characterListContainer.setVisibility(View.GONE);
        errorRelativeContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(errorText);
        hideLoader();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_failure_retry_button:
                getAllMarvelCharacters();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearFrescoImageCache();
        main.setHomeActivity(null);
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

    private void clearFrescoImageCache() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.characters_list_view:
                Character character = charactersListAdapter.getItem(position);
                boolean isTablet = getResources().getBoolean(R.bool.isTablet);
                if(isTablet) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(CONSTANTS.CHARACTER_ID_KEY, character.id);
                    DetailsFragment fragment = new DetailsFragment(this);
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, fragment)
                            .commit();
                } else {
                    launchCharacterDetailsActivity(character.id);
                }
                break;
        }
    }

    private void launchCharacterDetailsActivity(int id) {
        Intent characterDetailsIntent = new Intent(this, CharacterDetailsActivity.class);
        characterDetailsIntent.putExtra(CONSTANTS.CHARACTER_ID_KEY, id);
        startActivity(characterDetailsIntent);
    }

}
