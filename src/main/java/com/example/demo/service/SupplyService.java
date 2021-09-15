package com.example.demo.service;

import com.example.demo.entity.SupplyEntity;
import com.example.demo.exception.SupplyException;

import java.util.List;

public interface SupplyService {
    void addNewSupply(SupplyEntity supply) throws SupplyException;

    List<SupplyEntity> getAll();
}
