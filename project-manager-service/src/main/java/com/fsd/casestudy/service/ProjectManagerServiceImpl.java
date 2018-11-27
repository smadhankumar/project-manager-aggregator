package com.fsd.casestudy.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.casestudy.dao.ProjectManagerDAO;
import com.fsd.casestudy.entity.ParentTask;
import com.fsd.casestudy.entity.Project;
import com.fsd.casestudy.entity.Task;
import com.fsd.casestudy.entity.User;
import com.fsd.casestudy.exception.TaskException;
import com.fsd.casestudy.model.ParentTaskInfo;
import com.fsd.casestudy.model.ProjectInfo;
import com.fsd.casestudy.model.TaskInfo;
import com.fsd.casestudy.model.UserInfo;

/**
 * Service class for operations of Project Manager application
 * 
 * @author 463657
 *
 */

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagerServiceImpl.class);

	@Autowired
	private ProjectManagerDAO projectManagerDAOImpl;

	/**
	 * Retrieves project info
	 */
	@Override
	public List<ProjectInfo> getProjectInfo() throws TaskException {
		LOGGER.debug("Inside getProjectInfo() of ProjectManagerServiceImpl - Start");
		List<ProjectInfo> projectInfo = null;
		try {
			List<Project> projects = projectManagerDAOImpl.getProjectDetails();
			ProjectInfo projectIn = null;
			if (null != projects && projects.size() > 0) {
				projectInfo = new ArrayList<ProjectInfo>();
				for (Project project : projects) {
					projectIn = new ProjectInfo();
					projectIn.setProjectId(project.getProjectId());
					projectIn.setProjectName(project.getProjectName());
					projectIn.setStartDate(project.getStartDate());
					projectIn.setEndDate(project.getEndDate());
					projectIn.setPriority(project.getPriority());
					projectIn.setUserId(project.getUser().getUserId());
					if (null != project.getUser()) {
						projectIn.setUserName(project.getUser().getFirstName() + " " + project.getUser().getLastName());
					}
					project.setProjectId(project.getProjectId());
					if (null != project.getTasks() && project.getTasks().size() > 0) {
						projectIn.setTotalNoOfTasks(project.getTasks().size());
						int completedTaskCount = 0;
						for (Task task : project.getTasks()) {
							if ("Completed".equalsIgnoreCase(task.getStatus())) {
								completedTaskCount++;
							}
						}
						projectIn.setNoOfTasksCompleted(completedTaskCount);
					}
					projectInfo.add(projectIn);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getProjectInfo() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside getProjectInfo() of ProjectManagerServiceImpl - End");
		return projectInfo;
	}

	/**
	 * Retrieves user info
	 */
	@Override
	public List<UserInfo> getUserInfo() throws TaskException {
		LOGGER.debug("Inside getUserInfo() of ProjectManagerServiceImpl - Start");
		List<UserInfo> userInfo = null;
		try {
			List<User> users = projectManagerDAOImpl.getUserDetails();
			UserInfo userIn = null;
			if (null != users && users.size() > 0) {
				userInfo = new ArrayList<UserInfo>();
				for (User user : users) {
					userIn = new UserInfo();
					userIn.setEmpId(user.getEmployeeId());
					userIn.setFirstName(user.getFirstName());
					userIn.setLastName(user.getLastName());
					userIn.setUserId(user.getUserId());
					userInfo.add(userIn);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getUserInfo() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside getUserInfo() of ProjectManagerServiceImpl - End");
		return userInfo;
	}

	/**
	 * Retrieves task info for given project id
	 */
	@Override
	public List<TaskInfo> getTaskInfo(int projectId) throws TaskException {
		LOGGER.debug("Inside getTaskInfo() of ProjectManagerServiceImpl - Start");
		List<TaskInfo> taskInfo = null;
		try {
			List<Task> tasks = null;
			if (projectId != 0) {
				tasks = projectManagerDAOImpl.getTaskDetailsForProject(projectId);
			} else {
				tasks = projectManagerDAOImpl.getTaskDetails();
			}
			TaskInfo taskIn = null;
			if (null != tasks && tasks.size() > 0) {
				taskInfo = new ArrayList<TaskInfo>();
				for (Task task : tasks) {
					taskIn = new TaskInfo();
					taskIn.setTaskId(task.getTaskId());
					if (null != task.getParentTask()) {
						taskIn.setParentId(task.getParentTask().getParentId());
						taskIn.setParentTaskName(task.getParentTask().getParentTask());
					}
					if (null != task.getProject()) {
						taskIn.setProjectId(task.getProject().getProjectId());
						taskIn.setProjectName(task.getProject().getProjectName());
						if (null != task.getProject().getUser()) {
							taskIn.setUserId(task.getProject().getUser().getUserId());
							taskIn.setUserName(task.getProject().getUser().getFirstName() + " "
									+ task.getProject().getUser().getLastName());
						}
					}
					taskIn.setTaskName(task.getTask());
					taskIn.setStartDate(task.getStartDate());
					taskIn.setEndDate(task.getEndDate());
					taskIn.setPriority(task.getPriority());
					taskIn.setStatus(task.getStatus());
					taskInfo.add(taskIn);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getTaskInfo() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside getTaskInfo() of ProjectManagerServiceImpl - End");
		return taskInfo;
	}

	/**
	 * Add/Update User Info
	 */
	@Override
	public Boolean updateUserInfo(UserInfo userInfo) throws TaskException {
		LOGGER.debug("Inside updateUserInfo() of ProjectManagerServiceImpl - Start");
		try {
			if (null != userInfo) {
				User user = new User();
				user.setUserId(userInfo.getUserId());
				user.setFirstName(userInfo.getFirstName());
				user.setLastName(userInfo.getLastName());
				user.setEmployeeId(userInfo.getEmpId());
				projectManagerDAOImpl.updateUser(user);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in updateUserInfo() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside updateUserInfo() of ProjectManagerServiceImpl - End");
		return true;
	}

	/**
	 * Add/Update Project info
	 */
	@Override
	public Boolean updateProjectInfo(ProjectInfo projectInfo) throws TaskException {
		LOGGER.debug("Inside updateProjectInfo() of ProjectManagerServiceImpl - Start");
		try {
			if (null != projectInfo) {
				Project project = new Project();
				project.setProjectId(projectInfo.getProjectId());
				project.setProjectName(projectInfo.getProjectName());
				project.setStartDate(projectInfo.getStartDate());
				project.setEndDate(projectInfo.getEndDate());
				project.setPriority(projectInfo.getPriority());
				User user = projectManagerDAOImpl.getUser(projectInfo.getUserId());
				project.setUser(user);
				projectManagerDAOImpl.updateProject(project);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in updateProjectInfo() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside updateProjectInfo() of ProjectManagerServiceImpl - End");
		return true;
	}

	/**
	 * Add/Update task info
	 */
	@Override
	public Boolean updateTaskInfo(TaskInfo taskInfo) throws TaskException {
		LOGGER.debug("Inside updateTaskInfo() of ProjectManagerServiceImpl - Start");
		try {
			if (null != taskInfo) {
				if (taskInfo.isParentTaskEnabled()) {
					ParentTask parentTask = new ParentTask();
					parentTask.setParentId(taskInfo.getParentId());
					parentTask.setParentTask(taskInfo.getTaskName());
					Project project = projectManagerDAOImpl.getProject(taskInfo.getProjectId());
					parentTask.setProject(project);
					projectManagerDAOImpl.updateParentTask(parentTask);
				} else {
					Task task = new Task();
					task.setTaskId(taskInfo.getTaskId());
					task.setTask(taskInfo.getTaskName());
					task.setPriority(taskInfo.getPriority());
					task.setStartDate(taskInfo.getStartDate());
					task.setEndDate(taskInfo.getEndDate());
					task.setStatus(taskInfo.getStatus());
					Project project = projectManagerDAOImpl.getProject(taskInfo.getProjectId());
					task.setProject(project);
					if (taskInfo.getParentId() != 0) {
						ParentTask parentTask = projectManagerDAOImpl.getParentTask(taskInfo.getParentId());
						task.setParentTask(parentTask);
					}
					projectManagerDAOImpl.updateTask(task);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in updateTaskInfo() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside updateTaskInfo() of ProjectManagerServiceImpl - End");
		return true;
	}

	/**
	 * Retrieves Parent tasks for given project id
	 */
	@Override
	public List<ParentTaskInfo> getParentTasks(int projectId) throws TaskException {
		LOGGER.debug("Inside getParentTasks() of ProjectManagerServiceImpl - Start");
		List<ParentTaskInfo> parentTaskInfo = null;
		try {
			List<ParentTask> parentTasks = projectManagerDAOImpl.getParentTasks(projectId);
			ParentTaskInfo info = null;
			if (null != parentTasks && parentTasks.size() > 0) {
				parentTaskInfo = new ArrayList<ParentTaskInfo>();
				for (ParentTask parentTask : parentTasks) {
					info = new ParentTaskInfo();
					info.setParentId(parentTask.getParentId());
					info.setParentTaskName(parentTask.getParentTask());
					info.setProjectId(parentTask.getProject().getProjectId());
					parentTaskInfo.add(info);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getParentTasks() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside getParentTasks() of ProjectManagerServiceImpl - End");
		return parentTaskInfo;
	}

	/**
	 * Deletes user for given user id
	 */
	@Override
	public Boolean deleteUser(int userId) throws TaskException {
		LOGGER.debug("Inside deleteUser() of ProjectManagerServiceImpl - Start");
		Boolean deletedFlag = false;
		try {
			deletedFlag = projectManagerDAOImpl.deleteUser(userId);
		} catch (Exception e) {
			LOGGER.error("Exception in deleteUser() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside deleteUser() of ProjectManagerServiceImpl - End");
		return deletedFlag;
	}

	/**
	 * Deletes project for given project id
	 */
	@Override
	public Boolean deleteProject(int projectId) throws TaskException {
		LOGGER.debug("Inside deleteUser() of ProjectManagerServiceImpl - Start");
		Boolean deletedFlag = false;
		try {
			deletedFlag = projectManagerDAOImpl.deleteProject(projectId);
		} catch (Exception e) {
			LOGGER.error("Exception in deleteProject() of ProjectManagerServiceImpl " + e);
			throw new TaskException("1000", "Technical Error", 500);
		}
		LOGGER.debug("Inside deleteUser() of ProjectManagerServiceImpl - End");
		return deletedFlag;
	}

}
