package com.week6.AuthFlow.repositories;

import com.week6.AuthFlow.entities.SessionEntity;
import com.week6.AuthFlow.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
  List<SessionEntity> findByUser(UserEntity user);

  Optional<SessionEntity> findByRefreshToken(String refreshToken);

  void deleteByRefreshToken(String refreshToken);
}