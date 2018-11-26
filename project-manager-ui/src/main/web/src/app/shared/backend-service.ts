import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RouterModule, Router } from '@angular/router';
import { Config } from '../env/index';

@Injectable()
export class BackendService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  getTasks(projectId) {
    return this.httpClient.get(Config.API + '/project-manager/getTaskInfo/' + projectId);
  }

  getParentTasks(projectId) {
    return this.httpClient.get(Config.API + '/project-manager/getParentTaskInfo/' + projectId);
  }

  getUsers(inputParam) {
    return this.httpClient.get(Config.API + '/project-manager/getUserInfo', inputParam);
  }

  updateUser(inputParam) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.httpClient.post(Config.API + '/project-manager/updateUser',
      inputParam,
      { headers: headers });
  }

  deleteUser(userId) {
    return this.httpClient.delete(Config.API + '/project-manager/deleteUser/' + userId);
  }

  getProjects(inputParam) {
    return this.httpClient.get(Config.API + '/project-manager/getProjectInfo', inputParam);

  }

  deleteProject(projectId) {
    return this.httpClient.delete(Config.API + '/project-manager/deleteProject/' + projectId);
  }

  updateProject(inputParam) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.httpClient.post(Config.API + '/project-manager/updateProject',
      inputParam,
      { headers: headers });
  }

  updateTask(inputParam) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.httpClient.post(Config.API + '/project-manager/updateTask',
      inputParam,
      { headers: headers });
  }

}
