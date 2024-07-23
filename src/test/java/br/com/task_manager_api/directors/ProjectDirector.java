package br.com.task_manager_api.directors;

import br.com.task_manager_api.models.Project;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectDirector {
  public static Project validExample() {
    Project project = new Project();
    project.setName("Valid Test 1");
    return project;
  }

  public static Project invalidExample() {
    return new Project();
  }
}
