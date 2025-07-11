package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArchiveRepository extends JpaRepository<com.example.demo.entity.Archive, UUID> {
}
