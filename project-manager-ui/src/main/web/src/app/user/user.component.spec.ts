import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserComponent } from './user.component';
import { UpdatetaskComponent } from '../updatetask/updatetask.component';
import { FormsModule } from '@angular/forms';
import { ScreenFreezeComponent } from '../screenfreeze/screenfreeze.component';
import { TaskFilterPipe } from '../shared/task-filter.pipe';
import { AppRoutingModule } from '../app-routing.module';
import { ViewtaskComponent } from '../viewtask/viewtask.component';
import { ProjectComponent } from '../project/project.component';
import { SortPipe } from '../shared/sort.pipe';
import { APP_BASE_HREF } from '@angular/common';
import { TaskService } from '../shared/task-service';
import { BackendService } from '../shared/backend-service';
import { BackendServiceMock } from '../shared/backend-service-mock';
import { HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { of } from 'rxjs';
import { NgForm, FormControl, NgModel } from '@angular/forms';

describe('UserComponent', () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;
  let service: BackendService;
  let userForm = <NgForm>{
    value: {
      "userId": 0,
      "firstName": "",
      "lastName": "",
      "empId": ""
    }
  };

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
        DatePipe,
        NgForm
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    service = TestBed.get(BackendService);
    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    userForm = fixture.debugElement.injector.get(NgForm);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should check userModelList', () => {
    expect(component.userModelList).toBeDefined;
  });
  it('should call getUsers method', () => {
    expect(component.getUsers).toBeDefined;
  });
  it('should call sortUser method', () => {
    expect(component.sortUser('string')).toBe(false);
  });
  it('should call editUser method', () => {
    expect(component.editUser('123456')).toBeDefined;
  });
  it('should call deleteUser method', () => {
    expect(component.deleteUser('123456')).toBeDefined;
  });
  it('should call resetFields method', () => {
    expect(component.resetFields()).toBeDefined;
  });
  it('should call updateUser method', () => {
    component.userModel ={
      "userId": 0,
      "firstName": "Madhan",
      "lastName": "Kumar",
      "empId": "123456"
    }
    expect(component.updateUser()).toBeTruthy;
  });
});
