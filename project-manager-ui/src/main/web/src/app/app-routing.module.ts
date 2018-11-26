import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { UpdatetaskComponent } from './updatetask/updatetask.component';
import { ViewtaskComponent } from './viewtask/viewtask.component';
import { UserComponent } from './user/user.component';
import { ProjectComponent } from './project/project.component';

const routes: Routes = [
  { path: '', redirectTo: '/viewTask', pathMatch: 'full' },
  { path: 'viewTask', component: ViewtaskComponent },
  { path: 'updateTask', component: UpdatetaskComponent },
  { path: 'user', component: UserComponent },
  { path: 'project', component: ProjectComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }