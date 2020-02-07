package com.music.kevinmusic.filter;

import com.music.kevinmusic.domain.Feedback;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import java.util.Date;

public class QFeedback extends EntityPathBase<Feedback> {
  public static final QFeedback feedbackEntity = new QFeedback("feedback");

  public final NumberPath<Long> id;
  public final StringPath emailOrphone;
  public final StringPath text;
  public final StringPath name;
  public final DateTimePath<Date> createdDate;
  public final StringPath createdBy;
  public final DateTimePath<Date> updatedDate;
  public final StringPath updatedBy;


  public QFeedback(String variable) {
    super(Feedback.class, PathMetadataFactory.forVariable(variable));

    this.id = this.createNumber("id", Long.class);
    this.emailOrphone = this.createString("emailOrphone");
    this.text = this.createString("text");
    this.name = this.createString("name");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdDate = this.createDateTime("createdDate", Date.class);
    this.updatedDate = this.createDateTime("updatedDate", Date.class);

  }

  public QFeedback(PathMetadata metadata) {
    super(Feedback.class, metadata);

    this.id = this.createNumber("id", Long.class);
    this.emailOrphone = this.createString("emailOrphone");
    this.text = this.createString("text");
    this.name = this.createString("name");
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdDate = this.createDateTime("createdDate", Date.class);
    this.updatedDate = this.createDateTime("updatedDate", Date.class);
  }
}
