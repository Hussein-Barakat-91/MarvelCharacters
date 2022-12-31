package com.example.marvelcharacters.AppUtils;

public final class CONSTANTS {
    public final static int SHORT_WEBSERVICE_TIMEOUT = 1000 * 30;
    public static final String MAIN_ADDRESS ="https://gateway.marvel.com:443/v1/public/characters";
    public static final String API_KEY = "6bf7ac3e17458ab16d07590456656de7";
    public static final String MARVEL_PRIVATE_KEY = "531322ac619dde10225bf3a9239dc22ae8d67136";

    public static final String CHARACTER_ID_KEY = "CHARACTER_ID_KEY";

    public enum SERVICE_TYPE {
        getAllMarvelCharacters, getMarvelCharacterDetails, getMarvelCharacterComics, getMarvelCharacterEvents, getMarvelCharacterStories, getMarvelCharacterSeries
    }
}
