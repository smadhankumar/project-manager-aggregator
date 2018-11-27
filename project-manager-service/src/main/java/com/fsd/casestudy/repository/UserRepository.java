package com.fsd.casestudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsd.casestudy.entity.User;

/**
 * Repository for User
 * 
 * @author 463657
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findAll();

	User findByUserId(int userId);

}
