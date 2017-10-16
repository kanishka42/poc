package com.devicediaglab.poc.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;	
	
	
	public List<Course> getAllCourses(String topicId) {
		
		List<Course> courses = new ArrayList<Course>();
		
		courseRepository.findByTopicId(topicId).forEach(courses::add);
		
		//courseRepository.findAll().forEach(courses::add);
		
		return courses;
	}
	
	public Course getCourse(String id) {
		//return courses.stream().filter(t -> t.getId().equals(id)).findFirst().get();
		return courseRepository.findOne(id);
	}

	
	public void addCourse(Course course) {
		courseRepository.save(course);
	}

	public void updateCourse(Course course) {
		courseRepository.save(course);		
	}

	public void deleteCourse(String id) {
		//courses.removeIf(t -> t.getId().equals(id));
		courseRepository.delete(id);
	}
}
