package com.roche.mongodb.bean;

import com.roche.mongodb.model.Task;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, ObjectId> {

}
