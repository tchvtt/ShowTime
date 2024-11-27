package com.ShowTime.repository;

import com.ShowTime.model.Media;
import com.ShowTime.model.MediaList;
import com.ShowTime.model.MediaType;
import com.ShowTime.model.MediaListType;
import com.ShowTime.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediaListRepository extends JpaRepository<MediaList, Long> {

    // Retourne une liste générale de médias selon son type (trending, Most Watched, ...)
    MediaList findByMediaListTypeAndMediaType(MediaListType mediaListType, MediaType mediaType);

    // Retourne une liste spécifique d'un User (genre les films favoris)
    MediaList findByUserAndMediaListTypeAndMediaType(User user, MediaListType mediaListType, MediaType mediaType);

    // Retourne toutes les listes d'un User
    List<MediaList> findByUser(User user);

    @Query("SELECT CASE WHEN COUNT(ml) > 0 THEN TRUE ELSE FALSE END " +
            "FROM MediaList ml JOIN ml.mediaList m " +
            "WHERE ml.user = :user AND m = :media AND ml.mediaListType = :mediaListType")
    boolean existsByUserAndMediaAndMediaListType(
        @Param("user") User user, 
        @Param("media") Media media, 
        @Param("mediaListType") MediaListType mediaListType
    );

}
