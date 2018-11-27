import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'taskFilter',
  pure: false
})
export class TaskFilterPipe implements PipeTransform {

  transform(items: any, filter: any, screen: any): any {
    if (!items || !filter || !screen) {
      return items;
    }
    return items.filter(item => this.applyFilter(item, filter, screen));
  }

  applyFilter(taskItem: any, filterItem: any, screen: any): boolean {

    if (typeof filterItem == 'string') {
      if (screen == 'userScreen' && null !== filterItem && undefined !== filterItem && (taskItem['firstName'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1 && taskItem['lastName'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1 && taskItem['empId'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1)) {
        return false;
      } else if (screen == 'projectScreen' && null !== filterItem && undefined !== filterItem && (taskItem['projectName'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1 && taskItem['startDate'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1 && taskItem['endDate'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1 && taskItem['priority'].toString().indexOf(filterItem) === -1
        && taskItem['noOfTasksCompleted'].toString().indexOf(filterItem) === -1 && taskItem['totalNoOfTasks'].toString().indexOf(filterItem) === -1)) {
        return false;
      } else if (screen == 'parentTaskScreen' && null !== filterItem && undefined !== filterItem && taskItem['parentTaskName'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1) {
        return false;
      }  else if (screen == 'taskScreen' && null !== filterItem && undefined !== filterItem && taskItem['projectName'].toLowerCase().indexOf(filterItem.toLowerCase()) === -1) {
        return false;
      }
    }
    return true;
  }
}
