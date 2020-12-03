package com.franktran.springdata.springjdbc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BootstrapData implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public BootstrapData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email " +
                "FROM student";
        List<Student> students = jdbcTemplate.query(sql, mapStudentFomDb());
        System.out.println(students);
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
}
