package com.fsd.casestudy.service;

import java.util.ArrayList;
import java.util.List;

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

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService{
	
	@Autowired
	private ProjectManagerDAO projectManagerDAOImpl;

	@Override
	public List<ProjectInfo> getProjectInfo() throws TaskException{
		List<ProjectInfo> projectInfo = null;
		try {
			List<Project> projects = projectManagerDAOImpl.getProjectDetails();
			ProjectInfo projectIn = null;
			if(null != projects && projects.size() > 0) {
				projectInfo = new ArrayList<ProjectInfo>();
				for(Project project : projects) {
					projectIn = new ProjectInfo();
					projectIn.setProjectId(project.getProjectId());
					projectIn.setProjectName(project.getProjectName());
					projectIn.setStartDate(project.getStartDate());
					projectIn.setEndDate(project.getEndDate());
					projectIn.setPriority(project.getPriority());
					projectIn.setUserId(project.getUser().getUserId());
					if(null != project.getUser()) {
						projectIn.setUserName(project.getUser().getFirstName() + " "+project.getUser().getLastName());
					}
					project.setProjectId(project.getProjectId());
					if(null != project.getTasks() && project.getTasks().size() > 0) {
						projectIn.setTotalNoOfTasks(project.getTasks().size());
						int completedTaskCount = 0;
						for(Task task : project.getTasks()) {
							if("Completed".equalsIgnoreCase(task.getStatus())){
								completedTaskCount++;
							}
						}
						projectIn.setNoOfTasksCompleted(completedTaskCount);
					}
					projectInfo.add(projectIn);
				}
			}
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return projectInfo;
	}

	@Override
	public List<UserInfo> getUserInfo() throws TaskException {
		List<UserInfo> userInfo = null;
		try {
			List<User> users = projectManagerDAOImpl.getUserDetails();
			UserInfo userIn = null;
			if(null != users && users.size() > 0) {
				userInfo = new ArrayList<UserInfo>();
				for(User user : users) {
					userIn = new UserInfo();
					userIn.setEmpId(user.getEmployeeId());
					userIn.setFirstName(user.getFirstName());
					userIn.setLastName(user.getLastName());
					userIn.setUserId(user.getUserId());
					userInfo.add(userIn);
				}
			}
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return userInfo;
	}

	@Override
	public List<TaskInfo> getTaskInfo(int projectId) throws TaskException {
		List<TaskInfo> taskInfo = null;
		try {
			List<Task> tasks = null;
			if(projectId != 0) {
				tasks = projectManagerDAOImpl.getTaskDetailsForProject(projectId);
			}else {
				tasks = projectManagerDAOImpl.getTaskDetails();
			}
			TaskInfo taskIn = null;
			if(null != tasks && tasks.size() > 0) {
				taskInfo = new ArrayList<TaskInfo>();
				for(Task task : tasks) {
					taskIn = new TaskInfo();
					taskIn.setTaskId(task.getTaskId());
					if(null != task.getParentTask()) {
						taskIn.setParentId(task.getParentTask().getParentId());
						taskIn.setParentTaskName(task.getParentTask().getParentTask());
					}
					if(null != task.getProject()) {
						taskIn.setProjectId(task.getProject().getProjectId());
						taskIn.setProjectName(task.getProject().getProjectName());
						if(null != task.getProject().getUser()) {
							taskIn.setUserId(task.getProject().getUser().getUserId());
							taskIn.setUserName(task.getProject().getUser().getFirstName()+" "+task.getProject().getUser().getLastName());
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
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return taskInfo;
	}

	@Override
	public Boolean updateUserInfo(UserInfo userInfo) throws TaskException {
		try {
			if(null != userInfo) {
				User user = new User();
				user.setUserId(userInfo.getUserId());
				user.setFirstName(userInfo.getFirstName());
				user.setLastName(userInfo.getLastName());
				user.setEmployeeId(userInfo.getEmpId());
				projectManagerDAOImpl.updateUser(user);
			}
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return true;
	}

	@Override
	public Boolean updateProjectInfo(ProjectInfo projectInfo) throws TaskException {
		try {
			if(null != projectInfo) {
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
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return true;
	}

	@Override
	public Boolean updateTaskInfo(TaskInfo taskInfo) throws TaskException {
		try {
		if(null != taskInfo) {
			if(taskInfo.isParentTaskEnabled()) {
				ParentTask parentTask = new ParentTask();
				parentTask.setParentId(taskInfo.getParentId());
				parentTask.setParentTask(taskInfo.getTaskName());
				Project project = projectManagerDAOImpl.getProject(taskInfo.getProjectId());
				parentTask.setProject(project);
				projectManagerDAOImpl.updateParentTask(parentTask);
			}else {
				Task task = new Task();
				task.setTaskId(taskInfo.getTaskId());
				task.setTask(taskInfo.getTaskName());
				task.setPriority(taskInfo.getPriority());
				task.setStartDate(taskInfo.getStartDate());
				task.setEndDate(taskInfo.getEndDate());
				task.setStatus(taskInfo.getStatus());
				Project project = projectManagerDAOImpl.getProject(taskInfo.getProjectId());
				task.setProject(project);
				if(taskInfo.getParentId() != 0) {
					ParentTask parentTask = projectManagerDAOImpl.getParentTask(taskInfo.getParentId());
					task.setParentTask(parentTask);
				}
				projectManagerDAOImpl.updateTask(task);
			}
		}
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return true;
	}

	@Override
	public List<ParentTaskInfo> getParentTasks(int projectId) throws TaskException {
		List<ParentTaskInfo> parentTaskInfo = null;
		try {
			List<ParentTask> parentTasks = projectManagerDAOImpl.getParentTasks(projectId);
			ParentTaskInfo info = null;
			if(null != parentTasks && parentTasks.size() > 0) {
				parentTaskInfo = new ArrayList<ParentTaskInfo>();
				for(ParentTask parentTask : parentTasks) {
					info = new ParentTaskInfo();
					info.setParentId(parentTask.getParentId());
					info.setParentTaskName(parentTask.getParentTask());
					info.setProjectId(parentTask.getProject().getProjectId());
					parentTaskInfo.add(info);
				}
			}
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return parentTaskInfo;
	}

	@Override
	public Boolean deleteUser(int userId) throws TaskException {
		Boolean deletedFlag = false;
		try {
			deletedFlag = projectManagerDAOImpl.deleteUser(userId);
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return deletedFlag;
	}
	
	@Override
	public Boolean deleteProject(int projectId) throws TaskException {
		Boolean deletedFlag = false;
		try {
			deletedFlag = projectManagerDAOImpl.deleteProject(projectId);
		}catch(Exception e) {
			throw new TaskException("1000", "Technical Error", 500);
		}
		return deletedFlag;
	}

}
