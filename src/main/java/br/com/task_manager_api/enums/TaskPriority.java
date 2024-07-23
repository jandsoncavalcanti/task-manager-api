package br.com.task_manager_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskPriority {
  LOW("Low"), NORMAL("Normal"), HIGH("High");

  private final String priority;
}
