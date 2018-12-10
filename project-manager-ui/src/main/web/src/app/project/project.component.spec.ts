import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserComponent } from '../user/user.component';
import { UpdatetaskComponent } from '../updatetask/updatetask.component';
import { FormsModule } from '@angular/forms';
import { ScreenFreezeComponent } from '../screenfreeze/screenfreeze.component';
import { TaskFilterPipe } from '../shared/task-filter.pipe';
import { AppRoutingModule } from '../app-routing.module';
import { ViewtaskComponent } from '../viewtask/viewtask.component';
import { ProjectComponent } from './project.component';
import { SortPipe } from '../shared/sort.pipe';
import {APP_BASE_HREF} from '@angular/common';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { BackendServiceMock } from '../shared/backend-service-mock';
import { HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { of } from 'rxjs';

describe('ProjectComponent', () => {
  let component: ProjectComponent;
  let fixture: ComponentFixture<ProjectComponent>;
  let service: BackendService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        UpdatetaskComponent,
        ScreenFreezeComponent,
        TaskFilterPipe,
        ViewtaskComponent,
        UserComponent,
        ProjectComponent,
        SortPipe
      ],
      imports:[
        FormsModule,
        AppRoutingModule,
        HttpClientModule
      ],
      providers: [{provide: APP_BASE_HREF, useValue : '/' },
      TaskService,
      { provide: BackendService, useClass: BackendServiceMock },
      DatePipe
        ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    service = TestBed.get(BackendService);
    spyOn(service, 'getProjects').and.returnValue(of([]));
    fixture = TestBed.createComponent(ProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should call selectChkBox true', () => {
    expect(component.selectChkBox(true)).toBeDefined;
  });
  it('should call selectChkBox false', () => {
    expect(component.selectChkBox(false)).toBeDefined;
  });
  it('should call updateProject', () => {
    component.projectModel = {
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
    expect(component.updateProject()).toBeDefined;
  });
  it('should call resetFields', () => {
    expect(component.resetFields()).toBeDefined;
  });
  it('should call getProjects', () => {
    expect(component.getProjects()).toBeDefined;
  });
  it('should call deleteProject', () => {
    expect(component.deleteProject(1)).toBeDefined;
  });
  it('should call editProject', () => {
    let projectModel = {
      "projectId": 10,
      "projectName": "Project 1",
      "startDate": new Date(),
      "endDate": new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
      "priority": 10,
      "userId": 1,
      "userName": "Madhan"
    };
    expect(component.editProject(projectModel)).toBeDefined;
  });
  it('should call sortProject', () => {
    expect(component.sortProject('String')).toBe(false);
  });
  it('should call openProjectModal', () => {
    expect(component.openProjectModal()).toBeDefined;
  });
  it('should call closeProjectModal', () => {
    expect(component.closeProjectModal()).toBeDefined;
  });
  it('should call selectUser', () => {
    let userObj = {

    };
    expect(component.selectUser(userObj)).toBeDefined;
  });
  it('should call getUsers', () => {
    expect(component.getUsers()).toBeDefined;
  });
});
