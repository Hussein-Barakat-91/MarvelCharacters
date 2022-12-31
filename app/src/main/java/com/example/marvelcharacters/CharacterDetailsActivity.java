package com.example.marvelcharacters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marvelcharacters.AppUtils.CONSTANTS;

public class CharacterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivity main;
    private int characterId;
    private ImageView backImageView;

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
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(CONSTANTS.CHARACTER_ID_KEY, characterId);
            DetailsFragment fragment = new DetailsFragment(this);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();
        }
    }
    
    private void initializeViews() {
        backImageView = findViewById(R.id.back_image_view);
        backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back_image_view:
                onBackPressed();
                break;
        }
    }
}
