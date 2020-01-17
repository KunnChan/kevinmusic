package com.music.kevinmusic.filter;

import com.music.kevinmusic.domain.Song;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

import java.util.Date;

public class QSong extends EntityPathBase<Song> {
  public static final QSong songEntity = new QSong("song");

  public final StringPath id;
  public final StringPath photoLink;
  public final StringPath title;
  public final StringPath genre;
  public final StringPath artist;
  public final StringPath information;
  public final StringPath album;
  public final StringPath language;
  public final DateTimePath<Date> createdAt;
  public final StringPath createdBy;
  public final DateTimePath<Date> updatedAt;
  public final StringPath updatedBy;

  public QSong(String variable) {
    super(Song.class, PathMetadataFactory.forVariable(variable));

    this.id = this.createString("id");
    this.photoLink = this.createString("photoLink");
    this.title = this.createString("title");
    this.genre = this.createString("genre");
    this.artist = this.createString("artist");
    this.information = this.createString("information");
    this.album = this.createString("album");
    this.language = this.createString("language");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);

  }

  public QSong(PathMetadata metadata) {
    super(Song.class, metadata);
    this.id = this.createString("id");
    this.photoLink = this.createString("photoLink");
    this.title = this.createString("title");
    this.genre = this.createString("genre");
    this.artist = this.createString("artist");
    this.information = this.createString("information");
    this.album = this.createString("album");
    this.language = this.createString("language");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);
  }
}
