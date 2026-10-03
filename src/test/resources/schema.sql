CREATE TABLE IF NOT EXISTS students (
  id varchar(36) NOT NULL DEFAULT '',
  name varchar(20) NOT NULL DEFAULT '',
  furigana varchar(30) NOT NULL DEFAULT '',
  nickname varchar(20) NOT NULL DEFAULT '',
  email varchar(200) NOT NULL DEFAULT 'empty@sample.com',
  address varchar(100) NOT NULL DEFAULT '',
  age int NOT NULL DEFAULT 0,
  gender varchar(10) NOT NULL DEFAULT '',
  remark varchar(5000) NOT NULL DEFAULT '',
  deleted boolean DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS students_courses (
  id varchar(36) NOT NULL DEFAULT '',
  student_id varchar(36) NOT NULL DEFAULT '',
  course_name varchar(50) NOT NULL DEFAULT '',
  course_start_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  course_end_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);