package com.roche.mongodb.bean;

import com.roche.mongodb.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.verify;

public class TaskHomeTest {

    @Mock private TaskRepository taskRepository;
	private TaskHome taskHome;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        taskHome = new TaskHome();
        taskHome.setTaskRepository(taskRepository);
    }

    @Test
	public void shouldGetCorrectMessage() {			
		String message = taskHome.getMessage();
		Assert.assertEquals("Hello from Spring",message);
	}
	
	@Test
	public void shouldSaveTaskAndResetTaskInstanceOnBean() {		
		Task oldTask = taskHome.getTask();
		taskHome.getTask().setDescription("Sample Description");
		taskHome.saveTask();
        verify(taskRepository).save(oldTask);
		Assert.assertNull("Task has not been reset",taskHome.getTask().getDescription());
		Assert.assertNull("Task has not been reset",taskHome.getTask().getId());
		Assert.assertNotSame("Task object has not been replaced",oldTask, taskHome.getTask());
	}
}
