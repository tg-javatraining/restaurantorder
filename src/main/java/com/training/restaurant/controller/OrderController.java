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
import java.util.stream.Collectors;

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

         responseEntity = saveOrder(restaurantOrderDto, restaurantOrder);

        return responseEntity;
    }


    @GetMapping("/getOrders")
    public ResponseEntity<List<RestaurantOrderDto>> getOrders() {
        log.info("Fetch all orders : ");
        List<RestaurantOrder> orderList = restaurantOrderRepository.findAll();
        List<RestaurantOrderDto> orderdtolist = orderList.stream().map(restaurantOrder -> new RestaurantOrderDto(
                restaurantOrder.getCustomerId(), restaurantOrder.getOrderDetails(), "success ", true))
                .collect(Collectors.toList());

        ResponseEntity<List<RestaurantOrderDto>> responseEntity = new ResponseEntity<>
                (orderdtolist, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/getOrderForCustomer")
    public ResponseEntity<List<RestaurantOrderDto>> getOrderForCustomer
            (@RequestParam(value = "customerId") String customerId) {
        log.info("Calling order service for consumer id : {}", customerId);
        List<RestaurantOrder> orderList = restaurantOrderRepository.findByCustomerId(customerId);
        List<RestaurantOrderDto> orderdtolist = orderList.stream().map(restaurantOrder -> new RestaurantOrderDto(
                restaurantOrder.getCustomerId(), restaurantOrder.getOrderDetails(), "success ", true))
                .collect(Collectors.toList());

        ResponseEntity<List<RestaurantOrderDto>> responseEntity = new ResponseEntity<>
                (orderdtolist, HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<RestaurantOrderDto> updateRestaurantOrder
            (@RequestBody RestaurantOrderDto restaurantOrderDto) {
        ResponseEntity<RestaurantOrderDto> responseEntity;
        RestaurantOrder restaurantOrder = new RestaurantOrder();
        BeanUtils.copyProperties(restaurantOrderDto, restaurantOrder);

        List<RestaurantOrder> orderList = restaurantOrderRepository.findByCustomerId(restaurantOrder.getCustomerId());
        if (orderList.size() == 0) {
            log.info("This user id not exist");

            responseEntity = saveOrder(restaurantOrderDto, restaurantOrder);

        } else {
            RestaurantOrder customerOrder = orderList.get(orderList.size()-1);
            customerOrder.setOrderDetails(restaurantOrder.getOrderDetails());

            responseEntity = saveOrder(restaurantOrderDto, customerOrder);

        }



        return responseEntity;
    }

    private ResponseEntity<RestaurantOrderDto> saveOrder(@RequestBody RestaurantOrderDto restaurantOrderDto, RestaurantOrder restaurantOrder) {
        ResponseEntity<RestaurantOrderDto> responseEntity;
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

    private ResponseEntity<RestaurantOrderDto> deleteOrder(@RequestBody RestaurantOrderDto restaurantOrderDto, RestaurantOrder restaurantOrder) {
        ResponseEntity<RestaurantOrderDto> responseEntity;
        try {

            restaurantOrderRepository.delete(restaurantOrder);
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

}


//http://localhost:8001/restaurantorder/getOrder?consumerId=1