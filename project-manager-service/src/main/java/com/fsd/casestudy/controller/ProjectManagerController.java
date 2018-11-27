package com.fsd.casestudy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.casestudy.exception.TaskException;
import com.fsd.casestudy.model.ParentTaskInfo;
import com.fsd.casestudy.model.ProjectInfo;
import com.fsd.casestudy.model.TaskInfo;
import com.fsd.casestudy.model.UserInfo;
import com.fsd.casestudy.service.ProjectManagerService;

/**
 * This class acts as a controller for exposing different endpoints of Project
 * Manager application
 * 
 * @author 463657
 *
 */
@RestController
public class ProjectManagerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagerController.class);

	@Autowired
	private ProjectManagerService projectManagerServiceImpl;

	@GetMapping(value = "/getProjectInfo", headers = "Accept=application/json")
	public List<ProjectInfo> getProjectInfo() throws TaskException {
		LOGGER.debug("Inside getProjectInfo() of ProjectManagerController"); 
		return projectManagerServiceImpl.getProjectInfo();
	}

	@GetMapping(value = "/getUserInfo", headers = "Accept=application/json")
	public List<UserInfo> getUserInfo() throws TaskException {
		LOGGER.debug("Inside getUserInfo() of ProjectManagerController"); 
		return projectManagerServiceImpl.getUserInfo();
	}

	@GetMapping(value = "/getTaskInfo/{projectid}", headers = "Accept=application/json")
	public List<TaskInfo> getTaskInfo(@PathVariable("projectid") int projectId) throws TaskException {
		LOGGER.debug("Inside getTaskInfo() of ProjectManagerController"); 
		return projectManagerServiceImpl.getTaskInfo(projectId);
	}

	@PostMapping(value = "/updateUser", headers = "Accept=application/json")
	public Boolean addOrUpdUser(@RequestBody UserInfo user) throws TaskException {
		LOGGER.debug("Inside addOrUpdUser() of ProjectManagerController"); 
		return projectManagerServiceImpl.updateUserInfo(user);
	}

	@PostMapping(value = "/updateProject", headers = "Accept=application/json")
	public Boolean addOrUpdProject(@RequestBody ProjectInfo project) throws TaskException {
		LOGGER.debug("Inside addOrUpdProject() of ProjectManagerController"); 
		return projectManagerServiceImpl.updateProjectInfo(project);
	}

	@PostMapping(value = "/updateTask", headers = "Accept=application/json")
	public Boolean addOrUpdTask(@RequestBody TaskInfo task) throws TaskException {
		LOGGER.debug("Inside addOrUpdTask() of ProjectManagerController"); 
		return projectManagerServiceImpl.updateTaskInfo(task);
	}

	@GetMapping(value = "/getParentTaskInfo/{projectid}", headers = "Accept=application/json")
	public List<ParentTaskInfo> getParentTaskInfo(@PathVariable("projectid") int projectId) throws TaskException {
		LOGGER.debug("Inside getParentTaskInfo() of ProjectManagerController"); 
		return projectManagerServiceImpl.getParentTasks(projectId);
	}

	@DeleteMapping(value = "/deleteUser/{id}", headers = "Accept=application/json")
	public Boolean deleteUser(@PathVariable("id") int userId) throws TaskException {
		LOGGER.debug("Inside deleteUser() of ProjectManagerController"); 
		return projectManagerServiceImpl.deleteUser(userId);
	}

	@DeleteMapping(value = "/deleteProject/{id}", headers = "Accept=application/json")
	public Boolean deleteProject(@PathVariable("id") int projectId) throws TaskException {
		LOGGER.debug("Inside deleteProject() of ProjectManagerController"); 
		return projectManagerServiceImpl.deleteProject(projectId);
	}

}
