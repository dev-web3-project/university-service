package com.lms.university.controller;

import com.lms.university.dto.*;
import com.lms.university.entity.*;
import com.lms.university.exception.ConflictException;
import com.lms.university.exception.NotFoundException;
import com.lms.university.service.*;
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
@Tag(name = "Établissements & Cycles", description = "Gestion des établissements, cycles, classes et génies")
public class UniversityController {

    private final FacultyService facultyService;
    private final EstablishmentService establishmentService;
    private final CycleService cycleService;
    private final ClassService classService;
    private final GenieService genieService;


    // ---------------- Establishment ----------------

    @GetMapping("/establishment")
    public ResponseEntity<?> listEstablishments() {
        try {
            List<Establishment> establishments = establishmentService.listEstablishments();
            return new ResponseEntity<>(establishments, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list establishments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/establishment/{id}")
    public ResponseEntity<?> getEstablishment(@PathVariable(name = "id") Long id) {
        try {
            Establishment establishment = establishmentService.getEstablishmentById(id);
            return new ResponseEntity<>(establishment, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get establishment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/establishment")
    public ResponseEntity<?> createEstablishment(@RequestBody Establishment establishment) {
        try {
            Establishment newEstablishment = establishmentService.createEstablishment(establishment);
            return new ResponseEntity<>(newEstablishment, HttpStatus.CREATED);
        } catch (ConflictException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create establishment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/establishment/{id}/update")
    public ResponseEntity<?> updateEstablishment(@RequestBody EstablishmentUpdateDto establishmentUpdateDto, @PathVariable(name = "id") Long id) {
        try {
            Establishment updatedEstablishment = establishmentService.updateEstablishment(id, establishmentUpdateDto);
            return new ResponseEntity<>(updatedEstablishment, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update establishment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/establishment/{id}/delete")
    public ResponseEntity<String> deleteEstablishment(@PathVariable(name = "id") Long id) {
        try {
            String response = establishmentService.deleteEstablishment(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete establishment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/establishment/{id}/cycles")
    public ResponseEntity<?> listCyclesByEstablishmentId(@PathVariable(name = "id") Long id) {
        try {
            List<Cycle> cycles = cycleService.getCyclesByEstablishmentId(id);
            return new ResponseEntity<>(cycles, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list cycles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/establishment/{establishmentId}/cycle/{cycleId}")
    public ResponseEntity<?> assignCycleToEstablishment(@PathVariable(name = "establishmentId") Long establishmentId, @PathVariable(name = "cycleId") Long cycleId) {
        try {
            String response = establishmentService.assignCycleToEstablishment(establishmentId, cycleId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to assign cycle to establishment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cycle/unassigned")
    public ResponseEntity<?> listCyclesWithoutEstablishment() {
        try {
            List<Cycle> cycles = cycleService.listCyclesWithoutEstablishment();
            return new ResponseEntity<>(cycles, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list cycles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/cycle/{id}/unassign")
    public ResponseEntity<?> unassignCycleFromEstablishment(@PathVariable(name = "id") Long cycleId) {
        try {
            String response = establishmentService.unassignCycleFromEstablishment(cycleId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to unassign cycle from establishment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ---------------- Cycle ----------------

    @GetMapping("/cycle")
    public ResponseEntity<?> listCycles() {
        try {
            List<Cycle> cycles = cycleService.listCycles();
            return new ResponseEntity<>(cycles, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list cycles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cycle/{id}")
    public ResponseEntity<?> getCycle(@PathVariable(name = "id") Long id) {
        try {
            Cycle cycle = cycleService.getCycleById(id);
            return new ResponseEntity<>(cycle, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cycle")
    public ResponseEntity<?> createCycle(@RequestBody Cycle cycle) {
        try {
            Cycle newCycle = cycleService.createCycle(cycle);
            return new ResponseEntity<>(newCycle, HttpStatus.CREATED);
        } catch (ConflictException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/cycle/{id}/update")
    public ResponseEntity<?> updateCycle(@RequestBody CycleUpdateDto cycleUpdateDto, @PathVariable(name = "id") Long id) {
        try {
            Cycle updatedCycle = cycleService.updateCycle(id, cycleUpdateDto);
            return new ResponseEntity<>(updatedCycle, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/cycle/{id}/delete")
    public ResponseEntity<String> deleteCycle(@PathVariable(name = "id") Long id) {
        try {
            String response = cycleService.deleteCycle(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cycle/{id}/classes")
    public ResponseEntity<?> listClassesByCycleId(@PathVariable(name = "id") Long id) {
        try {
            List<com.lms.university.entity.Class> classes = classService.getClassesByCycleId(id);
            return new ResponseEntity<>(classes, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list classes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cycle/{cycleId}/class/{classId}")
    public ResponseEntity<?> assignClassToCycle(@PathVariable(name = "cycleId") Long cycleId, @PathVariable(name = "classId") Long classId) {
        try {
            String response = cycleService.assignClassToCycle(cycleId, classId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to assign class to cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/class/unassigned")
    public ResponseEntity<?> listClassesWithoutCycle() {
        try {
            List<com.lms.university.entity.Class> classes = classService.listClassesWithoutCycle();
            return new ResponseEntity<>(classes, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list classes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/class/{id}/unassign")
    public ResponseEntity<?> unassignClassFromCycle(@PathVariable(name = "id") Long classId) {
        try {
            String response = cycleService.unassignClassFromCycle(classId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to unassign class from cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ---------------- Class ----------------

    @GetMapping("/class")
    public ResponseEntity<?> listClasses() {
        try {
            List<com.lms.university.entity.Class> classes = classService.listClasses();
            return new ResponseEntity<>(classes, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list classes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<?> getClass(@PathVariable(name = "id") Long id) {
        try {
            com.lms.university.entity.Class classRef = classService.getClassById(id);
            return new ResponseEntity<>(classRef, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get class", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/class")
    public ResponseEntity<?> createClass(@RequestBody com.lms.university.entity.Class classRef) {
        try {
            com.lms.university.entity.Class newClass = classService.createClass(classRef);
            return new ResponseEntity<>(newClass, HttpStatus.CREATED);
        } catch (ConflictException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create class", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/class/{id}/update")
    public ResponseEntity<?> updateClass(@RequestBody ClassUpdateDto classUpdateDto, @PathVariable(name = "id") Long id) {
        try {
            com.lms.university.entity.Class updatedClass = classService.updateClass(id, classUpdateDto);
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update class", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/class/{id}/delete")
    public ResponseEntity<String> deleteClass(@PathVariable(name = "id") Long id) {
        try {
            String response = classService.deleteClass(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete class", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/class/{id}/genies")
    public ResponseEntity<?> listGeniesByClassId(@PathVariable(name = "id") Long id) {
        try {
            List<Genie> genies = genieService.getGeniesByClassId(id);
            return new ResponseEntity<>(genies, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list genies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/class/{classId}/genie/{genieId}")
    public ResponseEntity<?> assignGenieToClass(@PathVariable(name = "classId") Long classId, @PathVariable(name = "genieId") Long genieId) {
        try {
            String response = classService.assignGenieToClass(classId, genieId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to assign genie to class", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/genie/unassigned")
    public ResponseEntity<?> listGeniesWithoutClass() {
        try {
            List<Genie> genies = genieService.listGeniesWithoutClass();
            return new ResponseEntity<>(genies, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list genies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/genie/{id}/unassign")
    public ResponseEntity<?> unassignGenieFromClass(@PathVariable(name = "id") Long genieId) {
        try {
            String response = classService.unassignGenieFromClass(genieId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to unassign genie from class", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ---------------- Genie ----------------

    @GetMapping("/genie")
    public ResponseEntity<?> listGenies() {
        try {
            List<Genie> genies = genieService.listGenies();
            return new ResponseEntity<>(genies, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to list genies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genie/{id}")
    public ResponseEntity<?> getGenie(@PathVariable(name = "id") Long id) {
        try {
            Genie genie = genieService.getGenieById(id);
            return new ResponseEntity<>(genie, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get genie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/genie")
    public ResponseEntity<?> createGenie(@RequestBody Genie genie) {
        try {
            Genie newGenie = genieService.createGenie(genie);
            return new ResponseEntity<>(newGenie, HttpStatus.CREATED);
        } catch (ConflictException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create genie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/genie/{id}/update")
    public ResponseEntity<?> updateGenie(@RequestBody GenieUpdateDto genieUpdateDto, @PathVariable(name = "id") Long id) {
        try {
            Genie updatedGenie = genieService.updateGenie(id, genieUpdateDto);
            return new ResponseEntity<>(updatedGenie, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update genie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/genie/{id}/delete")
    public ResponseEntity<String> deleteGenie(@PathVariable(name = "id") Long id) {
        try {
            String response = genieService.deleteGenie(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete genie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
