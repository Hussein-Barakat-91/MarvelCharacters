package com.example.marvelcharacters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

import com.androidquery.callback.AjaxStatus;
import com.example.marvelcharacters.AppUtils.CONSTANTS;
import com.example.marvelcharacters.AppUtils.CustomMapper;
import com.example.marvelcharacters.AppUtils.CustomWebService;
import com.example.marvelcharacters.Responses.GetAllMarvelCharactersResponse;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends MultiDexApplication {

    private CustomMapper customMapper;
    private HomeActivity homeActivity;
    private CharacterDetailsActivity characterDetailsActivity;

    private CustomWebService getAllMarvelCharactersService;
    private CustomWebService getMarvelCharacterDetailsService;
    private CustomWebService getMarvelCharacterComicsService;

    @Override
    public void onCreate() {
        super.onCreate();
        customMapper = new CustomMapper();
        Fresco.initialize(this);
        initializeWebServices();
        startHomeActivity();
    }

    // initializing web services
    private void initializeWebServices() {
        getAllMarvelCharactersService = new CustomWebService(this, CONSTANTS.SERVICE_TYPE.getAllMarvelCharacters, CONSTANTS.SHORT_WEBSERVICE_TIMEOUT);
        getMarvelCharacterDetailsService = new CustomWebService(this, CONSTANTS.SERVICE_TYPE.getMarvelCharacterDetails, CONSTANTS.SHORT_WEBSERVICE_TIMEOUT);
        getMarvelCharacterComicsService = new CustomWebService(this, CONSTANTS.SERVICE_TYPE.getMarvelCharacterComics, CONSTANTS.SHORT_WEBSERVICE_TIMEOUT);
    }

    public void onCustomServiceResponse(AjaxStatus status, JSONObject object, CONSTANTS.SERVICE_TYPE service_type) {
        if (checkResponseError(status)) {
            Toast.makeText(this, "failed to retrieve data from server", Toast.LENGTH_LONG).show();
        } else {
            switch (service_type) {
                case getAllMarvelCharacters:
                    onGetAllMarvelCharactersResponse(status, object);
                    break;
                case getMarvelCharacterDetails:
                    onGetMarvelCharacterDetailsResponse(status, object);
                    break;
                case getMarvelCharacterComics:
                    onGetMarvelCharacterComicsResponse(status, object);
                    break;
            }
        }
    }

    private boolean checkResponseError(AjaxStatus status) {
        if (status.equals(AjaxStatus.AUTH_ERROR) || status.equals(AjaxStatus.TRANSFORM_ERROR) || status.equals(AjaxStatus.NETWORK_ERROR)) {
            return true;
        }
        return false;
    }

    public String getErrorByCode(AjaxStatus ajaxStatus) {
        switch (ajaxStatus.getCode()) {
            case AjaxStatus.AUTH_ERROR:
            case AjaxStatus.TRANSFORM_ERROR:
            case 400:
                return getString(R.string.transform_error);
            case AjaxStatus.NETWORK_ERROR:
                return getString(R.string.network_error);
            case 401:
            case 403:
            case 405:
            case 409:
                return ajaxStatus.getMessage();
            default:
                return getString(R.string.server_error);
        }
    }

    public void startHomeActivity() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(homeIntent);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // getter and setters for activities
    public HomeActivity getHomeActivity() {
        return homeActivity;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    public CharacterDetailsActivity getCharacterDetailsActivity() {
        return characterDetailsActivity;
    }

    public void setCharacterDetailsActivity(CharacterDetailsActivity characterDetailsActivity) {
        this.characterDetailsActivity = characterDetailsActivity;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private String grabHashKey() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String passHash = md5(ts + CONSTANTS.MARVEL_PRIVATE_KEY + CONSTANTS.API_KEY);
        return passHash;
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String grabAuthentication() {
        return  "?ts=" + System.currentTimeMillis() / 1000 + "&apikey=" + CONSTANTS.API_KEY + "&hash=" + grabHashKey();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // get marvel characters service
    public void getAllMarvelCharacters() {
        getAllMarvelCharactersService.get(CONSTANTS.MAIN_ADDRESS + grabAuthentication());
    }

    public void onGetAllMarvelCharactersResponse(AjaxStatus ajaxStatus, JSONObject object) {
        switch (ajaxStatus.getCode()) {
            case AjaxStatus.AUTH_ERROR:
            case AjaxStatus.TRANSFORM_ERROR:
            case 400:
            case AjaxStatus.NETWORK_ERROR:
                onGetAllMarvelCharactersFailure(ajaxStatus);
                return;
        }
        if (object != null) {
            onGetAllMarvelCharactersSuccess(object, ajaxStatus);
        } else {
            onGetAllMarvelCharactersFailure(ajaxStatus);
        }
    }

    public void onGetAllMarvelCharactersSuccess(JSONObject object, AjaxStatus ajaxStatus) {
        if (homeActivity != null) {
            if (object != null) {
                GetAllMarvelCharactersResponse getAllMarvelCharactersResponse = customMapper.mapGetAllMarvelCharactersResponse(object);
                homeActivity.onGetAllMarvelCharactersSucceed(getAllMarvelCharactersResponse);
            } else {
                homeActivity.onGetAllMarvelCharactersFailed(getErrorByCode(ajaxStatus));
            }
        }
    }
    public void onGetAllMarvelCharactersFailure(AjaxStatus ajaxStatus) {
        if (homeActivity != null) {
            homeActivity.onGetAllMarvelCharactersFailed(getErrorByCode(ajaxStatus));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // get marvel character details service
    public void getMarvelCharacterDetails(int id) {
        String charactersService = "&ts=" + System.currentTimeMillis() / 1000 + "&apikey=" + CONSTANTS.API_KEY + "&hash=" + grabHashKey();
        getMarvelCharacterDetailsService.get(CONSTANTS.MAIN_ADDRESS + "?id=" + id + charactersService);
    }

    public void onGetMarvelCharacterDetailsResponse(AjaxStatus ajaxStatus, JSONObject object) {
        switch (ajaxStatus.getCode()) {
            case AjaxStatus.AUTH_ERROR:
            case AjaxStatus.TRANSFORM_ERROR:
            case 400:
            case AjaxStatus.NETWORK_ERROR:
                onGetMarvelCharacterDetailsFailure(ajaxStatus);
                return;
        }
        if (object != null) {
            onGetMarvelCharacterDetailsSuccess(object, ajaxStatus);
        } else {
            onGetMarvelCharacterDetailsFailure(ajaxStatus);
        }
    }

    public void onGetMarvelCharacterDetailsSuccess(JSONObject object, AjaxStatus ajaxStatus) {
        if (characterDetailsActivity != null) {
            if (object != null) {
                GetAllMarvelCharactersResponse getAllMarvelCharactersResponse = customMapper.mapGetAllMarvelCharactersResponse(object);
                characterDetailsActivity.onGetMarvelCharacterDetailsSucceed(getAllMarvelCharactersResponse);
            } else {
                characterDetailsActivity.onGetMarvelCharacterDetailsFailed(getErrorByCode(ajaxStatus));
            }
        }
    }
    public void onGetMarvelCharacterDetailsFailure(AjaxStatus ajaxStatus) {
        if (characterDetailsActivity != null) {
            characterDetailsActivity.onGetMarvelCharacterDetailsFailed(getErrorByCode(ajaxStatus));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // get marvel character comics service
    public void getMarvelCharacterComics(int id) {
        getMarvelCharacterComicsService.get(CONSTANTS.MAIN_ADDRESS + "/" + id + "/comics" + grabAuthentication());
    }

    public void onGetMarvelCharacterComicsResponse(AjaxStatus ajaxStatus, JSONObject object) {
        switch (ajaxStatus.getCode()) {
            case AjaxStatus.AUTH_ERROR:
            case AjaxStatus.TRANSFORM_ERROR:
            case 400:
            case AjaxStatus.NETWORK_ERROR:
                onGetMarvelCharacterComicsFailure(ajaxStatus);
                return;
        }
        if (object != null) {
            onGetMarvelCharacterComicsSuccess(object, ajaxStatus);
        } else {
            onGetMarvelCharacterComicsFailure(ajaxStatus);
        }
    }

    public void onGetMarvelCharacterComicsSuccess(JSONObject object, AjaxStatus ajaxStatus) {
        if (characterDetailsActivity != null) {
            /*if (object != null) {
                GetAllMarvelCharactersResponse getAllMarvelCharactersResponse = customMapper.mapGetAllMarvelCharactersResponse(object);
                characterDetailsActivity.onGetMarvelCharacterDetailsSucceed(getAllMarvelCharactersResponse);
            } else {
                characterDetailsActivity.onGetMarvelCharacterDetailsFailed(getErrorByCode(ajaxStatus));
            }*/
        }
    }
    public void onGetMarvelCharacterComicsFailure(AjaxStatus ajaxStatus) {
        if (characterDetailsActivity != null) {
            //characterDetailsActivity.onGetMarvelCharacterDetailsFailed(getErrorByCode(ajaxStatus));
        }
    }

}