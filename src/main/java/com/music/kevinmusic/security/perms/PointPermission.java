package com.music.kevinmusic.security.perms;
/*
 * Created by kunnchan on 20/07/2020
 * package :  com.music.kevinmusic.security.perms
 */

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface PointPermission {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('point.create')")
    @interface Create {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('point.read')")
    @interface Read {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('point.update')")
    @interface Update {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('point.delete')")
    @interface Delete {
    }
}
