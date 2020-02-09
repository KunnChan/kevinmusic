package com.music.kevinmusic.service;

import com.music.kevinmusic.command.SongCommand;
import com.music.kevinmusic.command.SongDto;
import com.music.kevinmusic.command.SongPageDto;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.request.SongRequest;
import com.music.kevinmusic.request.SongSingleRequest;

import java.util.List;

public interface SongService {

    Song getSongById(Long id);

    SongPageDto getFilterOneQuery(SongSingleRequest songSingleRequest);

    SongPageDto getFilter(SongRequest songRequest);

    Song saveOrUpdate(SongCommand songCommand, Information information);

    Song addComment(SongCommand songCommand, Information information);

    Song addDownloadLinks(SongCommand songCommand, Information information);

    Song addReaction(SongCommand songCommand, Information information);

    Song removeDownloadLink(SongCommand songCommand, Information information);

    Song addSongLyric(SongCommand songCommand, Information information);

    Song addDownload(SongCommand songCommand, Information information);

    List<SongDto> getSongByAlbumId(Long albumId);
}
