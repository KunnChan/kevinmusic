package com.music.kevinmusic.service;

import com.music.kevinmusic.command.SongCommand;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.request.SongRequest;
import com.music.kevinmusic.request.SongSingleRequest;
import org.springframework.data.domain.Page;

import java.util.List;

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

    Song addDownload(SongCommand songCommand, Information information);

    List<Song> getTop15Album(Information information);

    Page<Song> getPopularSong(SongSingleRequest songSingleRequest);
}
