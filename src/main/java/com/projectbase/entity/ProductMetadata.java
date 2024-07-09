package com.projectbase.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ProductMetadata{

    Map<String, String> images = new HashMap<>();
}
