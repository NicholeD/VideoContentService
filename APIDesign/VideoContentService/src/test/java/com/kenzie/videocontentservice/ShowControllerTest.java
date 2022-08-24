package com.kenzie.videocontentservice;

import com.kenzie.videocontentservice.controller.ShowController;
import com.kenzie.videocontentservice.service.ContentService;
import com.kenzie.videocontentservice.service.model.EpisodeInfo;
import com.kenzie.videocontentservice.service.model.ParentalGuideline;
import com.kenzie.videocontentservice.service.model.ShowInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShowController.class)
@ExtendWith(SpringExtension.class)
public class ShowControllerTest {

    @MockBean
    private ContentService contentService;

    @BeforeEach
    public void init() {
    }

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<EpisodeInfo> episodeInfoCaptor;

    @Captor
    ArgumentCaptor<ShowInfo> showInfoCaptor;

    @Test
    void getShow_success() throws Exception {
        // GIVEN
        ShowInfo showInfo = new ShowInfo();
        showInfo.setShowId("fdsa879fd8s99");
        showInfo.setTitle("The Fake Show!");
        showInfo.setParentalGuideline(ParentalGuideline.TV_G);
        showInfo.setEpisodeLength(60);
        showInfo.setGenre("Fake");
        showInfo.setAverageRating(5.0);
        showInfo.setNumberOfRatings(10);
        showInfo.setNumberOfSeasons(2);
        showInfo.setBillingId("259012592");
        showInfo.setDatabaseId("542vdjs3289gja9332");

        when(contentService.getShow(showInfo.getShowId())).thenReturn(showInfo);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/show/" + showInfo.getShowId()))
        // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.showId").value(showInfo.getShowId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(showInfo.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentalGuideline").value(showInfo.getParentalGuideline().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.episodeLength").value(showInfo.getEpisodeLength()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(showInfo.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.averageRating").value(showInfo.getAverageRating()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfRatings").value(showInfo.getNumberOfRatings()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfSeasons").value(showInfo.getNumberOfSeasons()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.databaseId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.billingId").doesNotExist());

        verify(contentService).getShow(showInfo.getShowId());
    }

    @Test
    void getAllShows_success() throws Exception {
        // GIVEN
        List<ShowInfo> shows = new ArrayList<>();
        ShowInfo showInfo = new ShowInfo();
        showInfo.setShowId("fdsa879fd8s99");
        showInfo.setTitle("The Fake Show!");
        showInfo.setParentalGuideline(ParentalGuideline.TV_G);
        showInfo.setEpisodeLength(60);
        showInfo.setGenre("Fake");
        showInfo.setAverageRating(5.0);
        showInfo.setNumberOfRatings(10);
        showInfo.setNumberOfSeasons(2);
        showInfo.setBillingId("259012592");
        showInfo.setDatabaseId("542vdjs3289gja9332");
        shows.add(showInfo);

        ShowInfo showInfo2 = new ShowInfo();
        showInfo2.setShowId("asdfsjisff");
        showInfo2.setTitle("The Other Fake Show!");
        showInfo2.setParentalGuideline(ParentalGuideline.TV_14);
        showInfo2.setEpisodeLength(30);
        showInfo2.setGenre("Other");
        showInfo2.setAverageRating(6.0);
        showInfo2.setNumberOfRatings(20);
        showInfo2.setNumberOfSeasons(1);
        showInfo2.setBillingId("8521259215");
        showInfo2.setDatabaseId("f8da3fjlafefla");
        shows.add(showInfo2);

        when(contentService.getAllShows()).thenReturn(shows);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/show/all"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].showId").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].parentalGuideline").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].episodeLength").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].averageRating").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfRatings").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfSeasons").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].databaseId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].billingId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].showId").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].parentalGuideline").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].episodeLength").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].averageRating").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfRatings").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfSeasons").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].databaseId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].billingId").doesNotExist());

        verify(contentService).getAllShows();
    }

    @Test
    void postShow_success() throws Exception {
        // GIVEN

        String title = "The Fake Show!";
        String parentalGuideline = "TV_PG";
        Integer episodeLength = 60;
        String genre = "Fake";

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/show")
                        .contentType("application/json")
                        .content("{" +
                                    "\"title\": \"" + title + "\", " +
                                    "\"parentalGuideline\": \"" + parentalGuideline + "\", " +
                                    "\"episodeLength\": " + episodeLength + ", " +
                                    "\"genre\":\"" + genre + "\"" +
                                "}"))
                // THEN
                .andExpect(status().is2xxSuccessful());

        verify(contentService).createShow(showInfoCaptor.capture());
        ShowInfo capturedShowInfo = showInfoCaptor.getValue();
        assertEquals(title, capturedShowInfo.getTitle());
        assertEquals(parentalGuideline, capturedShowInfo.getParentalGuideline().toString());
        assertEquals(episodeLength, capturedShowInfo.getEpisodeLength());
        assertEquals(genre, capturedShowInfo.getGenre());
    }

    @Test
    void getEpisode_success() throws Exception {
        // GIVEN
        EpisodeInfo episodeInfo = new EpisodeInfo();
        episodeInfo.setShowId("fdsa879fd8s99");
        episodeInfo.setTitle("Fake Episode");
        episodeInfo.setSeasonNumber(1);
        episodeInfo.setEpisodeNumber(2);
        episodeInfo.setAired(new Date());
        episodeInfo.setAverageRating(3.5);
        episodeInfo.setNumberOfRatings(20);
        episodeInfo.setDescription("Exciting drama happens in this episode of The Fake Show!");
        episodeInfo.setDatabaseId("jfdsa83jgfes38552891");

        when(contentService.getEpisode(episodeInfo.getShowId(), episodeInfo.getSeasonNumber().toString(), episodeInfo.getEpisodeNumber().toString())).thenReturn(episodeInfo);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/show/" + episodeInfo.getShowId() +
                        "/season/" + episodeInfo.getSeasonNumber() +
                        "/episode/" + episodeInfo.getEpisodeNumber()))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.showId").value(episodeInfo.getShowId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seasonNumber").value(episodeInfo.getSeasonNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.episodeNumber").value(episodeInfo.getEpisodeNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(episodeInfo.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.averageRating").value(episodeInfo.getAverageRating()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfRatings").value(episodeInfo.getNumberOfRatings()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aired").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(episodeInfo.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.databaseId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.billingId").doesNotExist());
        verify(contentService).getEpisode(episodeInfo.getShowId(), episodeInfo.getSeasonNumber().toString(), episodeInfo.getEpisodeNumber().toString());
    }

    @Test
    void getAllEpisodes_success() throws Exception {
        // GIVEN
        String showId = "fjdkslfads";
        Integer seasonNumber = 1;
        List<EpisodeInfo> episodes = new ArrayList<>();
        EpisodeInfo episodeInfo = new EpisodeInfo();
        episodeInfo.setShowId(showId);
        episodeInfo.setTitle("Fake Episode 1");
        episodeInfo.setSeasonNumber(seasonNumber);
        episodeInfo.setEpisodeNumber(1);
        episodeInfo.setAired(new Date());
        episodeInfo.setAverageRating(2.5);
        episodeInfo.setNumberOfRatings(25);
        episodeInfo.setDescription("Exciting drama happens in this episode of The Fake Show!");
        episodeInfo.setDatabaseId("jfdsa83jgfes38552891");
        episodes.add(episodeInfo);

        EpisodeInfo episodeInfo2 = new EpisodeInfo();
        episodeInfo2.setShowId(showId);
        episodeInfo2.setTitle("Fake Episode 2");
        episodeInfo2.setSeasonNumber(seasonNumber);
        episodeInfo2.setEpisodeNumber(2);
        episodeInfo2.setAired(new Date());
        episodeInfo2.setAverageRating(3.5);
        episodeInfo2.setNumberOfRatings(20);
        episodeInfo2.setDescription("Exciting drama happens in this next episode of The Fake Show!");
        episodeInfo2.setDatabaseId("fd8s9afdsafojd");
        episodes.add(episodeInfo2);

        when(contentService.getAllEpisodesForShow(showId, seasonNumber)).thenReturn(episodes);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(
                        "/show/" + showId +
                                "/season/" + seasonNumber +
                                "/episode/all"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].showId").value(showId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seasonNumber").value(seasonNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].episodeNumber").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].averageRating").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfRatings").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].aired").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].databaseId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].billingId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].showId").value(showId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seasonNumber").value(seasonNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].episodeNumber").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].averageRating").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfRatings").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].aired").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].databaseId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].billingId").doesNotExist());

        verify(contentService).getAllEpisodesForShow(showId, seasonNumber);
    }

    @Test
    void postEpisode_success() throws Exception {
        // GIVEN
        String showId = "5829gjra39gueag397";
        Integer seasonNumber = 1;
        Integer episodeNumber = 2;
        String title = "Fake Episode!";
        String description = "This is a fake episode";

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/show/" + showId +
                "/season/" + seasonNumber + "/episode/")
                        .contentType("application/json")
                        .content("{" +
                                    "\"showId\": \"" + showId + "\", " +
                                    "\"seasonNumber\": " + seasonNumber + ", " +
                                    "\"episodeNumber\": " + episodeNumber + ", " +
                                    "\"title\":\"" + title + "\", " +
                                    "\"description\":\"" + description + "\"" +
                                "}"))
                // THEN
                .andExpect(status().is2xxSuccessful());

        verify(contentService).addEpisode(episodeInfoCaptor.capture());
        EpisodeInfo capturedEpisodeInfo = episodeInfoCaptor.getValue();
        assertEquals(showId, capturedEpisodeInfo.getShowId());
        assertEquals(seasonNumber, capturedEpisodeInfo.getSeasonNumber());
        assertEquals(episodeNumber, capturedEpisodeInfo.getEpisodeNumber());
        assertEquals(title, capturedEpisodeInfo.getTitle());
        assertEquals(description, capturedEpisodeInfo.getDescription());
    }

}
