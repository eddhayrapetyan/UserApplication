package com.jambit.testdocker.controller;

import com.jambit.testdocker.dto.UniversityDto;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.exception.PersonNotFoundException;
import com.jambit.testdocker.exception.UniversityAlreadyExistsException;
import com.jambit.testdocker.exception.UniversityNotFoundException;
import com.jambit.testdocker.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping("universities")
    public ResponseEntity<List<UniversityDto>> getAllUniversities() {
        List<UniversityDto> universities = universityService.getAllUniversities();

        if (universities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

    @PostMapping("universities/register")
    public ResponseEntity<String> registerUniversity(@RequestBody UniversityDto university) {
        try {
            universityService.insertUniversity(university);

            return ResponseEntity.ok("University successfully inserted");
        } catch (UniversityAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration error occurred!");
        }
    }

    @GetMapping("/persons/{person_id}/universities")
    public ResponseEntity<List<UniversityEntity>> getUniversitiesByPersonId(
            @PathVariable(value = "person_id") Long personId) {
        try {
            List<UniversityEntity> universitiesByPersonId = universityService.getUniversitiesByPersonId(personId);
            return new ResponseEntity<>(universitiesByPersonId, HttpStatus.OK);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("universities/{id}")
    public ResponseEntity<UniversityEntity> getUniversityById(@PathVariable long id) {
        try {
            var universityById = universityService.getUniversityById(id);
            return new ResponseEntity<>(universityById, HttpStatus.OK);
        } catch (UniversityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("universities/{id}")
    public ResponseEntity<UniversityDto> updateUniversityById(@PathVariable("id") long id,
                                                              @RequestBody UniversityDto universityDto) {
        try {
            universityService.updateUniversity(id, universityDto);

            return new ResponseEntity<>(universityDto, HttpStatus.OK);
        } catch (UniversityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/universities/{id}")
    public ResponseEntity<HttpStatus> deleteUniversityById(@PathVariable("id") long id) {
        try {
            universityService.deleteUniversityById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (UniversityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
