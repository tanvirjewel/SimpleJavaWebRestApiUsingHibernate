package com.simpleWebRest.SimpleWebRest.controller;

import com.simpleWebRest.SimpleWebRest.exception.DocumentNotFoundException;
import com.simpleWebRest.SimpleWebRest.repository.DocumentRepository;
import com.simpleWebRest.SimpleWebRest.service.FileStorageService;
import com.simpleWebRest.SimpleWebRest.domain.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
class DocumentController {

    private final DocumentRepository repository;
    @Autowired
    FileStorageService fileStorageService;

    DocumentController(DocumentRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("documents")
    List<Document> all() {
        return repository.findAll();
    }

    @PostMapping("/document")
    Document newDocument(@RequestBody Document newDocument) {
        return repository.save(newDocument);
    }

    // Single item

    @GetMapping("/document/{id}")
    Document one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException(id));
    }

    @PutMapping("/documents/{id}")
    Document replaceDocument(@RequestBody Document newDocument, @PathVariable Long id) {

        return repository.findById(id)
                .map(document -> {
                    document.setId(newDocument.getId());
                    document.setExpireDate(newDocument.getExpireDate());
                    document.setIssuePlace(newDocument.getIssuePlace());
                    document.setLicenseNumber(newDocument.getLicenseNumber());
                    document.setNid(newDocument.getNid());
                    document.setType(newDocument.getType());
                    return repository.save(document);
                })
                .orElseGet(() -> {
                    newDocument.setId(id);
                    return repository.save(newDocument);
                });
    }

    @DeleteMapping("/document/{id}")
    void deleteDocument(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @RequestMapping(value = "/upload-document-image", method = RequestMethod.POST)
    @ResponseBody
    public String uploadRefugeDocument(@RequestParam("file") MultipartFile file){
        if(!file.isEmpty() && file.getSize() > 0){
            String filePath = fileStorageService.storeFile(file);
            if (!filePath.isEmpty() && filePath != null){
                return filePath;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
