package com.music.kevinmusic.filter;

import com.music.kevinmusic.domain.Album;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import java.util.Date;

public class QAlbum extends EntityPathBase<Album> {
  public static final QAlbum albumEntity = new QAlbum("album");

  public final NumberPath<Long> id;
  public final StringPath photoLink;
  public final StringPath title;
  public final StringPath genre;
  public final StringPath artist;
  public final DateTimePath<Date> createdAt;
  public final StringPath createdBy;
  public final DateTimePath<Date> updatedAt;
  public final StringPath updatedBy;

  public QAlbum(String variable) {
    super(Album.class, PathMetadataFactory.forVariable(variable));

    this.id = this.createNumber("id", Long.class);
    this.photoLink = this.createString("photoLink");
    this.title = this.createString("title");
    this.genre = this.createString("genre");
    this.artist = this.createString("artist");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);

  }

  public QAlbum(PathMetadata metadata) {
    super(Album.class, metadata);
    this.id = this.createNumber("id", Long.class);
    this.photoLink = this.createString("photoLink");
    this.title = this.createString("title");
    this.genre = this.createString("genre");
    this.artist = this.createString("artist");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);
  }
}
