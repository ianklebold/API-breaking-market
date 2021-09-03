package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.service.MarketLineProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breakingmarket/v1/linesproduct")
public class LineProductController {
    

    @Autowired
    MarketLineProductService marketLineProductService;

    @GetMapping("/alllines")
    public ArrayList<LineProduct> getAllLines(){
        return marketLineProductService.getAllLines();
    }


}
