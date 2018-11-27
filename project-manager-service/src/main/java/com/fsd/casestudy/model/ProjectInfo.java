package com.fsd.casestudy.model;

/**
 * Project Model Object
 * 
 * @author 463657
 *
 */
import java.util.Date;

public class ProjectInfo {

	private int projectId;
	private String projectName;
	private int totalNoOfTasks;
	private int noOfTasksCompleted;
	private Date startDate;
	private Date endDate;
	private int priority;
	private int userId;
	private String userName;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getTotalNoOfTasks() {
		return totalNoOfTasks;
	}

	public void setTotalNoOfTasks(int totalNoOfTasks) {
		this.totalNoOfTasks = totalNoOfTasks;
	}

	public int getNoOfTasksCompleted() {
		return noOfTasksCompleted;
	}

	public void setNoOfTasksCompleted(int noOfTasksCompleted) {
		this.noOfTasksCompleted = noOfTasksCompleted;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
