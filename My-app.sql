-- Drop tables if they exist to ensure a clean slate, in reverse dependency order
DROP TABLE IF EXISTS lecturer_courses;
DROP TABLE IF EXISTS student_course_enrollment;
DROP TABLE IF EXISTS course_ta;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS fees;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS student;

-- Create tables
CREATE TABLE student (
    name VARCHAR(255),
    studentid INTEGER PRIMARY KEY,
    phone VARCHAR(20) -- Accommodates phone numbers with hyphens
);

CREATE TABLE fees (
    name VARCHAR(255),
    studentid INTEGER,
    amount_paid INTEGER,
    amount_owed INTEGER,
    CONSTRAINT fk_student_fees
        FOREIGN KEY (studentid) REFERENCES student(studentid)
);

CREATE TABLE courses (
    coursecode VARCHAR(255) PRIMARY KEY,
    coursename VARCHAR(255)
);

CREATE TABLE enrollments (
    semester VARCHAR(255), -- Renamed from Name for clarity
    studentid INTEGER,
    enrollmentid SERIAL PRIMARY KEY,
    CONSTRAINT fk_student_enrollment
        FOREIGN KEY (studentid) REFERENCES student(studentid)
);

CREATE TABLE student_course_enrollment (
    enrollmentid INTEGER,
    studentid INTEGER,
    coursecode VARCHAR(255),
    PRIMARY KEY (enrollmentid, studentid, coursecode),
    FOREIGN KEY (enrollmentid) REFERENCES enrollments(enrollmentid),
    FOREIGN KEY (studentid) REFERENCES student(studentid),
    FOREIGN KEY (coursecode) REFERENCES courses(coursecode)
);

CREATE TABLE course_ta (
    coursecode VARCHAR(255),
    coursetaid SERIAL PRIMARY KEY,
    CONSTRAINT fk_course
        FOREIGN KEY (coursecode) REFERENCES courses(coursecode)
);

CREATE TABLE lecturer_courses (
    coursecode VARCHAR(255),
    lecturername VARCHAR(255),
    coursetaid INTEGER,
    credithours INTEGER,
    PRIMARY KEY (coursecode),
    CONSTRAINT fk_course_ta
        FOREIGN KEY (coursetaid) REFERENCES course_ta(coursetaid),
    CONSTRAINT fk_courses
        FOREIGN KEY (coursecode) REFERENCES courses(coursecode)
);

-- Create or replace function for outstanding fees
CREATE OR REPLACE FUNCTION get_outstanding_fees()
RETURNS JSON AS $$
BEGIN
    RETURN (
        WITH Outstanding AS (
            SELECT 
                s.studentid,
                s.name,
                COALESCE(SUM(COALESCE(f.amount_owed, 0) - COALESCE(f.amount_paid, 0)), 0) AS OutstandingAmount
            FROM student s
            LEFT JOIN fees f ON s.studentid = f.studentid
            GROUP BY s.studentid, s.name
        )
        SELECT json_agg(
            json_build_object(
                'StudentID', studentid,
                'Name', name,
                'OutstandingAmount', OutstandingAmount
            )
        )
        FROM Outstanding
    );
END;
$$ LANGUAGE plpgsql;

-- Inserting data into the student table
INSERT INTO student (name, studentid, phone) VALUES
('Alfred Darkwa', 22221001, '024-456-7890'),
('Daniel Fugar', 22221002, '053-654-3210'),
('Nana Yaw Asiedu', 22222221, '055-555-5555'),
('John Ahiatrogah', 22222222, '024-444-4444');

-- Inserting data into the courses table
INSERT INTO courses (coursecode, coursename) VALUES
('CS101', 'Introduction to Programming'),
('MATH201', 'Calculus I'),
('PHY301', 'Physics I'),
('CBAS210', 'Academic Writing');

-- Inserting data into the enrollments table
INSERT INTO enrollments (semester, studentid) VALUES
('Fall 2025', 22221001),
('Fall 2025', 22221002),
('Spring 2025', 22222221),
('Spring 2025', 22222222);

-- Inserting data into the fees table
INSERT INTO fees (name, studentid, amount_paid, amount_owed) VALUES
('Tuition Fall 2025', 22221001, 500, 1000),
('Tuition Fall 2025', 22221002, 300, 1200),
('Tuition Spring 2025', 22222221, 800, 800),
('Tuition Spring 2025', 22222222, 1000, 500);

-- Inserting data into the student_course_enrollment table
INSERT INTO student_course_enrollment (enrollmentid, studentid, coursecode) VALUES
(1, 22221001, 'CS101'),
(1, 22221001, 'MATH201'),
(2, 22221002, 'CS101'),
(2, 22221002, 'PHY301'),
(3, 22222221, 'MATH201'),
(3, 22222221, 'CBAS210'),
(4, 22222222, 'PHY301'),
(4, 22222222, 'CBAS210');

-- Inserting data into the course_ta table
INSERT INTO course_ta (coursecode) VALUES
('CS101'),
('MATH201'),
('PHY301'),
('CBAS210');

-- Inserting data into the lecturer_courses table
INSERT INTO lecturer_courses (coursecode, lecturername, coursetaid, credithours) VALUES
('CS101', 'Dr. PC', 1, 3),
('MATH201', 'Dr. Allen', 2, 4),
('PHY301', 'Dr. Asimen', 3, 4),
('CBAS210', 'Dr. Mills', 4, 3);

-- Query to calculate outstanding fees for all students
SELECT get_outstanding_fees();