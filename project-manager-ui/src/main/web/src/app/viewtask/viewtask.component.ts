import { Component, OnInit } from '@angular/core';
import { TaskFilterPipe } from '../shared/task-filter.pipe';
import { Router } from '@angular/router';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { DatePipe } from '@angular/common';
declare var jQuery: any;

@Component({
  selector: 'app-viewtask',
  templateUrl: './viewtask.component.html',
  styleUrls: ['./viewtask.component.css']
})
export class ViewtaskComponent implements OnInit {

  taskModelList = [];
  technicalError: boolean = false;
  updateError: boolean = false;
  screenLoader: boolean = false;
  projectModelList: any = [];
  projectName: string = '';
  projectId: number = 0;
  order: number = 1; // 1 asc, -1 desc;
  fieldName: string = "";
  searchUsr: string = '';
  constructor(public router: Router, private taskService: TaskService, private backendService: BackendService,
    private datePipe: DatePipe) { }

  ngOnInit() {
    this.screenLoader = true;
    this.technicalError = false;
    this.taskModelList = [];
    this.taskService.editTask = null;
    this.getTasks();
  }

  editTask(taskModel: any) {
    this.taskService.editTask = taskModel;
    this.router.navigate(['/updateTask']);
  }

  endTask(taskModel: any) {
    this.screenLoader = true;
    taskModel.endDate = new Date();
    taskModel.status = 'COMPLETED';
    this.backendService.updateTask(taskModel).subscribe(
      (data: any) => {
        this.getTasks();
        this.updateError = false;
        this.router.navigate(['viewTask']);

      },
      (err: any) => {
        this.technicalError = true;
        this.updateError = true;
      }
    );
    this.screenLoader = false;
  }

  getTasks() {
    var inputParam = {
    };

    this.backendService.getTasks(0).subscribe(
      (data: any) => {
        this.technicalError = false;

        this.taskModelList = data;
        if (null != this.taskModelList && this.taskModelList.length > 0) {
          for (let task of this.taskModelList) {
            if (task.status == 'COMPLETED') {
              task.disabled = true;
            }
          }
        }
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
    this.projectName = "";
    jQuery('#projectModal').modal('show');

  }
  closeProjectModal() {
    this.projectName = "";
    jQuery('#projectModal').modal('hide');
  }

  selectProject(projectObj: any) {
    jQuery('#projectModal').modal('hide');
    this.projectName = projectObj.projectName;
    this.projectId = projectObj.projectId;
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

  sortTask(prop: string) {
    this.order = this.order * (-1); // change order
    let order_val = this.order == 1 ? 'asc' : 'desc';
    this.fieldName = prop + "-" + order_val;
    return false; // do not reload
  }
}
