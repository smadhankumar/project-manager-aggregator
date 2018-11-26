package com.fsd.casestudy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="task")
public class Task {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_id")
	private int taskId;
	@OneToOne
	@JoinColumn(name="parent_id")
	private ParentTask parentTask;
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
	@Column(name="task")
	private String task;
	@Column(name="start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;
	@Column(name="priority")
	private int priority;
	@Column(name="status")
	private String status;
	
	public ParentTask getParentTask() {
		return parentTask;
	}
	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
