package com.example.marvelcharacters.AppUtils;

import com.example.marvelcharacters.Responses.GetAllMarvelCharactersResponse;
import com.example.marvelcharacters.Responses.GetMarvelCharacterComicsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

public class CustomMapper {

    public GetAllMarvelCharactersResponse mapGetAllMarvelCharactersResponse(JSONObject object) {
        ObjectMapper mapper = new ObjectMapper();
        GetAllMarvelCharactersResponse getAllMarvelCharactersResponse = new GetAllMarvelCharactersResponse();
        try {
            getAllMarvelCharactersResponse = mapper.readValue(object.toString(), GetAllMarvelCharactersResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getAllMarvelCharactersResponse;

    }
    public GetMarvelCharacterComicsResponse mapGetMarvelCharacterComicsResponse(JSONObject object) {
        ObjectMapper mapper = new ObjectMapper();
        GetMarvelCharacterComicsResponse getMarvelCharacterComicsResponse = new GetMarvelCharacterComicsResponse();
        try {
            getMarvelCharacterComicsResponse = mapper.readValue(object.toString(), GetMarvelCharacterComicsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getMarvelCharacterComicsResponse;

    }
}
