package com.simpleWebRest.SimpleWebRest.repository;

import com.simpleWebRest.SimpleWebRest.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  DocumentRepository extends JpaRepository<Document, Long> {
}
