package com.nettops.helloworld.controller.user.v1;

import com.nettops.helloworld.model.user.User;
import com.nettops.helloworld.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user/v1")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = service.findByIdAndTreatHeaderValues(id, request, response);
        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public User create(@Valid @RequestBody User user) throws Exception {
        User created = service.create(user);
        return created;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public User update(@PathVariable Long id,
                       @Valid @RequestBody User user) throws Exception {
        User updated = service.update(user, id);
        return updated;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        service.delete(id);
    }

}
