package net.guides.springboot2.springboot2jpacrudexample.controller;

        import io.swagger.annotations.Api;
        import io.swagger.annotations.ApiOperation;
        import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
        import net.guides.springboot2.springboot2jpacrudexample.model.Student;
        import net.guides.springboot2.springboot2jpacrudexample.repository.StudentRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;

        import javax.validation.Valid;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import org.springframework.web.bind.annotation.CrossOrigin;
        import org.springframework.web.bind.annotation.DeleteMapping;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.PutMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v11")
@Api(value = "Student Application", description = "Operations pertaining to student in Student Application")
class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @ApiOperation(value = "View a list of available students", response = List.class)
    @GetMapping("/students")
    public List<Student> getAllEmployees() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getEmployeeById(@PathVariable(value = "id") Long studentId)
            throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("students not found for this id :: " + studentId));
        return ResponseEntity.ok().body(student);
    }

    @PostMapping("/students")
    public Student createEmployee(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long studentId,
                                                 @Valid @RequestBody Student studentDetails) throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("students not found for this id :: " + studentId));

        student.setEmailId(studentDetails.getEmailId());
        student.setLastName(studentDetails.getLastName());
        student.setFirstName(studentDetails.getFirstName());
        student.setAge(studentDetails.getAge());
        student.setCity(studentDetails.getCity());
        final Student updatedstud = studentRepository.save(student);
        return ResponseEntity.ok(updatedstud);
    }

    @DeleteMapping("/students/{id}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Long studentId)
            throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("students not found for this id :: " + studentId));

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

