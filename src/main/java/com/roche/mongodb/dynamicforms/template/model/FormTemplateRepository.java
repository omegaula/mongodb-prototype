package com.roche.mongodb.dynamicforms.template.model;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FormTemplateRepository extends PagingAndSortingRepository<FormTemplate, ObjectId> {
}
