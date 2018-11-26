package com.fsd.casestudy.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.casestudy.model.ParentTaskInfo;
import com.fsd.casestudy.model.ProjectInfo;
import com.fsd.casestudy.model.TaskInfo;
import com.fsd.casestudy.model.UserInfo;
import com.fsd.casestudy.service.ProjectManagerService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectManagerController.class)
public class ProjectManagerControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ProjectManagerService projectManagerServiceImpl;

	@Test
	public void getProjectInfo_thenReturnJsonArray() throws Exception {

		ProjectInfo projectInfo = getProjectInfo();

		List<ProjectInfo> projects = Arrays.asList(projectInfo);

		given(projectManagerServiceImpl.getProjectInfo()).willReturn(projects);

		mvc.perform(get("/getProjectInfo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].projectName", is(projectInfo.getProjectName())));
	}
	
	@Test
	public void getUserInfo_thenReturnJsonArray() throws Exception {

		UserInfo userInfo = getUserInfo();

		List<UserInfo> users = Arrays.asList(userInfo);

		given(projectManagerServiceImpl.getUserInfo()).willReturn(users);

		mvc.perform(get("/getUserInfo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].userId", is(userInfo.getUserId())));
	}
	
	@Test
	public void getTaskInfo_thenReturnJsonArray() throws Exception {

		TaskInfo taskInfo = getTaskInfo();

		List<TaskInfo> tasks = Arrays.asList(taskInfo);

		given(projectManagerServiceImpl.getTaskInfo(1)).willReturn(tasks);

		mvc.perform(get("/getTaskInfo/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].taskName", is(taskInfo.getTaskName())));
		
	}
	
	@Test
	public void getParentTaskInfo_thenReturnJsonArray() throws Exception {

		ParentTaskInfo taskInfo = getParentTaskInfo();

		List<ParentTaskInfo> tasks = Arrays.asList(taskInfo);

		given(projectManagerServiceImpl.getParentTasks(1)).willReturn(tasks);

		mvc.perform(get("/getParentTaskInfo/{projectId}",1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].parentTaskName", is(taskInfo.getParentTaskName())));
		
	}
	
	@Test
	public void getParentTaskInfo_InvalidArgument() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/getParentTaskInfo/f").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void addOrUpdUser_updateUser() throws Exception {
		UserInfo userInfo = getUserInfo();
		userInfo.setUserId(0);
		given(projectManagerServiceImpl.updateUserInfo(userInfo)).willReturn(true);
		ObjectMapper mapper = new ObjectMapper();
	
		mvc.perform(post("/updateUser").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userInfo)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
  
	}
	
	@Test
	public void addOrUpdUser_addUser() throws Exception {
		UserInfo userInfo = getUserInfo_Add();

		given(projectManagerServiceImpl.updateUserInfo(userInfo)).willReturn(true);
		ObjectMapper mapper = new ObjectMapper();
	
		mvc.perform(post("/updateUser").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userInfo)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
  
	}
	
	@Test
	public void updateProject_editProject() throws Exception {
		ProjectInfo projectInfo = getProjectInfo();

		given(projectManagerServiceImpl.updateProjectInfo(projectInfo)).willReturn(true);
		ObjectMapper mapper = new ObjectMapper();
	
		mvc.perform(post("/updateProject").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(projectInfo)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
  
	}
	
	@Test
	public void updateProject_addProject() throws Exception {
		ProjectInfo projectInfo = getProjectInfo_Add();
		given(projectManagerServiceImpl.updateProjectInfo(projectInfo)).willReturn(true);
		ObjectMapper mapper = new ObjectMapper();
	
		mvc.perform(post("/updateProject").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(projectInfo)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
  
	}
	
	@Test
	public void updateTask_editTask() throws Exception {
		TaskInfo taskInfo = getTaskInfo();

		given(projectManagerServiceImpl.updateTaskInfo(taskInfo)).willReturn(true);
		ObjectMapper mapper = new ObjectMapper();
	
		mvc.perform(post("/updateTask").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(taskInfo)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
  
	}
	
	@Test
	public void updateTask_addTask() throws Exception {
		TaskInfo taskInfo = getTaskInfo_Add();
		given(projectManagerServiceImpl.updateTaskInfo(taskInfo)).willReturn(true);
		ObjectMapper mapper = new ObjectMapper();
	
		mvc.perform(post("/updateTask").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(taskInfo)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
  
	}
	
	@Test
	public void updateTask_suspendTask() throws Exception {
		TaskInfo taskInfo = getTaskInfo_Suspend();
		given(projectManagerServiceImpl.updateTaskInfo(taskInfo)).willReturn(true);
		ObjectMapper mapper = new ObjectMapper();
	
		mvc.perform(post("/updateProject").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(taskInfo)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
  
	}
	
	@Test
	public void deleteProject_Success() throws Exception {

		given(projectManagerServiceImpl.deleteProject(1)).willReturn(true);

		mvc.perform(delete("/deleteProject/{id}",1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
	}
	
	@Test
	public void deleteUser_Success() throws Exception {

		given(projectManagerServiceImpl.deleteUser(1)).willReturn(true);

		mvc.perform(delete("/deleteUser/{id}",1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
	}
	
	private ProjectInfo getProjectInfo() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<ProjectInfo> mapObj = new TypeReference<ProjectInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("projectinfo.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		ProjectInfo projectInfo = mapper.readValue(file, mapObj);
		return projectInfo;
	}
	
	private ProjectInfo getProjectInfo_Add() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<ProjectInfo> mapObj = new TypeReference<ProjectInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("projectinfo_add.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		ProjectInfo projectInfo = mapper.readValue(file, mapObj);
		return projectInfo;
	}
	
	private UserInfo getUserInfo() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<UserInfo> mapObj = new TypeReference<UserInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("user.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		UserInfo userInfo = mapper.readValue(file, mapObj);
		return userInfo;
	}
	
	private UserInfo getUserInfo_Add() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<UserInfo> mapObj = new TypeReference<UserInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("user_add.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		UserInfo userInfo = mapper.readValue(file, mapObj);
		return userInfo;
	}
	
	private TaskInfo getTaskInfo() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<TaskInfo> mapObj = new TypeReference<TaskInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("taskinfo.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		TaskInfo taskInfo = mapper.readValue(file, mapObj);
		return taskInfo;
	}
	
	private TaskInfo getTaskInfo_Add() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<TaskInfo> mapObj = new TypeReference<TaskInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("taskinfo_add.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		TaskInfo taskInfo = mapper.readValue(file, mapObj);
		return taskInfo;
	}
	
	private TaskInfo getTaskInfo_Suspend() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<TaskInfo> mapObj = new TypeReference<TaskInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("taskinfo_suspend.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		TaskInfo taskInfo = mapper.readValue(file, mapObj);
		return taskInfo;
	}
	
	private ParentTaskInfo getParentTaskInfo() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<ParentTaskInfo> mapObj = new TypeReference<ParentTaskInfo>() {};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("parenttask.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		ParentTaskInfo parentTaskInfo = mapper.readValue(file, mapObj);
		return parentTaskInfo;
		
	}

}
