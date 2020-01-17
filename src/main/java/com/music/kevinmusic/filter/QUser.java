package com.music.kevinmusic.filter;

import com.music.kevinmusic.domain.Song;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import java.util.Date;

public class QUser extends EntityPathBase<Song> {
  public static final QUser userEntity = new QUser("user");

  public final NumberPath<Long> id;
  public final StringPath username;
  public final StringPath name;
  public final StringPath note;
  public final StringPath userStatus;
  public final StringPath phone;
  public final StringPath email;
  public final DateTimePath<Date> createdAt;
  public final StringPath createdBy;
  public final DateTimePath<Date> updatedAt;
  public final StringPath updatedBy;

  public QUser(String variable) {
    super(Song.class, PathMetadataFactory.forVariable(variable));

    this.id = this.createNumber("id", Long.class);
    this.username = this.createString("username");
    this.name = this.createString("name");
    this.note = this.createString("note");
    this.phone = this.createString("phone");
    this.email = this.createString("email");
    this.userStatus = this.createString("userStatus");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);

  }

  public QUser(PathMetadata metadata) {
    super(Song.class, metadata);
    this.id = this.createNumber("id", Long.class);
    this.username = this.createString("username");
    this.name = this.createString("name");
    this.note = this.createString("note");
    this.phone = this.createString("phone");
    this.email = this.createString("email");
    this.userStatus = this.createString("userStatus");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);
  }
}
