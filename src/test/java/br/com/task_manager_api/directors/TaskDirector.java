package br.com.task_manager_api.directors;

import br.com.task_manager_api.enums.TaskPriority;
import br.com.task_manager_api.enums.TaskStatus;
import br.com.task_manager_api.models.Task;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskDirector {

  public static Task basicValidExample() {
    Task task = new Task();
    task.setTitle("Task Title");
    task.setStatus(TaskStatus.OPENED);
    task.setPriority(TaskPriority.HIGH);
    return task;
  }

  public static Task noStatusExample() {
    Task task = basicValidExample();
    task.setStatus(null);
    return task;
  }

  public static Task noPriorityExample() {
    Task task = basicValidExample();
    task.setPriority(null);
    return task;
  }
}
