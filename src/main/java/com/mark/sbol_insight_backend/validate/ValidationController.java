package com.mark.sbol_insight_backend.validate;


import com.mark.sbol_insight_backend.utils.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.StmtIterator;
import org.sbolstandard.core3.entity.SBOLDocument;
import org.sbolstandard.core3.io.SBOLFormat;
import org.sbolstandard.core3.io.SBOLIO;
import org.sbolstandard.core3.util.SBOLGraphException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ValidationController {

    @PostMapping(value = "/api/validate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> validate(@RequestParam("file") MultipartFile multipartFile) {

        HashMap<String, Object> responseBody = new HashMap<>();
        SBOLDocument doc;
        SBOLFormat format; //todo: determine what format to use from the file extension


        try {
            File file = FileUtils.convertMultipartFileToFile(multipartFile);

            //todo: determine the appropriate file extension of the file in the payload
            // Get File extension
            // FilenameUtils.getExtension(filePath);

            // Check if the file's content type is acceptable
//            if (!Objects.equals(multipartFile.getContentType(), "application/rdf+xml") && !Objects.equals(multipartFile.getContentType(), "text/turtle")) {
//
//                responseBody.put("status", "error");
//                responseBody.put("message", "Unsupported content type: " + multipartFile.getContentType());
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
//            }

            //todo: figure out how to read file
            // File file = new File(multipartFile);

            if (!file.canRead()) {
                responseBody.put("status", "error");
                responseBody.put("message", "Cannot read file");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }

            doc = SBOLIO.read(file, SBOLFormat.TURTLE);
            Model model = doc.getRDFModel();
            StmtIterator statements = model.listStatements();
            List<String> statementList = new ArrayList<>();


            if (doc.getRDFModel().listStatements() != null) {
                while (statements.hasNext()) {
                    statementList.add(statements.nextStatement().toString() + "\n");
                }
            }
            responseBody.put("status", "success");
            responseBody.put("totalStmts", statementList.size());
            responseBody.put("stmts", statementList);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        } catch (Exception e) {
            if (e instanceof SBOLGraphException) {
                // responseBody.clear();
                responseBody.put("status", "error");
                responseBody.put("message", "Invalid SBOL Document");
                responseBody.put("error", e.getMessage());

                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }

            responseBody.clear();
            responseBody.put("status", "fail");
            responseBody.put("message", "Something went wrong");
            responseBody.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

}
