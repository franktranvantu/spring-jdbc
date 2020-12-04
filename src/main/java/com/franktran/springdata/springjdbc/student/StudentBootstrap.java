package com.franktran.springdata.springjdbc.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class StudentBootstrap implements CommandLineRunner {

    private final StudentDao studentDao;

    public StudentBootstrap(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Student> students = studentDao.selectAllStudents();
//        System.out.println(students);

//        Student frank = new Student(null, "Frank", "Tran", "frank@gmail.com");
//        int records = studentDao.insertStudent(UUID.randomUUID(), frank);
//        System.out.println(records);

        UUID studentId = UUID.fromString("29b509a8-4938-4fe1-a2e4-e93af251582b");
        Student student = studentDao.selectStudentById(studentId);
        Student updateStudent = new Student(studentId, "Henry", student.getLastName(), "henry@gmail.com");
        studentDao.updateStudent(studentId, updateStudent);
        System.out.println(updateStudent);

        boolean existsEmail = studentDao.selectExistsEmail(studentId, "henry@gmail.com");
        System.out.println(existsEmail);
    }

}
