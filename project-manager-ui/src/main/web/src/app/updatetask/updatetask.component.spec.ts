import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatetaskComponent } from './updatetask.component';
import { FormsModule } from '@angular/forms';
import { ScreenFreezeComponent } from '../screenfreeze/screenfreeze.component';
import { TaskFilterPipe } from '../shared/task-filter.pipe';
import { AppRoutingModule } from '../app-routing.module';
import { ViewtaskComponent } from '../viewtask/viewtask.component';
import { UserComponent } from '../user/user.component';
import { ProjectComponent } from '../project/project.component';
import { SortPipe } from '../shared/sort.pipe';
import { APP_BASE_HREF } from '@angular/common';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { BackendServiceMock } from '../shared/backend-service-mock';
import { HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';

describe('UpdatetaskComponent', () => {
  let component: UpdatetaskComponent;
  let fixture: ComponentFixture<UpdatetaskComponent>;
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
      imports: [
        FormsModule,
        AppRoutingModule,
        HttpClientModule
      ],
      providers: [{ provide: APP_BASE_HREF, useValue: '/' },
        TaskService,
      { provide: BackendService, useClass: BackendServiceMock },
        DatePipe
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    service = TestBed.get(BackendService);
    fixture = TestBed.createComponent(UpdatetaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should disableField true', () => {
    expect(component.disableField(true)).toBeDefined;
  });
  it('should disableField false', () => {
    expect(component.disableField(false)).toBeDefined;
  });
  it('should call updateTask', () => {
    component.taskModel = {

    };
    expect(component.updateTask()).toBeDefined;
  });
  it('should call resetFields', () => {
    expect(component.resetFields()).toBeDefined;
  });
  it('should call cancelTask', () => {
    expect(component.cancelTask()).toBeDefined;
  });
  it('should call openUserModal', () => {
    expect(component.openUserModal()).toBeDefined;
  });
  it('should call closeUserModal', () => {
    expect(component.closeUserModal()).toBeDefined;
  });
  it('should call selectUser', () => {
    let userObj = {

    };
    expect(component.selectUser(userObj)).toBeDefined;
  });
  it('should call getUsers', () => {
    expect(component.getUsers()).toBeDefined;
  });
  it('should call openProjectModal', () => {
    expect(component.openProjectModal()).toBeDefined;
  });
  it('should call closeProjectModal', () => {
    expect(component.closeProjectModal()).toBeDefined;
  });
  it('should call selectProject', () => {
    let projectObj = {

    };
    expect(component.selectProject(projectObj)).toBeDefined;
  });
  it('should call getProjects', () => {
    expect(component.getProjects()).toBeDefined;
  });
  it('should call openParentTaskModal', () => {
    expect(component.openParentTaskModal()).toBeDefined;
  });
  it('should call closeParentTaskModal', () => {
    expect(component.closeParentTaskModal()).toBeDefined;
  });
  it('should call selectParentTask', () => {
    let parentTask = {

    };
    expect(component.selectParentTask(parentTask)).toBeDefined;
  });
  it('should call getParentTasks', () => {
    expect(component.getParentTasks()).toBeDefined;
  });
});
