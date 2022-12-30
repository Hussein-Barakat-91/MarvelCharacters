package com.example.marvelcharacters.AppUtils;

import com.example.marvelcharacters.Responses.GetAllMarvelCharactersResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

public class CustomMapper {

    /*public JSONObject getCRMLoginRequest(CRMLoginRequest crmLoginRequest) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = null;
        try {
            json = new JSONObject(mapper.writeValueAsString(crmLoginRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }*/

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
}
