package br.com.task_manager_api.directors;

import br.com.task_manager_api.models.ApiUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUserDirector {
  public static ApiUser validExample() {
    ApiUser apiUser = new ApiUser();
    apiUser.setUsername("Valid ApiUser");
    return apiUser;
  }

  public static ApiUser invalidExample() {
    return new ApiUser();
  }
}
