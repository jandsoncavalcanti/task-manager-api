package br.com.task_manager_api.specifications;

import br.com.task_manager_api.dtos.TaskDto;
import br.com.task_manager_api.models.Subscription;
import br.com.task_manager_api.models.Task;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@Data
public class TaskFilterSpecification {
  public Specification<Task> search (TaskDto taskDto) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (taskDto.getTitle() != null && !taskDto.getTitle().isEmpty()) {
        predicates.add(criteriaBuilder.like(root.get("title"), "%" + taskDto.getTitle() + "%"));
      }

      if (taskDto.getStatus() != null) {
        predicates.add(criteriaBuilder.equal(root.get("status"), taskDto.getStatus()));
      }

      if (taskDto.getPriority() != null) {
        predicates.add(criteriaBuilder.equal(root.get("priority"), taskDto.getPriority()));
      }

      if (taskDto.getProjectID() != null) {
        predicates.add(criteriaBuilder.equal(root.get("project").get("uuid"), taskDto.getProjectID()));
      }

      if (taskDto.getAssignedToID() != null) {
        predicates.add(criteriaBuilder.equal(root.get("assignedTo").get("uuid"), taskDto.getAssignedToID()));
      }

      if (taskDto.getWatchingID() != null) {
        Subquery<Subscription> subquery = query.subquery(Subscription.class);
        Root<Subscription> subqueryRoot = subquery.from(Subscription.class);

        subquery.select(subqueryRoot)
          .where(
            criteriaBuilder.and(
              criteriaBuilder.equal(root.get("uuid"), subqueryRoot.get("task").get("uuid")),
              criteriaBuilder.equal(subqueryRoot.get("apiUser").get("uuid"), taskDto.getWatchingID())
            )
          );

        predicates.add(criteriaBuilder.exists(subquery));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }
}
