//package com.badminton.winzz.models;
//
//
//import jakarta.persistence.*;
//import org.springframework.data.domain.Score;
//
//import java.time.LocalDateTime;
//
//
//public class Scoreboard {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @OneToOne
//    private Match match;
//    private Integer teamAScore;
//    private Integer teamBScore;
//    private Integer setsTeamA;
//    private Integer setsTeamB;
//    private LocalDateTime updatedAt = LocalDateTime.now();
//
//    Scoreboard(){}
//
//    public Scoreboard(Long id, Match match, Integer teamAScore, Integer teamBScore, Integer setsTeamA, Integer setsTeamB, LocalDateTime updatedAt) {
//        this.id = id;
//        this.match = match;
//        this.teamAScore = teamAScore;
//        this.teamBScore = teamBScore;
//        this.setsTeamA = setsTeamA;
//        this.setsTeamB = setsTeamB;
//        this.updatedAt = updatedAt;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Match getMatch() {
//        return match;
//    }
//
//    public void setMatch(Match match) {
//        this.match = match;
//    }
//
//    public Integer getTeamAScore() {
//        return teamAScore;
//    }
//
//    public void setTeamAScore(Integer teamAScore) {
//        this.teamAScore = teamAScore;
//    }
//
//    public Integer getTeamBScore() {
//        return teamBScore;
//    }
//
//    public void setTeamBScore(Integer teamBScore) {
//        this.teamBScore = teamBScore;
//    }
//
//    public Integer getSetsTeamA() {
//        return setsTeamA;
//    }
//
//    public void setSetsTeamA(Integer setsTeamA) {
//        this.setsTeamA = setsTeamA;
//    }
//
//    public Integer getSetsTeamB() {
//        return setsTeamB;
//    }
//
//    public void setSetsTeamB(Integer setsTeamB) {
//        this.setsTeamB = setsTeamB;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}
