package com.kenzie.videocontentservice;

public class EpisodeNotFoundException extends RuntimeException {

    public EpisodeNotFoundException(String id) {
        super("Could not find episode " + id);
    }
}