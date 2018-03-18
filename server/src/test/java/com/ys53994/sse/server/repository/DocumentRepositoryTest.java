package com.ys53994.sse.server.repository;

import com.google.common.collect.ImmutableSet;
import com.ys53994.sse.server.domain.Document;
import com.ys53994.sse.server.repository.impl.DocumentRepositoryImpl;
import com.ys53994.sse.server.storage.impl.SimpleInMemoryDocumentStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.google.common.collect.ImmutableList.of;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class DocumentRepositoryTest {

    private DocumentRepository documentStorage;

    @Before
    public void setUp() {
        documentStorage = new DocumentRepositoryImpl(new SimpleInMemoryDocumentStorage());
    }


    @Test
    public void shouldSaveDocument() {
        final String documentKey = "1";
        assertFalse("Document should not be found ", documentStorage.find(Document.DocumentKey.of(documentKey)).isPresent());

        documentStorage.save(Document.of(Document.DocumentKey.of(documentKey), "document content"));

        assertTrue("Document should be saved successfully", documentStorage.find(Document.DocumentKey.of(documentKey)).isPresent());
    }

    @Test
    public void shouldFindDocumentByGivenId() {
        final Document document = Document.of(Document.DocumentKey.of("1"), "this is example of document content");

        documentStorage.save(document);

        assertTrue("Document should be found by given id", documentStorage.find(Document.DocumentKey.of("1")).isPresent());
        assertEquals("Documents should be equals", document, documentStorage.find(Document.DocumentKey.of("1")).get());
        assertFalse("Return empty if not found", documentStorage.find(Document.DocumentKey.of("not exists")).isPresent());
    }

    @Test
    public void shouldSearchDocumentsByTokens() {
        final Document document = Document.of(Document.DocumentKey.of("1"), "this is example of document content");
        final Document document2 = Document.of(Document.DocumentKey.of("2"), "another one document");

        documentStorage.save(document);
        documentStorage.save(document2);

        assertEquals("Should find 2 documents by given tokens", 2, documentStorage.search(Query.of(of("example", "another"))).size());
        assertEquals("Should find 1 document by given tokens", 1, documentStorage.search(Query.of(of("example", "blabla"))).size());
        assertEquals("Should find expected keys by tokens", ImmutableSet.of(Document.DocumentKey.of("1"), Document.DocumentKey.of("2")),
                documentStorage.search(Query.of(of("example", "document"))));
    }

    @Test
    public void shouldNotOverrideDocumentIfItAlreadyExists() {
        final Document.DocumentKey key = Document.DocumentKey.of("1");
        final String expectedContent = "content of first document";

        documentStorage.save(Document.of(key, expectedContent));
        documentStorage.save(Document.of(key, "content of second document"));

        assertEquals("Document should not be override and return content of first document", expectedContent, documentStorage.find(key).get().getData());

    }


}
