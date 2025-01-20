package com.famdev.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.famdev.dslist.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    
} 