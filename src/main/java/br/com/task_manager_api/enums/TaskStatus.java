package br.com.task_manager_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus {
  OPENED("Opened"),INPROGRESS("In progress"),COMPLETED("Completed");

  private final String status;
}
