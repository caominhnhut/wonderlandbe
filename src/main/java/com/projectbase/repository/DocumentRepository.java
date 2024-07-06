package com.projectbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectbase.entity.DocumentEntity;
import com.projectbase.factory.EntityStatus;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>{

    @Query("SELECT d FROM DocumentEntity d WHERE d.fileName = :fileName AND d.status = :status")
    DocumentEntity findByFilenameAndStatus(@Param("fileName") String fileName, @Param("status") EntityStatus status);
}
