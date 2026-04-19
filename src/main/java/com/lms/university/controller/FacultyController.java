package com.lms.university.controller;

import com.lms.university.dto.DepartmentUpdateDto;
import com.lms.university.entity.Department;
import com.lms.university.exception.ConflictException;
import com.lms.university.exception.NotFoundException;
import com.lms.university.service.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/uni")
@Tag(name = "Structure Universitaire", description = "Gestion des départements et assignation des cours")
public class FacultyController {

    private final FacultyService facultyService;

    // ======================== DÉPARTEMENTS ========================

    @Operation(summary = "Lister tous les départements", description = "Retourne la liste complète des départements (GIT, GC, GEM, etc.).")
    @GetMapping("/department")
    public ResponseEntity<?> listDepartments() {
        try {
            List<Department> departments = facultyService.getDepartments();
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list departments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtenir un département par ID", description = "Retourne les détails d'un département spécifique.")
    @GetMapping("/department/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable(name = "id") Long id) {
        try {
            Department department = facultyService.getDepartmentById(id);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get department", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Créer un département", description = "Crée un nouveau département dans la structure universitaire.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/department")
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        try {
            Department newDepartment = facultyService.createDepartment(department);
            return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
        } catch (ConflictException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create department", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Mettre à jour un département", description = "Met à jour les informations d'un département existant.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("department/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable(name = "id") Long id, @RequestBody DepartmentUpdateDto department) {
        try {
            Department updatedDepartment = facultyService.updateDepartment(id, department);
            return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update department", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Supprimer un département", description = "Supprime un département par son ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("department/{id}/delete")
    public ResponseEntity<String> deleteDepartment(@PathVariable(name = "id") Long id) {
        try {
            String response = facultyService.deleteDepartment(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete department", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Départements non assignés", description = "Retourne les départements qui ne sont assignés à aucune faculté.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/department/unassigned")
    public ResponseEntity<?> listDepartmentWithoutFaculty() {
        try {
            List<Department> departments = facultyService.listDepartmentWithoutFaculty();
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list departments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ======================== ASSIGNATION ========================

    @Operation(summary = "Départements d'une faculté", description = "Liste les départements assignés à une faculté donnée.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faculty/{id}/departments")
    public ResponseEntity<?> listDepartmentsByFacultyId(@PathVariable(name = "id") Long id) {
        try {
            List<Department> departments = facultyService.getDepartmentsByFacultyId(id);
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list departments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Assigner un département à une faculté", description = "Associe un département existant à une faculté.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/faculty/{facultyId}/department/{departmentId}")
    public ResponseEntity<?> assignDepartmentToFaculty(@PathVariable(name = "facultyId") Long facultyId, @PathVariable(name = "departmentId") Long departmentId) {
        try {
            String response = facultyService.assignDepartmentToFaculty(facultyId, departmentId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to assign department to faculty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Désassigner un département", description = "Retire l'assignation d'un département de sa faculté.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/department/{id}/unassign")
    public ResponseEntity<?> unassignDepartmentFromFaculty(@PathVariable(name = "id") Long departmentId) {
        try {
            String response = facultyService.unassignDepartmentFromFaculty(departmentId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to unassign department from faculty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Assigner un cours à un département", description = "Associe un cours existant à un département.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/department/{departmentId}/course/{courseId}")
    public ResponseEntity<?> assignCourseToDepartment(@PathVariable(name = "departmentId") Long departmentId, @PathVariable(name = "courseId") Long courseId) {
        try {
            String response = facultyService.assignCourseToDepartment(departmentId, courseId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to assign course to department: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Désassigner un cours d'un département", description = "Retire l'assignation d'un cours d'un département.")
     @DeleteMapping("/department/{departmentId}/course/{courseId}")
     public ResponseEntity<?> unassignCourseFromDepartment(@PathVariable(name = "departmentId") Long departmentId,  @PathVariable(name = "courseId") Long courseId) {
         try {
             String response = facultyService.unassignCourseFromDepartment(departmentId, courseId);
             return new ResponseEntity<>(response, HttpStatus.OK);
         } catch (NotFoundException e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
         } catch (Exception e) {
             return new ResponseEntity<>("Failed to unassign course from department", HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
}
