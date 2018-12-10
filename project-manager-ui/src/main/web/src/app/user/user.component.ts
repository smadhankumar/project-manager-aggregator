import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { NgForm, FormControl, NgModel } from '@angular/forms';
declare var jQuery: any;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  @ViewChild('userForm') userForm: NgForm;

  userModel: any = {};
  userModelList = [];
  technicalError: boolean = false;
  updateError: boolean = false;
  screenLoader: boolean = false;
  searchUsr: string = "";
  order: number = 1; // 1 asc, -1 desc;
  fieldName: string = "";
  buttonName: string = "Add";
  constructor(public router: Router, private backendService: BackendService, private taskService: TaskService) { }

  ngOnInit() {
    this.screenLoader = true;
    this.technicalError = false;
    this.updateError = false;
    this.userModel = this.taskService.editUser;
    if (null == this.userModel || undefined == this.userModel) {
      this.userModel = {
        "userId": 0,
        "firstName": "",
        "lastName": "",
        "empId": ""
      };
    }
    this.getUsers();
  }

  updateUser() {
    this.screenLoader = true;
    this.backendService.updateUser(this.userModel).subscribe(
      (data: any) => {
        this.updateError = false;
        this.technicalError = false;
        this.screenLoader = false;
        this.taskService.editUser = null;
        this.buttonName = "Add";
        this.resetFields();
      },
      (err: any) => {
        this.updateError = true;
        this.technicalError = true;
        this.screenLoader = false;
      }
    );

  }

  resetFields() {
    this.userModel = {
      "userId": 0,
      "firstName": "",
      "lastName": "",
      "empId": ""
    };
    this.userForm.form.markAsPristine();
    this.userForm.form.markAsUntouched();

    this.buttonName = "Add";
    this.taskService.editUser = null;
    this.getUsers();
    return;
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

  deleteUser(userId) {
    this.screenLoader = true;
    this.backendService.deleteUser(userId).subscribe(
      (data: any) => {
        this.updateError = false;
        this.technicalError = false;
        this.screenLoader = false;
        this.getUsers();
      },
      (err: any) => {
        this.updateError = true;
        this.technicalError = true;
        this.screenLoader = false;
      }
    );
  }

  editUser(userId) {
    this.buttonName = "Update";
    if (null !== this.userModelList && this.userModelList.length > 0) {
      for (let userModel of this.userModelList) {
        if (userModel.userId == userId) {
          this.taskService.editUser = userModel;
          this.ngOnInit();
        }
      }

    }
  }

  sortUser(prop: string) {
    this.order = this.order * (-1); // change order
    let order_val = this.order == 1 ? 'asc' : 'desc';
    this.fieldName = prop + "-" + order_val;
    return false; // do not reload
  }

}
