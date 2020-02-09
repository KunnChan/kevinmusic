package com.music.kevinmusic.filter;

import com.music.kevinmusic.domain.TransactionHistory;
import com.music.kevinmusic.enums.EventAction;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.*;

import java.util.Date;

public class QTransactionHistory extends EntityPathBase<TransactionHistory> {
  public static final QTransactionHistory historyEntity = new QTransactionHistory("transactionHistory");

  public final NumberPath<Long> id;
  public final StringPath payload;
  public final StringPath information;
  public final EnumPath<EventAction> eventAction;
  public final DateTimePath<Date> transactionDate;

  public QTransactionHistory(String variable) {
    super(TransactionHistory.class, PathMetadataFactory.forVariable(variable));

    this.id = this.createNumber("id", Long.class);
    this.payload = this.createString("payload");
    this.information = this.createString("information");
    this.eventAction = this.createEnum("eventAction", EventAction.class);
    this.transactionDate = this.createDateTime("transactionDate", Date.class);

  }

  public QTransactionHistory(PathMetadata metadata) {
    super(TransactionHistory.class, metadata);

    this.id = this.createNumber("id", Long.class);
    this.payload = this.createString("payload");
    this.information = this.createString("information");
    this.eventAction = this.createEnum("eventAction", EventAction.class);
    this.transactionDate = this.createDateTime("transactionDate", Date.class);
  }
}
