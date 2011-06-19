package com.roche.mongodb.bean;

import java.util.List;

import com.roche.mongodb.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("taskHome")
public class TaskHome {

	private static final Logger logger = LoggerFactory.getLogger(TaskHome.class);

	private Task task = new Task();
	private Iterable<Task> tasks;

    private TaskRepository taskRepository;

    @SuppressWarnings({"SpringJavaAutowiringInspection"})
    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public String getMessage() {
		logger.debug("Returning message from task home bean");
		return "Hello from Spring";
	}	

	public Task getTask() {
		return task;
	}

	public void saveTask() {
        taskRepository.save(task);
		task = new Task();
		invalidateTasks();
	}

	private void invalidateTasks() {
		tasks = null;
	}

	public Iterable<Task> getTasks() {
		if (tasks == null) {
			tasks = taskRepository.findAll();
		}
		return tasks;
		
	}
}
