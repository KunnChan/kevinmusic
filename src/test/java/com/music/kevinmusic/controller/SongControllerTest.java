package com.music.kevinmusic.controller;

import com.music.kevinmusic.command.SongCommand;
import com.music.kevinmusic.command.SongPageDto;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.request.PageInfo;
import com.music.kevinmusic.request.SongSingleRequest;
import com.music.kevinmusic.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.music.kevinmusic.controller.BaseIT.getJsonString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SongControllerTest {

    @Mock
    SongService songService;

    @InjectMocks
    SongController controller;

    MockMvc mockMvc;
    Song song;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        song = new Song();
        song.setId(1L);
    }

    @Test
    void getSongByIdTest() throws Exception {
        when(songService.getSongById(1L)).thenReturn(song);
        mockMvc.perform(get("/song/"+1L))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSongByIdTest() throws Exception {
        mockMvc.perform(delete("/song/"+1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void getSongSingleQueryTest() throws Exception {
        SongSingleRequest request = SongSingleRequest.builder()
                .query("test").page(PageInfo.builder().page(0).size(10).build()).build();

        when(songService.getFilterOneQuery(request, new Information())).thenReturn(new SongPageDto());
        mockMvc.perform(post("/song/q")
                .contentType("application/json")
                .content(getJsonString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void saveOrUpdateTest() throws Exception {

        SongCommand songCommand = new SongCommand();
        songCommand.setId(1L);
        songCommand.setTitle("Title1");

        when(songService.saveOrUpdate(songCommand, new Information())).thenReturn(new Song());

        mockMvc.perform(post("/shield/song/save")
                .contentType("application/json")
                .content(getJsonString(songCommand))
        ).andExpect(status().isOk());

        verify(songService).saveOrUpdate(songCommand, new Information());
    }
}