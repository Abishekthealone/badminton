package com.badminton.winzz.repository;

import com.badminton.winzz.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTournamentId(Long tournamentId);

    boolean existsByTournamentId(Long tournamentId);




    @Query("""
SELECT m FROM Match m
LEFT JOIN FETCH m.teamA
LEFT JOIN FETCH m.teamB
LEFT JOIN FETCH m.scoreboard
WHERE m.id = :id
""")
    Optional<Match> findMatchWithDetails(@Param("id") Long id);
}
