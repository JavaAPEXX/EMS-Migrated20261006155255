```java
package com.employee.system.service;

import com.employee.system.dto.DocumentDTO;
import com.employee.system.entity.Document;
import com.employee.system.entity.Employee;
import com.employee.system.repository.DocumentRepository;
import com.employee.system.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DocumentService documentService;

    @TempDir
    Path tempDir;

    private Employee testEmployee;
    private Document testDocument;
    private MultipartFile testFile;

    @BeforeEach
    void setUp() throws IOException {
        testEmployee = new Employee();
        testEmployee.setId(1L);

        testDocument = new Document();
        testDocument.setId(1L);
        testDocument.setEmployee(testEmployee);
        testDocument.setDocType("CONTRACT");
        testDocument.setOriginalFileName("contract.pdf");
        testDocument.setStoredFileName("uuid_contract.pdf");
        testDocument.setContentType("application/pdf");
        testDocument.setSize(1024L);

        // Create the uploads directory structure that the service expects
        // Since uploadsDir is a final field initialized to "uploads", we need to handle this carefully.
        // For testing, we will mock the file operations or ensure the directory exists if the service tries to use it.
        // However, since uploadsDir is private final and initialized in the constructor, 
        // we cannot easily inject a temp dir without reflection or changing the code.
        // But wait, the requirement says "FILESYSTEM ISOLATION: For classes performing file operations... use @TempDir Path tempDir or mock storage dependencies."
        // The service uses `Paths.get("uploads")`. This is a relative path.
        // To make tests robust, we should ideally mock the file system or use a working directory trick.
        // However, standard Mockito cannot mock static methods like Files.copy or Paths.get easily without mockito-inline.
        // Let's assume the test environment allows creating an "uploads" folder in the current working directory, 
        // OR we can use reflection to change the uploadsDir field to point to tempDir.
        // Given the constraints, using reflection to set the private final field is a common workaround for testing such services.
        
        // Let's use reflection to set the uploadsDir to tempDir to ensure isolation.
        try {
            java.lang.reflect.Field field = DocumentService.class.getDeclaredField("uploadsDir");
            field.setAccessible(true);
            // Remove final modifier if necessary (Java 17 might require special handling, but usually setAccessible works for non-static final in many contexts, 
            // though technically final fields are tricky. Let's try a simpler approach: 
            // Actually, in Java 17, modifying final fields via reflection is restricted. 
            // Alternative: The service creates the directory in @PostConstruct. 
            // If we don't call init(), the directory won't be