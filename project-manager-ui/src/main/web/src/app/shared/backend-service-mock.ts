import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RouterModule, Router } from '@angular/router';
import { Config } from '../env/index';
import { of } from 'rxjs';

@Injectable()
export class BackendServiceMock {

    constructor(private httpClient: HttpClient, private router: Router) { }

    getTasks(projectId) {
        let tasks = [
          
        ];
        return of(tasks);
    }

    getParentTasks(projectId) {
        let parentTasks = [
           
        ];
        return of(parentTasks);
    }

    getUsers(inputParam) {
        let users = [];
        return of(users);
    }

    updateUser(inputParam) {
        return of(true);
    }

    deleteUser(userId) {
        return of(true);
    }

    getProjects(inputParam) {
        let projects = [];
        return of(projects);

    }

    deleteProject(projectId) {
        return of(true);
    }

    updateProject(inputParam) {
       
        return of(true);
    }

    updateTask(inputParam) {
       return of(true);
    }

}
