import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { DatePipe } from '@angular/common';
import { NgForm, FormControl, NgModel } from '@angular/forms';
declare var jQuery: any;

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  @ViewChild('projectForm') projectForm: NgForm;

  searchUsr: string = '';

  projectModel: any = {
    "projectId": 0,
    "projectName": "",
    "totalNoOfTasks": 0,
    "noOfTasksCompleted": 0,
    "startDate": new Date(),
    "endDate": new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
    "priority": 0,
    "userId": 0,
    "userName": ""
  };
  projectModelList = [];
  userModelList = [];
  technicalError: boolean = false;
  updateError: boolean = false;
  screenLoader: boolean = false;
  searchPjt: string = "";
  order: number = 1; // 1 asc, -1 desc;
  fieldName: string = "";
  buttonName: string = "Add";
  disableDates: boolean = false;
  dateError: boolean = false;
  minDate: Date = new Date();
  maxDate: Date = new Date(this.minDate.getTime() + (1000 * 60 * 60 * 24));
  constructor(public router: Router, private backendService: BackendService, private taskService: TaskService, private datePipe: DatePipe) { }

  ngOnInit() {
    this.disableDates = false;
    this.screenLoader = true;
    this.technicalError = false;
    this.searchUsr = '';
    this.updateError = false;
    this.projectModel = this.taskService.editProject;
    if (null == this.projectModel || undefined == this.projectModel) {
      this.projectModel = {
        "projectId": 0,
        "projectName": "",
        "totalNoOfTasks": 0,
        "noOfTasksCompleted": 0,
        "startDate": new Date(),
        "endDate": new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
        "priority": 0,
        "userId": 0,
        "userName": ""
      };
    }
    this.getProjects();
  }

  selectChkBox(event) {
    if (event) {
      this.disableDates = false;
    } else {
      this.disableDates = true;
    }
  }

  updateProject() {
    let inputParam = this.projectModel;
    inputParam.startDate = new Date(this.projectModel.startDate);
    inputParam.endDate = new Date(this.projectModel.endDate);
    if (inputParam.startDate >= inputParam.endDate) {
      this.dateError = true;
      return;
    }
    this.screenLoader = true;
    this.backendService.updateProject(this.projectModel).subscribe(
      (data: any) => {
        this.updateError = false;
        this.technicalError = false;
        this.screenLoader = false;
        this.taskService.editProject = null;
        this.buttonName = "Add";
        this.resetFields();
        this.getProjects();
      },
      (err: any) => {
        this.updateError = true;
        this.technicalError = true;
        this.screenLoader = false;
      }
    );

  }

  resetFields() {
    this.buttonName = 'Add';
    this.disableDates = false;
    this.projectModel = {
      "projectId": 0,
      "projectName": "",
      "totalNoOfTasks": 0,
      "noOfTasksCompleted": 0,
      "startDate": new Date(),
      "endDate": new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
      "priority": 0,
      "userId": 0,
      "userName": ""
    };
    this.projectForm.form.markAsPristine();
    this.projectForm.form.markAsUntouched();
    this.dateError = false;
    this.getProjects();
    return;
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

  deleteProject(projectId) {
    this.screenLoader = true;
    this.backendService.deleteProject(projectId).subscribe(
      (data: any) => {
        this.updateError = false;
        this.technicalError = false;
        this.screenLoader = false;
        this.getProjects();
      },
      (err: any) => {
        this.updateError = true;
        this.technicalError = true;
        this.screenLoader = false;
      }
    );
  }

  editProject(projectModel) {
    this.buttonName = "Update";
    this.taskService.editProject = projectModel;
    this.ngOnInit();

  }

  sortProject(prop: string) {
    this.order = this.order * (-1); // change order
    let order_val = this.order == 1 ? 'asc' : 'desc';
    this.fieldName = prop + "-" + order_val;
    return false; // do not reload
  }

  openProjectModal() {
    this.getUsers();
    this.projectModel.userName = "";
    jQuery('#projectModal').modal('show');

  }
  closeProjectModal() {
    this.projectModel.userName = "";
    jQuery('#projectModal').modal('hide');
  }

  selectUser(userObj: any) {
    jQuery('#projectModal').modal('hide');
    this.projectModel.userName = userObj.firstName + " " + userObj.lastName;
    this.projectModel.userId = userObj.userId;
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

}
