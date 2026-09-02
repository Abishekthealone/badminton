package com.badminton.winzz.repository;

import com.badminton.winzz.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerUserDetailsServiceRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByUsername(String username);

}
