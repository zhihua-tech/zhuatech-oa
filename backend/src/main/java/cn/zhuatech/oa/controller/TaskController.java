/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.controller;
import cn.zhuatech.oa.common.*;
import cn.zhuatech.oa.dto.OaDto.*;
import cn.zhuatech.oa.model.TodoTask;
import cn.zhuatech.oa.repository.TodoTaskRepository;
import cn.zhuatech.oa.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/tasks")
public class TaskController {
    private final TodoTaskRepository tasks; private final CurrentUserService current;
    public TaskController(TodoTaskRepository tasks, CurrentUserService current) { this.tasks=tasks; this.current=current; }
    @GetMapping public ApiResponse<List<TaskView>> list() { return ApiResponse.ok(tasks.findByAssigneeOrderByCompletedAscDueDateAsc(current.get()).stream().map(TaskView::from).toList()); }
    @PostMapping public ApiResponse<TaskView> create(@Valid @RequestBody TaskCreateRequest req) { String p=req.priority()==null ? "MEDIUM" : req.priority(); return ApiResponse.ok("待办已创建", TaskView.from(tasks.save(new TodoTask(current.get(), req.title(), req.description(), req.dueDate(), p)))); }
    @PatchMapping("/{id}") public ApiResponse<TaskView> status(@PathVariable Long id, @RequestBody TaskStatusRequest req) { TodoTask task=tasks.findByIdAndAssignee(id, current.get()).orElseThrow(() -> new BusinessException("待办不存在或无权操作")); task.setCompleted(req.completed()); return ApiResponse.ok(TaskView.from(tasks.save(task))); }
    @DeleteMapping("/{id}") public ApiResponse<Void> delete(@PathVariable Long id) { TodoTask task=tasks.findByIdAndAssignee(id, current.get()).orElseThrow(() -> new BusinessException("待办不存在或无权操作")); tasks.delete(task); return ApiResponse.ok("待办已删除", null); }
}
