package com.kenzie.videocontentservice.service;

import com.kenzie.videocontentservice.EpisodeNotFoundException;
import com.kenzie.videocontentservice.ShowNotFoundException;
import com.kenzie.videocontentservice.repositories.ContentRepository;
import com.kenzie.videocontentservice.service.model.EpisodeInfo;
import com.kenzie.videocontentservice.service.model.ShowInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    private ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public EpisodeInfo getEpisode(String showId, String seasonNumber, String episodeNumber) {
        try {
            return contentRepository.getEpisode(showId, Integer.parseInt(seasonNumber), Integer.parseInt(episodeNumber));
        } catch (Exception e) {
            throw new EpisodeNotFoundException(showId);
        }
    }

    public ShowInfo getShow(String showId) {
        if (showId.length() > 0) {
            return contentRepository.getShowById(showId);
        } else {
            throw new ShowNotFoundException(showId);
        }
    }

    public List<ShowInfo> getAllShows() {
        return contentRepository.getShows();
    }

    public void createShow(ShowInfo showInfo) {
        contentRepository.createShow(showInfo);
    }

    public void addEpisode(EpisodeInfo episodeInfo) {
        contentRepository.addEpisode(episodeInfo);
    }

    public List<EpisodeInfo> getAllEpisodesForShow(String showId, Integer seasonNumber) {
        return contentRepository.getEpisodes(showId, seasonNumber);
    }
}
