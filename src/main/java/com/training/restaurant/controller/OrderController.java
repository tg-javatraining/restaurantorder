package com.training.restaurant.controller;

import com.training.restaurant.RestaurantOrder;
import com.training.restaurant.RestaurantOrderDto;
import com.training.restaurant.RestaurantOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurantorder")
@Slf4j

public class OrderController {

    @Autowired
    RestaurantOrderRepository restaurantOrderRepository;

    @GetMapping("/home")
    public ResponseEntity<String> home
            (@RequestParam(value = "consumerId") String consumerId) {
        log.info("Calling order service for consumer id : {}", consumerId);
        ResponseEntity<String> responseEntity = new ResponseEntity<>
                ("Welcome to our new restaurant", HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<RestaurantOrderDto> createRestaurantOrder
            (@RequestBody RestaurantOrderDto restaurantOrderDto) {
        ResponseEntity<RestaurantOrderDto> responseEntity;
        RestaurantOrder restaurantOrder = new RestaurantOrder();
        BeanUtils.copyProperties(restaurantOrderDto, restaurantOrder);

        try {

            restaurantOrderRepository.save(restaurantOrder);
            restaurantOrderDto.setMessage("Order has been saved  ");
            responseEntity = new ResponseEntity<>
                    (restaurantOrderDto, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error occured: ", e.getMessage());
            responseEntity = new ResponseEntity<>
                    (null, HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }


    @GetMapping("/getOrders")
    public ResponseEntity<String> getOrders() {
        log.info("Fetch all orders : ");
        List<RestaurantOrder> orderList = new ArrayList<>();
        orderList = restaurantOrderRepository.findAll();
        List<RestaurantOrderDto> orderdtolist = new ArrayList<>();





        ResponseEntity<String> responseEntity = new ResponseEntity<>
                ("Welcome to our new restaurant", HttpStatus.OK);
        return responseEntity;
    }



}


//http://localhost:8001/restaurantorder/getOrder?consumerId=1