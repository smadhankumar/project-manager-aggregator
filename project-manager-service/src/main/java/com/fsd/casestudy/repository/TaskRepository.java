package com.fsd.casestudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fsd.casestudy.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findAll();

	@Query(value = "SELECT t FROM Task t WHERE t.project.projectId = :projectId")
	List<Task> getTaskDetails(@Param("projectId") int projectId);

	@Modifying
	@Query(value = "delete from task where project_id= :projectId", nativeQuery = true)
	void deleteTaskForProject(@Param("projectId") int projectId);

}
