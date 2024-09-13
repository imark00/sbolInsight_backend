package com.mark.sbol_insight_backend.visualize;

import com.mark.sbol_insight_backend.utils.Elements;
import com.mark.sbol_insight_backend.utils.FileUtils;
import org.apache.jena.rdf.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;

@RestController
public class VisualizeController {


    @PostMapping(value = "/api/graph-data", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getGraphData(@RequestParam("file") MultipartFile multipartFile) {
        HashMap<String, Object> responseBody = new HashMap<>();

        try {
            File file = FileUtils.convertMultipartFileToFile(multipartFile);

            if (!file.canRead()) {
                responseBody.put("status", "error");
                responseBody.put("message", "Cannot read file");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }

            // create an empty model
            Model model = ModelFactory.createDefaultModel();
            model.read(file.getAbsolutePath());

            // list the statements in the graph
            StmtIterator statements = model.listStatements();

            // System.out.println("\nNode\tEdge\tNode\n");
            Elements ele = new Elements();
            while (statements.hasNext()) {
                Statement stmt = statements.nextStatement();

                Resource sourceNode = stmt.getSubject();
                String sNodeName = sourceNode.getLocalName();
                ele.addNode(sourceNode, sNodeName);

                RDFNode targetNode = stmt.getObject();
                String tNodeName = (targetNode.canAs(Resource.class) ? (!targetNode.asResource().getLocalName().isEmpty()) ? targetNode.asResource().getLocalName() : targetNode.toString() : targetNode.toString());
                ele.addNode(targetNode, tNodeName);

                Property edge = stmt.getPredicate();
                String edgeName = edge.getLocalName();
                ele.addEdge(edgeName, sNodeName, tNodeName);

//                System.out.print(sourceNode + " " + edge + " " + targetNode + "\n\n");
//                System.out.print(sNodeName + " " + edgeName + " " + tNodeName + "\n");
            }

            responseBody.put("status", "success");
            responseBody.put("elements", ele.getElements());

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        } catch (Exception e) {
            responseBody.clear();
            responseBody.put("status", "fail");
            responseBody.put("message", "Something went wrong");
            responseBody.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

    }

}
