package com.ys53994.sse.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.ys53994.sse.client.service.DocumentServiceClient;
import com.ys53994.sse.common.model.DocumentDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.ys53994.sse.client.cli.CliManager.USAGE_CMD_LINE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClientTest {

    @Autowired
    private Client application;

    @MockBean
    DocumentServiceClient documentServiceClient;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    private final DocumentDto expectedDocument = new DocumentDto("key1", "there is document content");

    @Before
    public void setUp() {
        outputCapture.reset();
        Mockito.when(documentServiceClient.get(expectedDocument.getKey())).thenReturn(of(expectedDocument));
        Mockito.when(documentServiceClient.get("not exists")).thenReturn(empty());
        Mockito.when(documentServiceClient.search(ImmutableList.of("document"))).thenReturn(ImmutableSet.of(expectedDocument.getKey()));
    }

    @Test
    public void shouldDisplayUsageInfoInCaseWhenNoAnyArguments() {
        application.run();
        assertTrue(outputCapture.toString().contains(USAGE_CMD_LINE));
    }

    @Test
    public void shouldDisplayUsageInfoIfHelpCommandEntered() {
        application.run("-h");
        assertTrue(outputCapture.toString().contains(USAGE_CMD_LINE));
    }

    @Test
    public void shouldGetDocumentIfGetCommandEntered() {
        application.run("-g", expectedDocument.getKey());
        assertTrue(outputCapture.toString().contains("Found document: " + expectedDocument.getKey() + " " + expectedDocument.getData()));
    }

    @Test
    public void shouldNotFoundDocumentInServerIfGetCommandEnteredWithNotExistsKey() {
        application.run("-g", "not exists");
        assertTrue(outputCapture.toString().contains("Document not found"));
    }

    @Test
    public void shouldSearchDocumentKeysIfSearchCommandEntered() {
        application.run("-s", "\"document\"");
        assertTrue(outputCapture.toString().contains("Found keys: " + expectedDocument.getKey()));
    }

    @Test
    public void shouldDisplayInvalidParameterErrorIfInvalidParameterEntered(){
        application.run("-p","invalid","parameter");
        assertTrue(outputCapture.toString().contains("Invalid input parameter"));
    }

    @Test
    public void shouldDisplayUsageInfoIfUnknownCommand(){
        application.run("-b");
        assertTrue(outputCapture.toString().contains(USAGE_CMD_LINE));
    }

    @Test
    public void shouldPutDocumentIfPutCommandEntered() {
        application.run("-p", "\"1 content data\"");
        assertTrue(outputCapture.toString().contains("Document has been added successfully"));
    }
}
