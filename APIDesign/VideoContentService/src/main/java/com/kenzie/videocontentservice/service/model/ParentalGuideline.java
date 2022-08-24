package com.kenzie.videocontentservice.service.model;

public enum ParentalGuideline {
    TV_Y("TV_Y"),
    TV_Y7("TV_Y7"),
    TV_G("TV_G"),
    TV_PG("TV_PG"),
    TV_14("TV_14"),
    TV_MA("TV_MA");

    private final String name;

    ParentalGuideline(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
