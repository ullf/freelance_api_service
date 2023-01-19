package dev.freelance.freeserve.controller;

import dev.freelance.freeserve.entity.AbstractClient;
import dev.freelance.freeserve.service.AbstractClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import dev.freelance.freeserve.entity.ApiError;
import java.util.Optional;

/*
This is a controller class in the application that provides several endpoints for interacting with a AbstractClient object, such as creating a new client,
 finding a client by nickname, and updating a client.
 */
@RestController
@AllArgsConstructor
public class ClientController {

    private final AbstractClientService abstractClientService;
    private final PasswordEncoder passwordEncoder;

    /* It accepts a AbstractClient object as input and saves it to the database, after encoding the password with a PasswordEncoder.
     There is also an overloaded version of this method that takes in individual name, surname, and boolean value as path variables
     and creates a new AbstractClient object with those values.
     */
    @PostMapping("/createClient")
    public void createAbstractClient(@RequestBody AbstractClient abstractClient) {
        abstractClient.setPassword(passwordEncoder.encode(abstractClient.getPassword()));
        abstractClientService.createAbstractClient(abstractClient);
    }

    /*
    The createAbstractClient method accepts a AbstractClient object as input and saves it to the database, after encoding the password with a PasswordEncoder.
    There is also an overloaded version of this method that takes in individual name, surname, and boolean value as path variables and creates a new AbstractClient object
    with those values.
     */
    @GetMapping("/createClient/{name}/{surname}/{bool}")
    public void createAbstractClient(@PathVariable String name,@PathVariable String surname,@PathVariable boolean bool) {
        abstractClientService.createAbstractClient(name,surname,bool);
    }

    /*
     method accepts a nickname as a path variable and attempts to find a AbstractClient object with that nickname in the database.
      If it is found, it is returned in the response. If it is not found, an ApiError object is created and returned in the response.
     */
    @GetMapping("/findClientByNickname/{nickname}")
    public ResponseEntity<?> findClientByNickname(@PathVariable String nickname) {
        var user = abstractClientService.findAbstractClientByNickname(nickname);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        ApiError err = new ApiError();
        err.setMessage("No client found with such nickname");
        err.setStatus(HttpStatus.NOT_FOUND);
        Optional<ApiError> op_err = Optional.of(err);
        return ResponseEntity.of(op_err);
    }

}
