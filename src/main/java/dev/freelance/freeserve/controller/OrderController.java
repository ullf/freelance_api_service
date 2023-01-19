package dev.freelance.freeserve.controller;

import dev.freelance.freeserve.entity.AbstractOrder;
import dev.freelance.freeserve.entity.ApiError;
import dev.freelance.freeserve.service.AbstractOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class OrderController {

    private final AbstractOrderService abstractOrderService;

    /*
    The createOrder method accepts an AbstractOrder object and an HttpServletRequest object as input and uses the abstractOrderService to save the order to the database.
    If the order was saved successfully, it returns an HTTP status code of 200 (OK) and the AbstractOrder object in the response.
    If there was an error saving the order, it returns an HTTP status code of 400 (Bad Request) and an error message in the response.
    */
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody AbstractOrder ord, HttpServletRequest request) {
      var order = abstractOrderService.createOrder(ord);
      if(order.getAbstractName() != null && !order.getClientsId().getNickname().equals(null)) {
          return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().body("Unknown error,authentication could be needed");
        }
    }

    /*
    The takeOrder method accepts an orderId path variable as input and uses the abstractOrderService to retrieve the order with the given ID from the database.
    If the order is found, it returns an HTTP status code of 200 (OK) and the AbstractOrder object in the response. If the order is not found, it returns an HTTP
    status code of 404 (Not Found) and an error message in the response.
     */
    @PostMapping("/takeOrder/{orderId}")
    public ResponseEntity<?> takeOrder(@PathVariable int orderId) {
        var order = abstractOrderService.takeOrder(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(404).body("No order found with such order id");
        }
    }

    /*
    The getTakenOrders method accepts a clientId path variable as input and uses the abstractOrderService to retrieve a list of orders
    taken by the client with the given ID from the database.
    If the list is not empty, it returns an HTTP status code of 200 (OK) and the list of orders in the response.
    If the list is empty, it returns an HTTP status code of 404 (Not Found) and the empty list in the response.
     */
    @GetMapping("/getTakenOrders/{clientId}")
    public ResponseEntity<List<?>> getTakenOrders(@PathVariable int clientId) {
        var orders = abstractOrderService.getTakenOrders(clientId);
        if (orders.size() != 0) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(404).body(orders);
        }
    }

    /*
    The checkOrder method accepts an orderId path variable as input and uses the abstractOrderService to retrieve the order with the given ID from the database.
    If the order is found, it returns an HTTP status code of 200 (OK) and the AbstractOrder object in the response. If the order is not found,
    it creates an ApiError object and returns an HTTP status code of 404 (Not Found) and the ApiError object in the response.
     */
    @GetMapping("/checkOrder/{orderId}")
    public ResponseEntity<?> checkOrder(@PathVariable int orderId) {
        var order = abstractOrderService.checkOrder(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            ApiError err = new ApiError();
            err.setMessage("No order found with such order id");
            err.setStatus(HttpStatus.NOT_FOUND);
            Optional<ApiError> op_err = Optional.of(err);
            return ResponseEntity.of(op_err);
        }
    }

    /*
    The createOrder method accepts three path variables: clientId, name, and description, and uses them to create a new order object.
    The abstractOrderService is then used to save the order object to the database. If the order was saved successfully,
    it returns an HTTP status code of 200 (OK) and the AbstractOrder object in the response. If the order was not saved successfully,
    it returns an HTTP status code of 404 (Not Found).
     */
    @GetMapping("/createOrder/{clientId}/{name}/{description}")
    public ResponseEntity<AbstractOrder> createOrder(@PathVariable int clientId,@PathVariable String name,@PathVariable String description) {
        var order = abstractOrderService.createOrder(clientId,name,description);
        if(order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /*
    The getAllOrdersByClientId method accepts an id path variable as input and uses the abstractOrderService to retrieve a list of orders associated with the given client ID from the database.
    If the list is not empty, it returns an HTTP status code of 200 (OK) and the list of orders in the response.
    If the list is empty, it creates an ApiError object and returns an HTTP status code of 404 (Not Found) and the ApiError object in the response.
    */
    @GetMapping("/getAllOrdersByClientId/{id}")
    public ResponseEntity<List<?>> createOrder(@PathVariable int id) {
        var orders = abstractOrderService.getAllOrdersById(id);
        if(orders.size() != 0) {
            return ResponseEntity.ok(orders);
        } else {
            ApiError err = new ApiError();
            err.setMessage("No orders found with such client id");
            err.setStatus(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(404).body(List.of(err));
        }
    }

    /*
    The completeOrder method accepts an orderId path variable as input and uses the abstractOrderService to mark the order with the given ID as complete.
    If the order was successfully marked as complete, it returns an HTTP status code of 200 (OK) and the result of the operation in the response.
    If the order with the given ID was not found, it creates an ApiError object and returns an HTTP status code of 404 (Not Found) and the ApiError object in the response.
    */
    @GetMapping("/completeOrder/{orderId}")
    public ResponseEntity<?> completeOrder(@PathVariable int orderId) {
        int result = abstractOrderService.completeOrder(orderId);
        if (result == 0) {
            return ResponseEntity.status(200).body(result);
        } else {
            ApiError err = new ApiError();
            err.setMessage("No order found with such order id");
            err.setStatus(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(404).body(err);
        }
    }
}
