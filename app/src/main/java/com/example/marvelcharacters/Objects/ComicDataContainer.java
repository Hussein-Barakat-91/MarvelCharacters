package com.example.marvelcharacters.Objects;

import com.example.marvelcharacters.Objects.Comics.ComicItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicDataContainer {
    public List<ComicItem> results;
}
