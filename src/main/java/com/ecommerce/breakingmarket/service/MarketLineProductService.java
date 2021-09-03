package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.repository.LineProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketLineProductService {
    @Autowired
    LineProductRepository lineProductRepository;

    public ArrayList<LineProduct> getAllLines(){
        return (ArrayList<LineProduct>) lineProductRepository.findAll();
    }

}
