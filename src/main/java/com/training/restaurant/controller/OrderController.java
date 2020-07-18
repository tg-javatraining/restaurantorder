package com.training.restaurant.controller;
import com.training.restaurant.RestaurantOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantorder")
@Slf4j

public class OrderController {
    @GetMapping("/getOrder")
    public ResponseEntity<String> getOrder
            (@RequestParam(value="consumerId") String consumerId){
        log.info("Calling order service for consumer id : {}",consumerId);
        ResponseEntity<String> responseEntity = new ResponseEntity<>
                ("Welcome to our new restaurant", HttpStatus.OK);
        return responseEntity;
   }

    @PostMapping("/createOrder")
    public ResponseEntity<RestaurantOrderDto> createRestaurantOrder
            (@RequestBody RestaurantOrderDto restaurantOrderDto) {
        ResponseEntity<RestaurantOrderDto> responseEntity;

        if (true) {
            responseEntity = new ResponseEntity<>
                    (restaurantOrderDto, HttpStatus.OK);
        } else {

            responseEntity = new ResponseEntity<>
                    (restaurantOrderDto, HttpStatus.BAD_REQUEST);

        }
        return responseEntity;
        }


}


//http://localhost:8001/restaurantorder/getOrder?consumerId=1