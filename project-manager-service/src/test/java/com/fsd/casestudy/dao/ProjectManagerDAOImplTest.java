package com.fsd.casestudy.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.casestudy.entity.ParentTask;
import com.fsd.casestudy.entity.Project;
import com.fsd.casestudy.entity.Task;
import com.fsd.casestudy.entity.User;
import com.fsd.casestudy.exception.TaskException;
import com.fsd.casestudy.repository.ParentTaskRespository;
import com.fsd.casestudy.repository.ProjectRepository;
import com.fsd.casestudy.repository.TaskRepository;
import com.fsd.casestudy.repository.UserRepository;

/**
 * Test class for unit testing ProjectManager DAO
 * 
 * @author 463657
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectManagerDAOImplTest {

	@InjectMocks
	private ProjectManagerDAOImpl projectManagerDAOImpl;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ParentTaskRespository parentTaskRespository;

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private ProjectRepository projectRepository;

	@Test
	public void deleteTask_Valid() throws TaskException {
		assertTrue(projectManagerDAOImpl.deleteTask(1));
	}

	@Test
	public void getProjects_Valid() throws TaskException {
		when(projectRepository.findAll()).thenReturn(Arrays.asList(new Project()));
		assertNotNull(projectManagerDAOImpl.getProjectDetails());
	}

	@Test
	public void getUsers_Valid() throws TaskException {
		when(userRepository.findAll()).thenReturn(Arrays.asList(new User()));
		assertNotNull(projectManagerDAOImpl.getUserDetails());
	}

	@Test
	public void getParentTasks_Valid() throws TaskException {
		when(parentTaskRespository.getParentTasks(Mockito.anyInt())).thenReturn(Arrays.asList(new ParentTask()));
		assertNotNull(projectManagerDAOImpl.getParentTasks(1));
	}

	@Test
	public void getTasks_Valid() throws TaskException {
		when(taskRepository.findAll()).thenReturn(Arrays.asList(new Task()));
		assertNotNull(projectManagerDAOImpl.getTaskDetails());
	}

	@Test
	public void getTaskForProject_Valid() throws TaskException {
		when(taskRepository.getTaskDetails(Mockito.anyInt())).thenReturn(Arrays.asList(new Task()));
		assertNotNull(projectManagerDAOImpl.getTaskDetailsForProject(1));
	}

	@Test
	public void updateUser_Valid() throws TaskException {
		User user = new User();
		when(userRepository.save(user)).thenReturn(user);
		assertNotNull(projectManagerDAOImpl.updateUser(user));
	}

	@Test
	public void updateProject_Valid() throws TaskException {
		Project project = new Project();
		when(projectRepository.save(project)).thenReturn(project);
		assertNotNull(projectManagerDAOImpl.updateProject(project));
	}

	@Test
	public void updateParentTask_Valid() throws TaskException {
		ParentTask parentTask = new ParentTask();
		when(parentTaskRespository.save(parentTask)).thenReturn(parentTask);
		assertNotNull(projectManagerDAOImpl.updateParentTask(parentTask));
	}

	@Test
	public void updateTask_Valid() throws TaskException {
		Task task = new Task();
		when(taskRepository.save(task)).thenReturn(task);
		assertNotNull(projectManagerDAOImpl.updateTask(task));
	}

	@Test
	public void getUser_Valid() throws TaskException {
		User user = new User();
		when(userRepository.findByUserId(Mockito.anyInt())).thenReturn(user);
		assertNotNull(projectManagerDAOImpl.getUser(1));
	}

	@Test
	public void getProject_Valid() throws TaskException {
		Project project = new Project();
		when(projectRepository.findByProjectId(Mockito.anyInt())).thenReturn(project);
		assertNotNull(projectManagerDAOImpl.getProject(1));
	}

	@Test
	public void getParentTask_Valid() throws TaskException {
		ParentTask parentTask = new ParentTask();
		when(parentTaskRespository.findByParentId(Mockito.anyInt())).thenReturn(parentTask);
		assertNotNull(projectManagerDAOImpl.getParentTask(1));
	}

	@Test
	public void deleteParentTaskForProjectId_Valid() throws TaskException {

		doNothing().when(parentTaskRespository).deleteParentTaskForProject(Mockito.anyInt());
		assertTrue(projectManagerDAOImpl.deleteParentTaskForProjectId(1));

	}

	@Test
	public void deleteTaskForProjectId_Valid() throws TaskException {

		doNothing().when(taskRepository).deleteTaskForProject(Mockito.anyInt());
		assertTrue(projectManagerDAOImpl.deleteTaskForProjectId(1));

	}

	@Test
	public void deleteProjectForUser_Valid()
			throws TaskException, JsonParseException, JsonMappingException, IOException {
		TypeReference<List<Project>> mapObj = new TypeReference<List<Project>>() {
		};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("projectlist.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		List<Project> projects = mapper.readValue(file, mapObj);

		when(projectRepository.getProjectsForUser(Mockito.anyInt())).thenReturn(projects);
		doNothing().when(taskRepository).deleteTaskForProject(Mockito.anyInt());
		doNothing().when(parentTaskRespository).deleteParentTaskForProject(Mockito.anyInt());
		doNothing().when(projectRepository).deleteProjectForUser(Mockito.anyInt());
		assertTrue(projectManagerDAOImpl.deleteProjectForUser(1));

	}

	@Test
	public void deleteUser_Valid() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<List<Project>> mapObj = new TypeReference<List<Project>>() {
		};
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("projectlist.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		List<Project> projects = mapper.readValue(file, mapObj);

		when(projectRepository.getProjectsForUser(Mockito.anyInt())).thenReturn(projects);
		doNothing().when(taskRepository).deleteTaskForProject(Mockito.anyInt());
		doNothing().when(parentTaskRespository).deleteParentTaskForProject(Mockito.anyInt());
		doNothing().when(projectRepository).deleteProjectForUser(Mockito.anyInt());
		doNothing().when(userRepository).deleteById(Mockito.anyInt());
		assertTrue(projectManagerDAOImpl.deleteUser(1));

	}

	@Test
	public void deleteProject_Valid() throws JsonParseException, JsonMappingException, IOException {
		doNothing().when(taskRepository).deleteTaskForProject(Mockito.anyInt());
		doNothing().when(parentTaskRespository).deleteParentTaskForProject(Mockito.anyInt());
		doNothing().when(projectRepository).deleteById(Mockito.anyInt());

		assertTrue(projectManagerDAOImpl.deleteProject(1));

	}

}
