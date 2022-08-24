package com.kenzie.videocontentservice;

public class ShowNotFoundException extends RuntimeException {

    public ShowNotFoundException(String id) {
        super("Could not find show " + id);
    }
}
