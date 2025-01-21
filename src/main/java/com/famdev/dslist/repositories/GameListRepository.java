package com.famdev.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.famdev.dslist.entities.GameList;

public interface GameListRepository extends JpaRepository<GameList, Long> {

    
} 