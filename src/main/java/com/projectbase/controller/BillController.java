package com.projectbase.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectbase.dto.BillDto;
import com.projectbase.dto.ResponseDto;
import com.projectbase.factory.ValidationType;
import com.projectbase.mapper.BillMapper;
import com.projectbase.model.Bill;
import com.projectbase.service.BillService;
import com.projectbase.validator.ValidatorProvider;

@RestController
@RequestMapping(value ="/bills")
public class BillController{

    @Autowired
    private BillService billService;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private ValidatorProvider validatorProvider;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<String>> generateBill(@RequestBody BillDto billDto) {

        //validatorProvider.execute(ValidationType.BILL_CREATION, billDto);

        Bill bill = billMapper.toModel(billDto);
        String categoryId = billService.generate(bill);

        return ResponseEntity.ok(ResponseDto.response(categoryId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<BillDto>>> findAllBills() {

        List<BillDto> bills = billService.findAll().stream().map(billMapper::fromModel).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDto.response(bills));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{uuid}")
    public ResponseEntity downloadPDF(@PathVariable("uuid") String uuid) {

        byte[] fileContent = billService.downloadReport(uuid);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uuid + "\".pdf")
                .body(new ByteArrayResource(fileContent));
    }
}
