package com.mark.sbol_insight_backend;

import lombok.val;
import org.apache.jena.rdf.model.*;
import org.sbolstandard.core3.entity.SBOLDocument;
import org.sbolstandard.core3.io.SBOLFormat;
import org.sbolstandard.core3.io.SBOLIO;
import org.sbolstandard.core3.util.SBOLGraphException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
public class SBOLInsightBackendApplication {

    public static void main(String[] args) throws FileNotFoundException, SBOLGraphException {
        SpringApplication.run(SBOLInsightBackendApplication.class, args);

        System.out.println("Hello World!");

        boolean showDocumentInfo = true;

        if (showDocumentInfo) {

            String localFilePath = "C:\\Users\\ALIENWARE\\IdeaProjects\\sbol_insight_backend\\src\\main\\resources\\assets\\sbol_files\\toggle_switch.ttl";
            File file = new File(localFilePath);

            Model model = ModelFactory.createDefaultModel();
            model.read(localFilePath);

//            // list the statements in the graph
            StmtIterator iter = model.listStatements();
            System.out.println("Node\tEdge\tNode\n");
            // print out the predicate, subject and object of each statement
            while (iter.hasNext()) {
                Statement stmt = iter.nextStatement();         // get next statement
                Resource subject = stmt.getSubject();   // get the subject
                Property predicate = stmt.getPredicate(); // get the predicate
                RDFNode object = stmt.getObject();    // get the object
                System.out.println(subject + " " + predicate + " " + object);
                System.out.println(subject.getLocalName() + " " + predicate.getLocalName() + " " + object);
                if (object.canAs(Resource.class)) {
                    Resource objRes = object.asResource();
                    System.out.println(subject.getLocalName() + " " + predicate.getLocalName() + " " + objRes.getLocalName() + " " + object.canAs(Resource.class));
                }
                System.out.print("\n");

            }
//
//
//            System.out.println("Subject\t\tName\t\tNamespace");
//            ResIterator subjects = model.listSubjects();
//            while (subjects.hasNext()) {
//                Resource subject = subjects.next();
//                System.out.println(subject + " " + subject.getLocalName() + " " + subject.getNameSpace());
//            }
//
//            System.out.println("\nObject\t\tName\t\tNamespace");
//            NodeIterator objects = model.listObjects();
//            while (objects.hasNext()) {
//                RDFNode object = objects.next();
//                System.out.println(object + " ");
//            }


            SBOLDocument doc = SBOLIO.read(file, SBOLFormat.TURTLE);

            if (doc.getAgents() != null) {
                for (val c : doc.getAgents()) {
                    System.out.println("\nAgent " + c + "\n");
                }
            }

            if (doc.getSequences() != null) {
                for (val c : doc.getSequences()) {
                    System.out.println("\nSequence " + c + "\n");
                }
            }

            if (doc.getComponents() != null) {
                for (val c : doc.getComponents()) {
                    //System.out.println("\nComponent " + c);
                    System.out.println("\nComponent Description " + c.getDescription());
                    System.out.println("Component DisplayID " + c.getDisplayId());
                    System.out.println("Component Name " + c.getName());
                    System.out.println("Component URI " + c.getUri());
                    System.out.println("Component Namespace " + c.getNamespace());
                    System.out.println("Component Features " + c.getFeatures());
                    System.out.println("Component Interactions " + c.getInteractions());
                    System.out.println("Component Children " + c.getChildren() + "\n");
                }
            }

            if (doc.getModels() != null) {
                for (val c : doc.getModels()) {
                    System.out.println("\nModel " + c + "\n");
                }
            }

            if (doc.getImplementations() != null) {
                for (val c : doc.getImplementations()) {
                    System.out.println("\nImplementation " + c + "\n");
                }
            }

            if (doc.getValidationMessages() != null) {
                for (val c : doc.getValidationMessages()) {
                    System.out.println("\nValidationMessage " + c + "\n");
                }
            }

            if (doc.getAttachments() != null) {
                for (val c : doc.getAttachments()) {
                    System.out.println("\nAttachment " + c + "\n");
                }
            }

            if (doc.getActivities() != null) {
                for (val c : doc.getActivities()) {
                    System.out.println("\nActivity " + c + "\n");
                }
            }

            if (doc.getTopLevels() != null) {
                for (val c : doc.getTopLevels()) {
                    //System.out.println("\nTop Level " + c + "\n");
                    System.out.println("\nTop Level Name " + c.getName());
                    System.out.println("Top Level Description " + c.getDescription());
                    System.out.println("Top Level DisplayID " + c.getDisplayId());
                    System.out.println("Top Level Namespace " + c.getNamespace());
                    System.out.println("Top Level Children " + c.getChildren());
                    System.out.println("Top Level Attachments " + c.getAttachments());
                    System.out.println("Top Level Resource Type " + c.getResourceType() + "\n");
                }
            }

            if (doc.getCollections() != null) {
                for (val c : doc.getCollections()) {
                    //System.out.println("\nCollection " + c + "\n");
                    System.out.println("\nCollection Name " + c.getName());
                    System.out.println("Collection Description " + c.getDescription());
                    System.out.println("Collection Display ID " + c.getDisplayId());
                    System.out.println("Collection Namespace " + c.getNamespace());
                    System.out.println("Collection Members " + c.getMembers());
                    System.out.println("Collection Children " + c.getChildren());
                    System.out.println("Collection Resource Type " + c.getResourceType());
                    System.out.println("Collection Validation Messages " + c.getValidationMessages() + "\n");
                }
            }

            if (doc.getCombinatorialDerivations() != null) {
                for (val c : doc.getCombinatorialDerivations()) {
                    System.out.println("\nCombinatorial Derivation " + c + "\n");
                }
            }

            if (doc.getPlans() != null) {
                for (val c : doc.getPlans()) {
                    System.out.println("\nPlans " + c + "\n");
                }
            }

        }


    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/test").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/graph-data").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/validate").allowedOrigins("http://localhost:3000");
            }
        };
    }

}
