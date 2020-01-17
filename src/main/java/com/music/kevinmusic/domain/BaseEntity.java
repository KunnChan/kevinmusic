package com.music.kevinmusic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(CommonEntityListener.class)
public abstract class BaseEntity extends Audaitable<String> implements Serializable{

}
