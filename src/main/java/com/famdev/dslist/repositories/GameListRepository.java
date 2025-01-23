package com.famdev.dslist.repositories;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.famdev.dslist.entities.GameList;
import com.famdev.dslist.projections.GameMinProjection;

import jakarta.transaction.Transactional;

public interface GameListRepository extends JpaRepository<GameList, Long> {

    // @Modifying
    // @Query("UPDATE GameList SET position = :position WHERE id = :id")
    // void updatePosition(Long id, int position);

    // @Modifying // Modifying é necessário para operações de escrita (Udate,
    // Delete, Insert)
    // @Query(nativeQuery = true, value = """
    // UPDATE tb_belonging SET position = :newPosition
    // WHERE list_id = : listId
    // AND game_id=:gameId
    // """)

    // void updateBelongingPosition(Long listId, Long gameId, int newPosition);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            UPDATE tb_belonging SET position = :newPosition
            WHERE list_id = :listId
            AND game_id = :gameId
            """)
    void updateBelongingPosition(
            Long listId,
            Long gameId, int newPosition);

}