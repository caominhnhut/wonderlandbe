package com.projectbase.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.projectbase.dto.BillDto;
import com.projectbase.entity.BillEntity;
import com.projectbase.model.Bill;

@Component
@Mapper(componentModel = "spring")
public interface BillMapper{

    Bill toModel(BillDto billDto);

    @Mapping(target = "createdDate", ignore = true)
    BillEntity toEntity(Bill bill);

    BillDto fromModel(Bill bill);

    Bill fromEntity(BillEntity billEntity);
}
