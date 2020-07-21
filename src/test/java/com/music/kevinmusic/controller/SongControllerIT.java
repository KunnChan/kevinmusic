package com.music.kevinmusic.controller;
/*
 * Created by kunnchan on 18/07/2020
 * package :  com.music.kevinmusic.controller
 */

import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.repository.SongRepository;
import com.music.kevinmusic.request.PageInfo;
import com.music.kevinmusic.request.SongSingleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SongControllerIT extends BaseIT {

    @Autowired
    private SongRepository songRepository;

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests {

        public Song songToDelete() {
            Song song = new Song();
            song.setArtist("test artist");
            song.setGenre("test genre");
            song.setInformation("test info");
            song.setLanguage("test language");
            song.setTitle("test title");
            return songRepository.save(song);
        }

        @Test
        void deleteBeerHttpBasic() throws Exception{
            final String accessToken = getAccessToken("admin", "admin");
            mockMvc.perform(delete("/song/" + songToDelete().getId())
                    .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().is2xxSuccessful());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.music.kevinmusic.controller.SongControllerIT#getStreamNotAdmin")
        void deleteBeerHttpBasicNotAuth(String user, String pwd) throws Exception {
            final String accessToken = getAccessToken(user, pwd);
            mockMvc.perform(delete("/song/" + songToDelete().getId())
                    .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerNoAuth() throws Exception {
            mockMvc.perform(delete("/song/" + songToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("Get Song By ID")
    @Nested
    class GetBeerById {

        public Song songToTest() {
            Song song = new Song();
            song.setArtist("test artist");
            song.setGenre("test genre");
            song.setInformation("test info");
            song.setLanguage("test language");
            song.setTitle("test title");
            return songRepository.save(song);
        }

        @Test
        void getSongById() throws Exception {
            Song song = songRepository.findById(songToTest().getId()).orElse(new Song());
            mockMvc.perform(get("/song/" + song.getId()))
                    .andExpect(status().isUnauthorized());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.music.kevinmusic.controller.SongControllerIT#getStreamAllUsers")
        void getSongByIdAUTH(String user, String pwd) throws Exception {
            final String accessToken = getAccessToken(user, pwd);
            Song song = songRepository.findById(songToTest().getId()).orElse(new Song());
            mockMvc.perform(get("/song/" + song.getId())
                    .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().isOk());
        }
    }

    @DisplayName("List Songs with Single Query")
    @Nested
    class ListSongs {
        @Test
        void getSongSingleQuery() throws Exception {

            SongSingleRequest request = SongSingleRequest.builder()
                    .query("test").page(PageInfo.builder().page(0).size(10).build()).build();
            mockMvc.perform(post("/song/q")
                    .contentType("application/json")
                    .content(getJsonString(request))
                ).andExpect(status().isUnauthorized());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.music.kevinmusic.controller.SongControllerIT#getStreamAllUsers")
        void getSongSingleQueryAUTH(String user, String pwd) throws Exception {
            final String accessToken = getAccessToken(user, pwd);
            SongSingleRequest request = SongSingleRequest.builder()
                    .query("test").page(PageInfo.builder().page(0).size(10).build()).build();
            mockMvc.perform(post("/song/q")
                    .contentType("application/json")
                    .content(getJsonString(request))
                    .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().isOk());
        }
    }
}
