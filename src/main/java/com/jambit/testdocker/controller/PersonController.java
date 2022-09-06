package com.jambit.testdocker.controller;

import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.exception.PersonAlreadyExistException;
import com.jambit.testdocker.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService service;

    @PostMapping
    public ResponseEntity<String> personRegistration(@RequestBody PersonEntity entity) {
        try {
            service.insertPerson(entity);
            return ResponseEntity.ok("Person successfully inserted");
        } catch (PersonAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration error occurred!");
        }
    }

    @GetMapping("/persons")
    public ResponseEntity<List<PersonEntity>> getAllPersons(@RequestParam(required = false) String username) {
        List<PersonEntity> persons;
        if (username == null) {
            persons = service.getAllPersonsList();
        } else {
            persons = service.getAllPersonsListByUsername(username);
        }

        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }


}
