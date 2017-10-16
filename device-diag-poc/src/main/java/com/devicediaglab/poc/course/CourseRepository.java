package com.devicediaglab.poc.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author kanishka.yaftali
 * 
 * Data Service
 *
 */
public interface CourseRepository extends CrudRepository<Course, String>{
	
	public List<Course> findByName(String name);
	
	public List<Course> findByTopicId(String topicId);

}
