package com.fsd.casestudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fsd.casestudy.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findAll();

	Project findByProjectId(int projectId);

	@Query(value = "SELECT pj FROM Project pj WHERE pj.user.userId = :userId")
	List<Project> getProjectsForUser(@Param("userId") int userId);

	@Modifying
	@Query(value = "delete from project where user_id=:userId", nativeQuery = true)
	void deleteProjectForUser(@Param("userId") int userId);

}
