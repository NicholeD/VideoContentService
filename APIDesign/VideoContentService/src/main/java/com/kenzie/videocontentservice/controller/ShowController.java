package com.kenzie.videocontentservice.controller;

import com.kenzie.videocontentservice.service.ContentService;


public class ShowController {

    private ContentService contentService;

    ShowController(ContentService contentService) {
        this.contentService = contentService;
    }

    // Create your endpoints here and add the necessary annotations
}
