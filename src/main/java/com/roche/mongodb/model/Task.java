package com.roche.mongodb.model;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.mapping.Document;

@Document
public class Task {

	private ObjectId id;
	private String description;

    public Task() { }

    public Task(String description) {
        this();
        this.description = description;
    }

    public ObjectId getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != null ? !id.equals(task.id) : task.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
