package com.fsd.casestudy.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsd.casestudy.entity.ParentTask;
import com.fsd.casestudy.entity.Project;
import com.fsd.casestudy.entity.Task;
import com.fsd.casestudy.entity.User;
import com.fsd.casestudy.repository.ParentTaskRespository;
import com.fsd.casestudy.repository.ProjectRepository;
import com.fsd.casestudy.repository.TaskRepository;
import com.fsd.casestudy.repository.UserRepository;

/**
 * 
 * This class is used to perform DB operations
 * 
 * @author 463657
 *
 */
@Component
@Transactional
public class ProjectManagerDAOImpl implements ProjectManagerDAO {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ParentTaskRespository parentTaskRespository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	/**
	 * Retrieves all project details
	 */
	@Override
	public List<Project> getProjectDetails() {
		return projectRepository.findAll();
	}

	/**
	 * Retrieves all user details
	 */
	@Override
	public List<User> getUserDetails() {
		return userRepository.findAll();
	}

	/**
	 * Retrieves all task details
	 */
	@Override
	public List<Task> getTaskDetails() {
		return taskRepository.findAll();
	}

	/**
	 * Retrieves task details for specific project
	 */
	@Override
	public List<Task> getTaskDetailsForProject(int projectId) {
		return taskRepository.getTaskDetails(projectId);
	}

	/**
	 * Retrieves parent tasks details for specific project
	 */
	@Override
	public List<ParentTask> getParentTasks(int projectId) {
		return parentTaskRespository.getParentTasks(projectId);
	}

	/**
	 * Add/Update user details
	 */
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Add/Update project details
	 */
	@Override
	public Project updateProject(Project project) {
		return projectRepository.save(project);
	}

	/**
	 * Add/Update parent task details
	 */
	@Override
	public ParentTask updateParentTask(ParentTask parentTask) {
		return parentTaskRespository.save(parentTask);
	}

	/**
	 * Add/Update task details
	 */
	@Override
	public Task updateTask(Task task) {
		return taskRepository.save(task);
	}

	/**
	 * Get user details for user id
	 */
	@Override
	public User getUser(int userId) {
		return userRepository.findByUserId(userId);
	}

	/**
	 * Get project details for project id
	 */
	@Override
	public Project getProject(int projectId) {
		return projectRepository.findByProjectId(projectId);
	}

	/**
	 * Get parent task details for parent id
	 */
	@Override
	public ParentTask getParentTask(int parentId) {
		return parentTaskRespository.findByParentId(parentId);
	}

	/**
	 * Deletes user detail for user id
	 */
	@Override
	public boolean deleteUser(int userId) {
		deleteProjectForUser(userId);
		userRepository.deleteById(userId);
		return true;
	}

	/**
	 * Deletes project details for project id
	 */
	@Override
	public boolean deleteProject(int projectId) {
		deleteTaskForProjectId(projectId);
		deleteParentTaskForProjectId(projectId);
		projectRepository.deleteById(projectId);
		return true;
	}

	/**
	 * Deletes task details for task id
	 */
	@Override
	public boolean deleteTask(int taskId) {
		taskRepository.deleteById(taskId);
		return true;
	}

	/**
	 * Delete project details for user id
	 */
	@Override
	public boolean deleteProjectForUser(int userId) {
		List<Project> projects = projectRepository.getProjectsForUser(userId);
		if (null != projects && projects.size() > 0) {
			for (Project project : projects) {
				deleteTaskForProjectId(project.getProjectId());
				deleteParentTaskForProjectId(project.getProjectId());
			}
		}
		projectRepository.deleteProjectForUser(userId);
		return true;
	}

	/**
	 * Deletes parent task for project id
	 */
	@Override
	public boolean deleteParentTaskForProjectId(int projectId) {
		parentTaskRespository.deleteParentTaskForProject(projectId);
		return true;
	}

	/**
	 * Deletes task for project id
	 */
	@Override
	public boolean deleteTaskForProjectId(int projectId) {
		taskRepository.deleteTaskForProject(projectId);
		return true;
	}
}
