package com.pisarevAS.familybudget.user.repository;

import com.pisarevAS.familybudget.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    void deleteUserByLogin(String login);
}