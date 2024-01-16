package com.example.demoauth.repository;

import java.util.Optional;

import com.example.demoauth.models.UserAuthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserAuthData, Long>{
	Optional<UserAuthData> findByUsername(String username);
	Optional<UserAuthData> findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

	boolean existsByPassword(String password);

	UserAuthData findByUserId(String id);

	@Query("UPDATE UserAuthData U SET U.isEnabled = true where U.id = ?1")
	@Modifying
	@Transactional
	void enable(Long id);

	UserAuthData findByVerificationCode(String code);

}
