package com.projectbase.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectbase.dto.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value ="/dashboards")
@Slf4j
public class DashboardController{

    @GetMapping
    public ResponseEntity<ResponseDto<Map<String, Integer>>> getAll() {
        log.info("Processing for dashboard");
        return ResponseEntity.ok(ResponseDto.response(Map.of()));
    }
}
