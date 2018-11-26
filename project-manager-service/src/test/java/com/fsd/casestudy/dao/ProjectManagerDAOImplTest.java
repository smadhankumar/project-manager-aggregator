package com.fsd.casestudy.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fsd.casestudy.entity.ParentTask;
import com.fsd.casestudy.entity.Project;
import com.fsd.casestudy.entity.Task;
import com.fsd.casestudy.entity.User;
import com.fsd.casestudy.exception.TaskException;
import com.fsd.casestudy.repository.ParentTaskRespository;
import com.fsd.casestudy.repository.ProjectRepository;
import com.fsd.casestudy.repository.TaskRepository;
import com.fsd.casestudy.repository.UserRepository;

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

}
