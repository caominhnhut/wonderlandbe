package com.projectbase.service;

import java.util.List;

import com.projectbase.model.Bill;

public interface BillService{

    String generate(Bill bill);

    List<Bill> findAll();

    byte[] downloadReport(String uuid);

}
