package com.backend.springboot.template.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.backend.springboot.template.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.springboot.template.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService service;
    @Autowired
    UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO create(@RequestBody UserDTO userEntry) {
        return service.create(userEntry);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    UserDTO delete(@PathVariable("id") String id) {
        return service.delete(id);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    List<UserDTO> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    UserDTO findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/findByUserName/{userName:.+}", method = RequestMethod.GET)
    UserDTO findByUserName(@PathVariable("userName") String userName) {
        return service.findByUserName(userName);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    UserDTO update(@RequestBody @Valid UserDTO userEntry) {
        return service.update(userEntry, false);
    }
}