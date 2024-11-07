package com.genaro1024.xmlapi.xmlapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.genaro1024.xmlapi.xmlapi.service.XmlServiceClient;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/xmlschema")
@CrossOrigin(origins="*")
@Slf4j
public class XMLSchemaController {
    @Autowired
    private XmlServiceClient xmlServiceClient;

     /**
     * @return returns all the XML 
     */  
    @Operation(summary = "Get xml", description = "Get xml")
    @GetMapping(path="/all", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> getAllXML() {
        Mono<String> result = xmlServiceClient.getXMLSchema();
        log.info(result.block());
        return ResponseEntity.ok(result.block());
    }   
    
 
    /**
     * @param search search text
     * @return returns the XML by search 
     */
    @Operation(summary = "Get xml by search", description = "Get xml by search")
    @GetMapping(path="/search/{text}",produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> getXMLBySeach(@PathVariable String text) {
        Mono<String> result = xmlServiceClient.getXMLSchemaBySearch(text);
        log.info(result.block());
        return ResponseEntity.ok(result.block());
    }      

}
