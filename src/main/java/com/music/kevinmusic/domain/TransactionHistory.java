package com.music.kevinmusic.domain;

import com.music.kevinmusic.enums.EventAction;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date transactionDate;

    @Lob
    private String payload;

    @OneToOne(cascade = CascadeType.ALL)
    private Information information;

    @Enumerated(value = EnumType.STRING)
    private EventAction eventAction;

    public TransactionHistory(String payload, EventAction eventAction) {
        this.payload = payload;
        this.eventAction = eventAction;
        this.transactionDate = new Date();
    }

    public TransactionHistory(String payload) {
        this.payload = payload;
        this.transactionDate = new Date();
    }
}
