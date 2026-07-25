/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.repository;
import cn.zhuatech.oa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
    List<TodoTask> findByAssigneeOrderByCompletedAscDueDateAsc(UserAccount assignee);
    Optional<TodoTask> findByIdAndAssignee(Long id, UserAccount assignee);
    long countByAssigneeAndCompletedFalse(UserAccount assignee);
}
