package com.music.kevinmusic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class StatusEntity extends BaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = -1071676814987528980L;

    @Enumerated(value = EnumType.STRING)
    private ActivationStatus activationStatus;
}
