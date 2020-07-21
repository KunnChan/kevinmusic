package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.Song;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends PagingAndSortingRepository<Song, Long>, QuerydslPredicateExecutor<Song> {

    List<Song> findAllByAlbumId(Long id);

//    @Query("select o from BeerOrder o where o.id =?1 and " +
//            "(true = :#{hasAuthority('order.read')} or o.customer.id = ?#{principal?.customer?.id})")
//    BeerOrder findOrderByIdSecure(UUID orderId);
}
