import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ViewtaskComponent } from './viewtask/viewtask.component';
import { UpdatetaskComponent } from './updatetask/updatetask.component';
import { UserComponent } from './user/user.component';
import { ProjectComponent } from './project/project.component';
import { FormsModule } from '@angular/forms';
import { SortPipe } from './shared/sort.pipe';
import { TaskFilterPipe } from './shared/task-filter.pipe';
import { ScreenFreezeComponent } from './screenfreeze/screenfreeze.component';
import {APP_BASE_HREF} from '@angular/common';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        ViewtaskComponent,
        UpdatetaskComponent,
        UserComponent,
        ProjectComponent,
        SortPipe,
        TaskFilterPipe,
        ScreenFreezeComponent
      ],
      imports:[
        FormsModule,
        AppRoutingModule
      ],
      providers: [{provide: APP_BASE_HREF, useValue : '/' }]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it(`should have as title 'Project Manager'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Project Manager');
  }));
});
