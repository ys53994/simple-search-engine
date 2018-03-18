package com.ys53994.sse.server.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys53994.sse.common.model.DocumentDto;
import com.ys53994.sse.common.rest.RestApiDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldReturn404ErrorWhenKeyNotFoundInStorage() throws Exception {
        this.mockMvc.perform(get(RestApiDescriptor.DOCUMENT_PUBLIC_REST_API + "/notExistsKey")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404ErrorInCaseWhenNoAnyKeyFoundByToken() throws Exception {
        this.mockMvc.perform(get(RestApiDescriptor.DOCUMENT_PUBLIC_REST_API)
                .param(RestApiDescriptor.TOKEN_REQUEST_PARAM, "notExistsToken")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldSaveDocument() throws Exception {
        final DocumentDto expectedDocument = new DocumentDto("1", "document content");

        saveAndCheckDocument(expectedDocument);
        getAndCheckDocument(expectedDocument);
    }

    @Test
    public void shouldSearchDocumentsByTokens() throws Exception {
        final DocumentDto expectedDocument = new DocumentDto("key1", "text for search");


        saveAndCheckDocument(expectedDocument);

        this.mockMvc.perform(get(RestApiDescriptor.DOCUMENT_PUBLIC_REST_API)
                .param(RestApiDescriptor.TOKEN_REQUEST_PARAM, "text", "second")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0]").value("key1"))
                .andExpect(status().isOk());


    }

    private void getAndCheckDocument(DocumentDto expectedDocument) throws Exception {
        this.mockMvc.perform(get(RestApiDescriptor.DOCUMENT_PUBLIC_REST_API + "/" + expectedDocument.getKey())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.key").value(expectedDocument.getKey()))
                .andExpect(jsonPath("$.data").value(expectedDocument.getData()))
                .andExpect(status().isOk());
    }


    private void saveAndCheckDocument(DocumentDto documentDto) throws Exception {
        this.mockMvc.perform(post(RestApiDescriptor.DOCUMENT_PUBLIC_REST_API)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(documentDto)))
                .andExpect(status().isOk());
    }

}
