package com.example.demo.controller;

import com.example.demo.model.OutstandingFee;
import com.example.demo.model.User;
import com.example.demo.model.Student;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.LecturerCourse;
import com.example.demo.service.FeeService;
import com.example.demo.service.UserService;
import com.example.demo.service.SoftwareEngineeringService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private FeeService feeService;

    @Autowired
    private SoftwareEngineeringService softwareEngineeringService;

    private static final String SECRET_KEY = "yourSecretKeyMustBeAtLeast256BitsLongForSecurity"; // At least 32 characters for 256 bits
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        System.out.println("Received registration request: " + request.getEmail() + ", Method: POST, Headers: " + request.toString());
        try {
            User user = userService.registerUser(request.getEmail(), request.getPassword(), "admin");
            String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("token", token);
            System.out.println("Registration successful for: " + user.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Registration error for " + request.getEmail() + ": " + e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        try {
            User user = userService.loginUser(request.getEmail(), request.getPassword());
            String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/fees/outstanding")
    public ResponseEntity<List<OutstandingFee>> getOutstandingFees() {
        List<OutstandingFee> fees = feeService.getOutstandingFees();
        return ResponseEntity.ok(fees);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = softwareEngineeringService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = softwareEngineeringService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getEnrollments() {
        List<Enrollment> enrollments = softwareEngineeringService.getEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/lecturer-courses")
    public ResponseEntity<List<LecturerCourse>> getLecturerCourses() {
        List<LecturerCourse> lecturerCourses = softwareEngineeringService.getLecturerCourses();
        return ResponseEntity.ok(lecturerCourses);
    }
}

class UserRequest {
    private String email;
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
}