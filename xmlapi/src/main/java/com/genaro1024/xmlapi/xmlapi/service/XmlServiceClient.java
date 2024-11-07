package com.genaro1024.xmlapi.xmlapi.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.genaro1024.xmlapi.xmlapi.exception.ResourceNotFoundException;
import reactor.core.publisher.Mono;

@Service
public class XmlServiceClient {
    private final String baseUrl="https://www.w3.org/2001";
    private final WebClient webClient;

    public XmlServiceClient() {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }
    public Mono<String> getXMLSchema() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/XMLSchema").build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    if (response == null) {
                        return Mono.error(new ResourceNotFoundException("El servicio XML no devolvió ningún resultado"));
                    } else {
                        return Mono.just(response);
                    }
                });
    } 
    
    public Mono<String> getXMLSchemaBySearch(String text) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/XMLSchema").build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    StringBuilder stringBuilderMatchingNodes = new StringBuilder();
                    if (response == null) {
                        return Mono.error(new ResourceNotFoundException("El servicio XML no devolvió ningún resultado"));
                    } else {
                            try {
                                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
                                DocumentBuilder builder = factory.newDocumentBuilder();
                                InputStream inputStream = new ByteArrayInputStream(response.getBytes());
                                Document document = builder.parse(inputStream);
                                Element root = document.getDocumentElement();
                                NodeList documentationList = root.getElementsByTagName("xs:documentation");

                                for (int i = 0; i < documentationList.getLength(); i++) {
                                    Node documentationNode = documentationList.item(i);
                                    String documentationContent = documentationNode.getTextContent();
                                    if (documentationContent.contains(text)){
                                        String textNode = convertNodeToString(documentationNode);
                                        stringBuilderMatchingNodes.append(textNode).append("\n");
                                    }

                                }
                                inputStream.close();

                            } catch (IOException | SAXException | ParserConfigurationException e) {
                               Mono.error(e);
                            }                           

                            return Mono.just(stringBuilderMatchingNodes.toString());
                    }
                });
    }     
    
    

    public static String convertNodeToString(Node node) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(node), new StreamResult(sw));
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    

}
