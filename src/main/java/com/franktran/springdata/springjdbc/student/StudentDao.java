package com.franktran.springdata.springjdbc.student;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author frank.tran
 */
@Repository
public class StudentDao {

    private final JdbcTemplate jdbcTemplate;

    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Student> mapStudentFomDb() {
        return (resultSet, i) -> {
            String studentIdStr = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(studentIdStr);

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");

            return new Student(
                    studentId,
                    firstName,
                    lastName,
                    email
            );
        };
    }

    public List<Student> selectAllStudents() {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email " +
                "FROM student";
        return jdbcTemplate.query(sql, mapStudentFomDb());
    }

    public Student selectStudentById(UUID studentId) {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email " +
                "FROM student " +
                "WHERE student_id = ?";
        return DataAccessUtils.uniqueResult(jdbcTemplate.query(sql, mapStudentFomDb(), studentId.toString()));
    }

    boolean selectExistsEmail(UUID studentId, String email) {
        String sql = "" +
                "SELECT EXISTS ( " +
                "   SELECT 1 " +
                "   FROM student " +
                "   WHERE student_id <> ? " +
                "    AND email = ? " +
                ")";
        return jdbcTemplate.queryForObject(sql, Boolean.class, studentId.toString(), email);
    }

    public int insertStudent(UUID studentId, Student student) {
        String sql = "" +
                "INSERT INTO student (" +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email) " +
                "VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                studentId.toString(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }

    public int updateStudent(UUID studentId, Student student) {
        String sql = "" +
                "UPDATE student " +
                " SET first_name = ?, " +
                " last_name = ?, " +
                " email = ? " +
                " WHERE student_id = ?";
        return jdbcTemplate.update(
                sql,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getStudentId().toString()
        );
    }

    public int deleteStudentById(UUID studentId) {
        String sql = "" +
                " DELETE FROM student " +
                " WHERE student_id = ?";
        return jdbcTemplate.update(sql, studentId);
    }
}
