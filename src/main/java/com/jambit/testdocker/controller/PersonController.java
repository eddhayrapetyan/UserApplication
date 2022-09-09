package com.jambit.testdocker.controller;

import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.exception.PersonAlreadyExistException;
import com.jambit.testdocker.exception.PersonNotFoundException;
import com.jambit.testdocker.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping("/persons/register")
    public ResponseEntity<String> registerPerson(@RequestBody PersonEntity entity) {
        try {
            personService.insertPerson(entity);
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
            persons = personService.findAllPersons();
        } else {
            persons = personService.getAllPersonsListByUsername(username);
        }

        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/persons/uni/{id}")
    public ResponseEntity<List<PersonEntity>> getPersonsByUniId(@PathVariable long id) {
        List<PersonEntity> persons = personService.getPersonsByUniId(id);

        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<PersonEntity> getPersonById(@PathVariable("id") long id) {
        try {
            PersonEntity personById = personService.getPersonById(id);
            return ResponseEntity.ok(personById);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/persons/{id}")
    public ResponseEntity<PersonEntity> updatePersonById(@PathVariable("id") long id,
                                                         @RequestBody PersonEntity person) {
        try {
            personService.updatePersonById(id, person);
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable("id") long id) {
        try {
            personService.deletePersonById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}