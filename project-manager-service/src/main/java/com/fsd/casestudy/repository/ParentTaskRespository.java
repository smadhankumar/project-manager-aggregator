package com.fsd.casestudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fsd.casestudy.entity.ParentTask;

@Repository
public interface ParentTaskRespository extends JpaRepository<ParentTask, Integer> {

	List<ParentTask> findAll();

	@Query(value = "SELECT pt FROM ParentTask pt WHERE pt.project.projectId = :projectId")
	List<ParentTask> getParentTasks(@Param("projectId") int projectId);

	ParentTask findByParentId(int parentId);

	@Modifying
	@Query(value = "delete from parent_task where project_id=:projectId", nativeQuery = true)
	void deleteParentTaskForProject(@Param("projectId") int projectId);
}
