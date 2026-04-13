package com.badminton.winzz.repository;

import com.badminton.winzz.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {



    List<Player> findByTournamentId(Long tournamentId);

    @Modifying
    @Query("UPDATE Player p SET p.team = null WHERE p.tournament.id = :tournamentId")
    void clearTeamFromPlayers(Long tournamentId);
}
