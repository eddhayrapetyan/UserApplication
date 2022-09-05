package com.jambit.testdocker.controller;

import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.exception.PersonAlreadyExistException;
import com.jambit.testdocker.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
