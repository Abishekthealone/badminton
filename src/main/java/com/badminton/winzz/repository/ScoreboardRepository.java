package com.badminton.winzz.repository;

import com.badminton.winzz.models.Match;
import com.badminton.winzz.models.Scoreboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreboardRepository extends JpaRepository<Scoreboard, Long> {

    Optional<Scoreboard> findByMatch(Match match);

}
