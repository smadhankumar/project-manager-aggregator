package com.fsd.casestudy.controller;

import java.util.List;

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

@RestController
public class ProjectManagerController {
	
	@Autowired
	private ProjectManagerService projectManagerServiceImpl;
	
	@GetMapping(value="/getProjectInfo", headers="Accept=application/json")
	public List<ProjectInfo> getProjectInfo() throws TaskException{
		return projectManagerServiceImpl.getProjectInfo();
	}
	
	@GetMapping(value="/getUserInfo", headers="Accept=application/json")
	public List<UserInfo> getUserInfo() throws TaskException{
		return projectManagerServiceImpl.getUserInfo();
	}
	
	@GetMapping(value="/getTaskInfo/{projectid}", headers="Accept=application/json")
	public List<TaskInfo> getTaskInfo(@PathVariable("projectid") int projectId) throws TaskException{
		return projectManagerServiceImpl.getTaskInfo(projectId);
	}
	
	@PostMapping(value="/updateUser",headers="Accept=application/json")
    public Boolean addOrUpdUser(@RequestBody UserInfo user) throws TaskException{
        return projectManagerServiceImpl.updateUserInfo(user);
    }
	
	@PostMapping(value="/updateProject",headers="Accept=application/json")
    public Boolean addOrUpdProject(@RequestBody ProjectInfo project) throws TaskException{
        return projectManagerServiceImpl.updateProjectInfo(project);
    }
	
	@PostMapping(value="/updateTask",headers="Accept=application/json")
    public Boolean addOrUpdTask(@RequestBody TaskInfo task) throws TaskException{
        return projectManagerServiceImpl.updateTaskInfo(task);
    }
	
	@GetMapping(value="/getParentTaskInfo/{projectid}", headers="Accept=application/json")
	public List<ParentTaskInfo> getParentTaskInfo(@PathVariable("projectid") int projectId) throws TaskException{
		return projectManagerServiceImpl.getParentTasks(projectId);
	}
	
	@DeleteMapping(value="/deleteUser/{id}", headers ="Accept=application/json")
    public Boolean deleteUser(@PathVariable("id") int userId) throws TaskException{
		return projectManagerServiceImpl.deleteUser(userId);
    }
	
	@DeleteMapping(value="/deleteProject/{id}", headers ="Accept=application/json")
    public Boolean deleteProject(@PathVariable("id") int projectId) throws TaskException{
		return projectManagerServiceImpl.deleteProject(projectId);
    }
	
}
