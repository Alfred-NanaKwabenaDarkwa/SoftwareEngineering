package com.example.demo.service;

import com.example.demo.model.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SoftwareEngineeringService {

    public List<Student> getAllStudents() {
        Student student1 = new Student();
        student1.setName("Alfred Darkwa");
        student1.setStudentId(22221001);
        student1.setPhone("024-456-7890");

        Student student2 = new Student();
        student2.setName("Daniel Fugar");
        student2.setStudentId(22221002);
        student2.setPhone("053-654-3210");

        Student student3 = new Student();
        student3.setName("Nana Yaw Asiedu");
        student3.setStudentId(22222221);
        student3.setPhone("055-555-5555");

        Student student4 = new Student();
        student4.setName("John Ahiatrogah");
        student4.setStudentId(22222222);
        student4.setPhone("024-444-4444");

        return Arrays.asList(student1, student2, student3, student4);
    }

    public List<Course> getAllCourses() {
        Course course1 = new Course();
        course1.setCourseCode("CS101");
        course1.setCourseName("Introduction to Programming");

        Course course2 = new Course();
        course2.setCourseCode("MATH201");
        course2.setCourseName("Calculus I");

        Course course3 = new Course();
        course3.setCourseCode("PHY301");
        course3.setCourseName("Physics I");

        Course course4 = new Course();
        course4.setCourseCode("CBAS210");
        course4.setCourseName("Academic Writing");

        return Arrays.asList(course1, course2, course3, course4);
    }

    public List<LecturerCourse> getLecturerCourses() {
        LecturerCourse lc1 = new LecturerCourse();
        lc1.setCourseCode("CS101");
        lc1.setLecturerName("Dr. PC");
        lc1.setCourseTaId(1);
        lc1.setCreditHours(3);

        LecturerCourse lc2 = new LecturerCourse();
        lc2.setCourseCode("MATH201");
        lc2.setLecturerName("Dr. Allen");
        lc2.setCourseTaId(2);
        lc2.setCreditHours(4);

        LecturerCourse lc3 = new LecturerCourse();
        lc3.setCourseCode("PHY301");
        lc3.setLecturerName("Dr. Asimen");
        lc3.setCourseTaId(3);
        lc3.setCreditHours(4);

        LecturerCourse lc4 = new LecturerCourse();
        lc4.setCourseCode("CBAS210");
        lc4.setLecturerName("Dr. Mills");
        lc4.setCourseTaId(4);
        lc4.setCreditHours(3);

        return Arrays.asList(lc1, lc2, lc3, lc4);
    }

    public List<Enrollment> getEnrollments() {
        Enrollment e1 = new Enrollment();
        e1.setSemester("Fall 2025");
        e1.setStudentId(22221001);
        e1.setEnrollmentId(1);

        Enrollment e2 = new Enrollment();
        e2.setSemester("Fall 2025");
        e2.setStudentId(22221002);
        e2.setEnrollmentId(2);

        Enrollment e3 = new Enrollment();
        e3.setSemester("Spring 2025");
        e3.setStudentId(22222221);
        e3.setEnrollmentId(3);

        Enrollment e4 = new Enrollment();
        e4.setSemester("Spring 2025");
        e4.setStudentId(22222222);
        e4.setEnrollmentId(4);

        return Arrays.asList(e1, e2, e3, e4);
    }
} 