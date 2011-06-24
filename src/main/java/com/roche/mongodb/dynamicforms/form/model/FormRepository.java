package com.roche.mongodb.dynamicforms.form.model;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FormRepository extends PagingAndSortingRepository<Form, ObjectId> {

}
