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

	@Override
	public List<Project> getProjectDetails() {
		return projectRepository.findAll();
	}

	@Override
	public List<User> getUserDetails() {
		return userRepository.findAll();
	}

	@Override
	public List<Task> getTaskDetails() {
		return taskRepository.findAll();
	}

	@Override
	public List<Task> getTaskDetailsForProject(int projectId) {
		return taskRepository.getTaskDetails(projectId);
	}

	@Override
	public List<ParentTask> getParentTasks(int projectId) {
		return parentTaskRespository.getParentTasks(projectId);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Project updateProject(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public ParentTask updateParentTask(ParentTask parentTask) {
		return parentTaskRespository.save(parentTask);
	}

	@Override
	public Task updateTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public User getUser(int userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public Project getProject(int projectId) {
		return projectRepository.findByProjectId(projectId);
	}

	@Override
	public ParentTask getParentTask(int parentId) {
		return parentTaskRespository.findByParentId(parentId);
	}

	@Override
	public boolean deleteUser(int userId) {
		deleteProjectForUser(userId);
		userRepository.deleteById(userId);
		return true;
	}

	@Override
	public boolean deleteProject(int projectId) {
		deleteTaskForProjectId(projectId);
		deleteParentTaskForProjectId(projectId);
		projectRepository.deleteById(projectId);
		return true;
	}

	@Override
	public boolean deleteTask(int taskId) {
		taskRepository.deleteById(taskId);
		return true;
	}

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

	@Override
	public boolean deleteParentTaskForProjectId(int projectId) {
		parentTaskRespository.deleteParentTaskForProject(projectId);
		return true;
	}

	@Override
	public boolean deleteTaskForProjectId(int projectId) {
		taskRepository.deleteTaskForProject(projectId);
		return true;
	}
}
