"use client";
import { useState, useEffect } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";
import styles from "./page.module.css";
import LoadingSpinner from "../components/LoadingSpinner";

export default function Dashboard() {
  const [fees, setFees] = useState([]);
  const [students, setStudents] = useState([]);
  const [courses, setCourses] = useState([]);
  const [enrollments, setEnrollments] = useState([]);
  const [lecturerCourses, setLecturerCourses] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const auth = JSON.parse(localStorage.getItem("auth"));
        if (!auth || !auth.token) {
          router.push("/login");
          return;
        }
        
        const headers = { Authorization: `Bearer ${auth.token}` };
        
        // Fetch all data in parallel
        const [feesRes, studentsRes, coursesRes, enrollmentsRes, lecturerCoursesRes] = await Promise.all([
          axios.get("http://localhost:8080/api/fees/outstanding", { headers }),
          axios.get("http://localhost:8080/api/students", { headers }),
          axios.get("http://localhost:8080/api/courses", { headers }),
          axios.get("http://localhost:8080/api/enrollments", { headers }),
          axios.get("http://localhost:8080/api/lecturer-courses", { headers })
        ]);
        
        setFees(feesRes.data);
        setStudents(studentsRes.data);
        setCourses(coursesRes.data);
        setEnrollments(enrollmentsRes.data);
        setLecturerCourses(lecturerCoursesRes.data);
      } catch (err) {
        setError("Failed to load data: " + err.response?.data?.message || err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [router]);

  const handleLogout = () => {
    localStorage.removeItem("auth");
    router.push("/login");
  };

  if (loading) {
    return (
      <div className={styles.page}>
        <div className={styles.main}>
          <div className={styles.content}>
            <LoadingSpinner />
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.page}>
      <button onClick={handleLogout} className={styles.logout}>
        Logout
      </button>
      <div className={styles.main}>
        <div className={styles.header}>
          <h1 className={styles.title}>Software Engineering Dashboard</h1>
          <p className={styles.subtitle}>Academic Information Management System</p>
        </div>
        
        <div className={styles.content}>
          {error && <div className={styles.error}>{error}</div>}
          
          {/* Students Section */}
          <div className={styles.section}>
            <h2 className={styles.sectionTitle}>Students</h2>
            <table className={styles.table}>
              <thead className={styles.tableHeader}>
                <tr>
                  <th>Student ID</th>
                  <th>Name</th>
                  <th>Phone</th>
                </tr>
              </thead>
              <tbody className={styles.tableBody}>
                {students.map((student) => (
                  <tr key={student.studentId}>
                    <td className={styles.studentId}>{student.studentId}</td>
                    <td>{student.name}</td>
                    <td>{student.phone}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Courses Section */}
          <div className={styles.section}>
            <h2 className={styles.sectionTitle}>Courses</h2>
            <table className={styles.table}>
              <thead className={styles.tableHeader}>
                <tr>
                  <th>Course Code</th>
                  <th>Course Name</th>
                </tr>
              </thead>
              <tbody className={styles.tableBody}>
                {courses.map((course) => (
                  <tr key={course.courseCode}>
                    <td className={styles.courseCode}>{course.courseCode}</td>
                    <td>{course.courseName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Enrollments Section */}
          <div className={styles.section}>
            <h2 className={styles.sectionTitle}>Enrollments</h2>
            <table className={styles.table}>
              <thead className={styles.tableHeader}>
                <tr>
                  <th>Enrollment ID</th>
                  <th>Student ID</th>
                  <th>Semester</th>
                </tr>
              </thead>
              <tbody className={styles.tableBody}>
                {enrollments.map((enrollment) => (
                  <tr key={enrollment.enrollmentId}>
                    <td>{enrollment.enrollmentId}</td>
                    <td className={styles.studentId}>{enrollment.studentId}</td>
                    <td>{enrollment.semester}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Lecturer Courses Section */}
          <div className={styles.section}>
            <h2 className={styles.sectionTitle}>Lecturer Course Assignments</h2>
            <table className={styles.table}>
              <thead className={styles.tableHeader}>
                <tr>
                  <th>Course Code</th>
                  <th>Lecturer Name</th>
                  <th>Credit Hours</th>
                </tr>
              </thead>
              <tbody className={styles.tableBody}>
                {lecturerCourses.map((lc) => (
                  <tr key={lc.courseCode}>
                    <td className={styles.courseCode}>{lc.courseCode}</td>
                    <td>{lc.lecturerName}</td>
                    <td>{lc.creditHours}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Outstanding Fees Section */}
          <div className={styles.section}>
            <h2 className={styles.sectionTitle}>Outstanding Fees</h2>
            {fees.length === 0 ? (
              <div className={styles.empty}>No outstanding fees found.</div>
            ) : (
              <table className={styles.table}>
                <thead className={styles.tableHeader}>
                  <tr>
                    <th>Student ID</th>
                    <th>Name</th>
                    <th>Outstanding Amount</th>
                  </tr>
                </thead>
                <tbody className={styles.tableBody}>
                  {fees.map((fee) => (
                    <tr key={fee.studentID}>
                      <td className={styles.studentId}>{fee.studentID}</td>
                      <td>{fee.name}</td>
                      <td className={styles.amount}>${fee.outstandingAmount}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}