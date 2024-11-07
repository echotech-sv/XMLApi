package com.genaro1024.xmlapi.xmlapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
class XmlapiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

	@Test
	public void testGetAllXML() throws Exception {
        mockMvc.perform(get("/xmlschema/all").characterEncoding("UTF-8"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_XML))
            .andExpect(content().string(containsString("xs:schema")));	
	}


	@Test
	public void testXMLBySeach() throws Exception {
        mockMvc.perform(get("/xmlschema/search/{text}","The element element can be used either").characterEncoding("UTF-8"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_XML))
            .andExpect(content().string(containsString("xs:documentation")));	
	}    

}
