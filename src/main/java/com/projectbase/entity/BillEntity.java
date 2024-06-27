package com.projectbase.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "bill")
@Data
@DynamicUpdate
@DynamicInsert
@RequiredArgsConstructor
@AllArgsConstructor
public class BillEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "detailed_bill", columnDefinition = "json")
    private String detailedBill;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private OffsetDateTime createdDate = OffsetDateTime.now();
}
