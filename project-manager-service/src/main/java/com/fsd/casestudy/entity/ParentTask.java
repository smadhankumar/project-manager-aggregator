package com.fsd.casestudy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class is an entity for Parent Task table
 * 
 * @author 463657
 *
 */
@Entity
@Table(name = "parent_task")
public class ParentTask {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "parent_id")
	private int parentId;
	@Column(name = "parent_task")
	private String parentTask;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;

	public int getParentId() {
		return parentId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

}
