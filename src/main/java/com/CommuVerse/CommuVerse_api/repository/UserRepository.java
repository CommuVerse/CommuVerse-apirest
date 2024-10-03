package com.CommuVerse.CommuVerse_api.repository;

import com.CommuVerse.CommuVerse_api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByNickName(String nickname);  
}
