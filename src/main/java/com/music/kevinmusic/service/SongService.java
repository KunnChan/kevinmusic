package com.music.kevinmusic.service;

import com.music.kevinmusic.command.SongCommand;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.request.SongRequest;
import com.music.kevinmusic.request.SongSingleRequest;
import org.springframework.data.domain.Page;

public interface SongService {

    Song getSongById(Long id, Information information);

    Page<Song> getFilterOneQuery(SongSingleRequest songSingleRequest);

    Page<Song> getFilter(SongRequest songRequest);

    Song saveOrUpdate(SongCommand songCommand, Information information);

    Song addComment(SongCommand songCommand, Information information);

    Song addDownloadLinks(SongCommand songCommand, Information information);

    Song addReaction(SongCommand songCommand, Information information);

    Song removeDownloadLink(SongCommand songCommand, Information information);

    Song addSongLyric(SongCommand songCommand, Information information);
}
