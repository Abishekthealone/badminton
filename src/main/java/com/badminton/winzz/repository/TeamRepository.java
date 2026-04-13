package com.badminton.winzz.repository;

import com.badminton.winzz.dto.TeamResponse;
import com.badminton.winzz.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    
   
    
    List<Team> findByTournamentId(Long tournamentId);

    List<Team> findAllByTournamentId(Long id);

    @Modifying
    @Query("DELETE FROM Team t WHERE t.tournament.id = :tournamentId")
    void deleteByTournamentId(Long tournamentId);
}
