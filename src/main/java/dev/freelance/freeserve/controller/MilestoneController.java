package dev.freelance.freeserve.controller;

import dev.freelance.freeserve.entity.ApiError;
import dev.freelance.freeserve.entity.Milestone;
import dev.freelance.freeserve.service.MilestoneService;
import lombok.AllArgsConstructor;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;

    /*
    The createMilestone method accepts three path variables: orderId, name, and description, and uses them to create a new milestone object.
    The milestoneService is then used to save the milestone object to the database.
     */
    @GetMapping("/createMilestone/{orderId}/{name}/{description}")
    public void createMilestone(@PathVariable int orderId,@PathVariable String name,@PathVariable String description) {
        milestoneService.createMilestone(orderId,name,description);
    }

    /*
    The createMilestone method accepts a Milestone object as input and uses the milestoneService to save it to the database.
    If the milestone was saved successfully, it returns an HTTP status code of 200 (OK) and the Milestone object in the response.
    If there was an error saving the milestone, it returns an HTTP status code of 400 (Bad Request) and the Milestone object in the response.
     */
    @PostMapping("/createMilestone")
    public ResponseEntity<Milestone> createMilestone(@RequestBody Milestone milestone) {
        int result = milestoneService.createMilestone(milestone);
        if (result == 0) {
            return ResponseEntity.ok(milestone);
        } else {
            return ResponseEntity.status(400).body(milestone);
        }
    }

    /*
    The getAllMilestonesByOrderId method accepts an id path variable and a Principal object as input.
    It uses the milestoneService to retrieve a list of milestones associated with the given order ID from the database. If the list is not empty,
    it returns an HTTP status code of 200 (OK) and the list of milestones in the response.
    If the list is empty, it creates an ApiError object and returns an HTTP status code of 404 (Not Found) and the ApiError object in the response.
     */
    @GetMapping("/getAllMilestonesByOrderId/{id}")
    public ResponseEntity<List<?>> createOrder(@PathVariable int id,Principal principal) {
        System.out.println("Sec: "+SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        var milestones = milestoneService.getAllMilestonesByOrderId(id);
        if(milestones.size() != 0) {
            return ResponseEntity.ok(milestones);
        } else {
            ApiError err = new ApiError();
            err.setMessage("No milestones found with such order id");
            err.setStatus(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(404).body(List.of(err));
        }
    }

    /*
    The completeMilestone method accepts a milestoneId path variable as input and uses the milestoneService to mark the milestone with the given ID as complete.
    If the milestone was successfully marked as complete, it returns an HTTP status code of 200 (OK) and the result of the operation in the response.
    If the milestone with the given ID was not found, it creates an ApiError object and returns an HTTP status code of 404 (Not Found) and the ApiError object in the response.
    */
    @GetMapping("/completeMilestone/{milestoneId}")
    public ResponseEntity<?> completeMilestone(@PathVariable int milestoneId) {
        int result = milestoneService.completeMilestone(milestoneId);
        if (result == 0) {
            return ResponseEntity.status(200).body(result);
        } else {
            ApiError err = new ApiError();
            err.setMessage("No milestone found with such milestone id");
            err.setStatus(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(404).body(err);
        }
    }

}
