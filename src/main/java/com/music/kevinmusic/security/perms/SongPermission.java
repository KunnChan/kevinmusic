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
public @interface SongPermission {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('song.create')")
    @interface Create {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('song.read')")
    @interface Read {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('song.update')")
    @interface Update {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAuthority('song.delete')")
    @interface Delete {
    }
}
