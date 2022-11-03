package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCourses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    @GetMapping( "/{id}/previous")
    public Iterable<Course> getParentCourses(@PathVariable long id) {

        Course course = courseRepository.findById(id);
        String path = course.getPath();
        List <Course> courses = new ArrayList<>();
        if(path!=null){
            if(path.contains(".")){
                List<Long> ids = Arrays.stream(path.split("\\."))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                for(int i=0; i < ids.size(); i++){
                    courses.add(courseRepository.findById(ids.get(i)).get());
                }
            }else{
                courses.add(courseRepository.findById(Long.parseLong(path)));
            }
        }
        return courses;
    }
    // END

}
