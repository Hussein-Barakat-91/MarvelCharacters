package com.example.marvelcharacters.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.marvelcharacters.HomeActivity;
import com.example.marvelcharacters.Objects.Character;
import com.example.marvelcharacters.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class HomeCharactersListAdapter extends ArrayAdapter<Character> {

    private HomeActivity context;
    private int resource;

    public HomeCharactersListAdapter(@NonNull HomeActivity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    private static class DataHandler {
        SimpleDraweeView characterThumbnailImage;
        TextView characterNameText;
        TextView characterIdText;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataHandler handler = new DataHandler();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, parent, false);
        handler.characterThumbnailImage = (SimpleDraweeView) convertView.findViewById(R.id.character_profile_image_view);
        handler.characterNameText = convertView.findViewById(R.id.character_name_text_view);
        handler.characterIdText = convertView.findViewById(R.id.character_id_text_view);
        convertView.setTag(handler);

        Character character = getItem(position);
        if (character != null) {
            Uri uri = Uri.parse(character.thumbnail.path + "." + character.thumbnail.extension);
            handler.characterThumbnailImage.setImageURI(uri);
            handler.characterNameText.setText(character.name);
            handler.characterIdText.setText(String.valueOf(character.id));
        }
        return convertView;
    }
}
