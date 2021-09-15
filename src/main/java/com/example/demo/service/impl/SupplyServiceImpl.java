package com.example.demo.service.impl;

import com.example.demo.entity.SupplyEntity;
import com.example.demo.exception.SupplyException;
import com.example.demo.service.SupplyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {
    @Override
    public void addNewSupply(SupplyEntity supply) throws SupplyException {

    }

    @Override
    public List<SupplyEntity> getAll() {
        return null;
    }
}
