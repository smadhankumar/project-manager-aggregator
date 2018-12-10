import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserComponent } from '../user/user.component';
import { UpdatetaskComponent } from '../updatetask/updatetask.component';
import { FormsModule } from '@angular/forms';
import { ScreenFreezeComponent } from '../screenfreeze/screenfreeze.component';
import { TaskFilterPipe } from '../shared/task-filter.pipe';
import { AppRoutingModule } from '../app-routing.module';
import { ViewtaskComponent } from './viewtask.component';
import { ProjectComponent } from '../project/project.component';
import { SortPipe } from '../shared/sort.pipe';
import {APP_BASE_HREF} from '@angular/common';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { BackendServiceMock } from '../shared/backend-service-mock';
import { HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { of } from 'rxjs';

describe('ViewtaskComponent', () => {
  let component: ViewtaskComponent;
  let fixture: ComponentFixture<ViewtaskComponent>;
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
    //spyOn(service, 'getTasks').and.returnValue(of([]));
    fixture = TestBed.createComponent(ViewtaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should check taskModelList', () => {
    expect(component.taskModelList).toBeDefined;
  });
  it('should call sortTask method', () => {
    expect(component.sortTask('string')).toBe(false);
  });
  it('should call getTasks method', () => {
    expect(component.getTasks()).toBeDefined;
  });
  it('should call endTask method', () => {
    let taskModel = {
    };
    expect(component.endTask(taskModel)).toBeDefined;
  });
  it('should call editTask method', () => {
    let taskModel = {
    };
    expect(component.editTask(taskModel)).toBeDefined;
  });
  it('should call getProjects method', () => {
    expect(component.getProjects()).toBeDefined;
  });
  it('should call openProjectModal method', () => {
    expect(component.openProjectModal()).toBeDefined;
  });
  it('should call closeProjectModal method', () => {
    expect(component.closeProjectModal()).toBeDefined;
  });
  it('should call selectProject method', () => {
    let taskModel = {
    };
    expect(component.selectProject(taskModel)).toBeDefined;
  });
});
