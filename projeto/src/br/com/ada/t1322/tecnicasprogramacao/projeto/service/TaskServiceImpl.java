package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepository;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.notification.Notifier;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.validation.TaskValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TaskServiceImpl extends AbstractTaskService {

    public static final Comparator<Task> DEFAULT_TASK_SORT = Comparator.comparing(Task::getDeadline);
    private static TaskServiceImpl INSTANCE;

    private TaskServiceImpl(TaskRepository taskRepository, TaskValidator taskValidator, Notifier notifier) {
        super(taskRepository, taskValidator, notifier);
    }

    public static TaskServiceImpl create(TaskRepository taskRepository, TaskValidator taskValidator, Notifier notifier) {
        if (INSTANCE == null) {
            synchronized (TaskServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskServiceImpl(taskRepository, taskValidator, notifier);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public List<Task> findAll(Optional<Comparator<Task>> orderBy) {
        if(orderBy.isPresent()) {
            return taskRepository.findAll().stream().sorted(orderBy.get()).toList();
        }
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByStatus(Task.Status status, Optional<Comparator<Task>> orderBy) {
        if(orderBy.isPresent()) {
            return taskRepository.findByStatus(status).stream().sorted(orderBy.get()).toList();
        }
        return taskRepository.findByStatus(status);
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy) {
        if(orderBy.isPresent()) {
            return taskRepository.findBy(predicate).stream().sorted(orderBy.get()).toList();
        }
        return taskRepository.findBy(predicate);
    }

}
