package com.todaymeal.todaymeal.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

	@Query(value = "select u from Post p join fetch User u where p.user = u and p.id = ?1")
	Optional<User> findByPostId(Long postId);
}
