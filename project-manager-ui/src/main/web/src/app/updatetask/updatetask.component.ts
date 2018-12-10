import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { DatePipe } from '@angular/common';
import { NgForm, FormControl, NgModel } from '@angular/forms';
declare var jQuery: any;

@Component({
  selector: 'app-updatetask',
  templateUrl: './updatetask.component.html',
  styleUrls: ['./updatetask.component.css']
})
export class UpdatetaskComponent implements OnInit {

  @ViewChild('taskForm') taskForm: NgForm;

  userModelList: any = [];
  projectModelList: any = [];
  parentTaskList: any = [];
  minDate: Date = new Date();
  maxDate: Date = new Date(this.minDate.getTime() + (1000 * 60 * 60 * 24));
  taskModel: any = {
    "taskId": 0,
    "projectId": 0,
    "projectName": "",
    "taskName": "",
    "parentTaskEnabled": false,
    "priority": "0",
    "parentId": 0,
    "parentTaskName": "",
    "startDate": new Date(),
    "endDate": new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
    "userName": "",
    "userId": 0,
    "status": 'INPROGRESS'
  };
  taskModelList = [];
  technicalError: boolean = false;
  updateError: boolean = false;
  screenLoader: boolean = false;
  buttonName: string = 'Add Task';
  searchUsr: string = '';
  dateError: boolean = false;
  constructor(public router: Router, private backendService: BackendService, private taskService: TaskService, private datePipe: DatePipe) { }

  ngOnInit() {
    // this.screenLoader = true;
    this.technicalError = false;
    this.updateError = false;
    this.taskModel = this.taskService.editTask;

    if (null == this.taskModel || undefined == this.taskModel) {
      this.taskModel = {
        "taskId": 0,
        "projectId": 0,
        "projectName": "",
        "taskName": "",
        "parentTaskEnabled": false,
        "priority": "0",
        "parentId": 0,
        "parentTaskName": "",
        "startDate": new Date(),
        "endDate": new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
        "userName": "",
        "userId": 0,
        "status": 'INPROGRESS'
      };
    } else {
      this.buttonName = "Update Task";
    }
  }

  disableField(event) {
    if (event) {
      this.taskModel.parentTaskEnabled = true;
    } else {
      this.taskModel.parentTaskEnabled = false;
    }
  }

  updateTask() {
    let inputParam = this.taskModel;
    inputParam.startDate = new Date(this.taskModel.startDate);
    inputParam.endDate = new Date(this.taskModel.endDate);
    if (inputParam.startDate >= inputParam.endDate) {
      this.dateError = true;
      return;
    }
    this.screenLoader = true;
    this.backendService.updateTask(this.taskModel).subscribe(
      (data: any) => {
        this.updateError = false;
        this.technicalError = false;
        this.screenLoader = false;
        this.buttonName = "Update Task";
        this.router.navigate(['viewTask']);
      },
      (err: any) => {
        this.updateError = true;
        this.technicalError = true;
        this.screenLoader = false;
      }
    );

  }

  resetFields() {
    this.taskModel = {
      "taskId": 0,
      "projectId": 0,
      "projectName": "",
      "taskName": "",
      "parentTaskEnabled": false,
      "priority": "0",
      "parentId": 0,
      "parentTaskName": "",
      "startDate": new Date(),
      "endDate": new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
      "userName": "",
      "userId": 0,
      "status": 'INPROGRESS'
    };
    this.taskForm.form.markAsPristine();
    this.taskForm.form.markAsUntouched();
    this.dateError = false;
    this.buttonName = 'Add Task';
    return;
  }

  cancelTask() {
    this.router.navigate(['viewTask']);
  }

  openUserModal() {
    this.getUsers();
    this.taskModel.userName = "";
    jQuery('#userModal').modal('show');

  }
  closeUserModal() {
    this.taskModel.userName = "";
    jQuery('#userModal').modal('hide');
  }

  selectUser(userObj: any) {
    jQuery('#userModal').modal('hide');
    this.taskModel.userName = userObj.firstName + " " + userObj.lastName;
    this.taskModel.userId = userObj.userId;
  }

  getUsers() {
    var inputParam = {
    };

    this.backendService.getUsers(inputParam).subscribe(
      (data: any) => {
        this.technicalError = false;
        this.userModelList = data;
        this.screenLoader = false;
      },
      (err: any) => {
        this.technicalError = true;
        this.screenLoader = false;
      }
    );
  }

  openProjectModal() {
    this.getProjects();
    this.taskModel.projectName = "";
    jQuery('#projectModal').modal('show');

  }
  closeProjectModal() {
    this.taskModel.projectName = "";
    jQuery('#projectModal').modal('hide');
  }

  selectProject(projectObj: any) {
    this.taskModel.projectName = projectObj.projectName;
    this.taskModel.projectId = projectObj.projectId;
    jQuery('#projectModal').modal('hide');

  }

  getProjects() {
    var inputParam = {
    };

    this.backendService.getProjects(inputParam).subscribe(
      (data: any) => {
        this.technicalError = false;
        this.projectModelList = data;
        this.screenLoader = false;
      },
      (err: any) => {
        this.technicalError = true;
        this.screenLoader = false;
      }
    );

  }

  openParentTaskModal() {
    this.getParentTasks();
    this.taskModel.parentTaskName = "";
    jQuery('#parentTaskModal').modal('show');

  }
  closeParentTaskModal() {
    this.taskModel.parentTaskName = "";
    jQuery('#parentTaskModal').modal('hide');
  }

  selectParentTask(parentTaskObj: any) {
    jQuery('#parentTaskModal').modal('hide');
    this.taskModel.parentTaskName = parentTaskObj.parentTaskName;
    this.taskModel.parentId = parentTaskObj.parentId;
  }

  getParentTasks() {

    this.backendService.getParentTasks(this.taskModel.projectId).subscribe(
      (data: any) => {
        this.technicalError = false;
        this.parentTaskList = data;
        this.screenLoader = false;
      },
      (err: any) => {
        this.technicalError = true;
        this.screenLoader = false;
      }
    );

  }

}
