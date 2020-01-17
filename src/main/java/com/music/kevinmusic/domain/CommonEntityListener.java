package com.music.kevinmusic.domain;


import com.music.kevinmusic.service.impl.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.MANDATORY;

public class CommonEntityListener {
    @PrePersist
    public void prePersist(Object target) {
        perform(target, ModeAction.INSERT);
    }

    @PreUpdate
    public void preUpdate(Object target) {
        perform(target, ModeAction.UPDATE);
    }

    @PreRemove
    public void preRemove(Object target) {
        perform(target, ModeAction.DELETE);
    }

    @Transactional(MANDATORY)
    public void perform(Object target, ModeAction action) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(new History(target, action));
    }
}
