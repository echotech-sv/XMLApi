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
    public Mono<ResponseEntity<String>> getAllXML() {
        return xmlServiceClient.getXMLSchema()
                .doOnNext(result -> log.info(result))
                .map(ResponseEntity::ok);
    }   
    
 

    /**
     * Retrieves an XML document based on the provided search text.
     *
     * @param text the search text used to find the XML document
     * @return a Mono emitting a ResponseEntity containing the XML document as a string
     */
    @Operation(summary = "Get xml by search", description = "Get xml by search")
    @GetMapping(path="/search/{text}", produces = MediaType.TEXT_XML_VALUE)
    public Mono<ResponseEntity<String>> getXMLBySearch(@PathVariable String text) {
        return xmlServiceClient.getXMLSchemaBySearch(text)
                .doOnNext(result -> log.info(result))
                .map(ResponseEntity::ok);
    }
    

    /**
     * @param input the input string
     * @return returns the same input string
     */
    @GetMapping(path="/echo/{input}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> echo(@PathVariable String input) {
        return ResponseEntity.ok(input);
    }

    //create a method that receives a string and returns it in uppercase
    @GetMapping(path="/uppercase/{input}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uppercase(@PathVariable String input) {
        return ResponseEntity.ok(input.toUpperCase());
    }

    @GetMapping(path="/lowercase/{input}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> lowercase(@PathVariable String input) {
        return ResponseEntity.ok(input.toLowerCase());
    }
    

    
    

}
