/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.progparttwopoe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DC
 */
public class MessageTest {

    private static Message instance;

    @BeforeClass
    public static void setUpClass() {
        // enable headless mode for GitHub
        System.setProperty("java.awt.headless", "true");
        instance = new Message(1);
    }

    @Before
    public void setUp() {
        instance.setRecipient("0821234567");
        instance.setMessageText("Hello this is a test message");
    }

    @Test
    public void testCheckMessageID() {
        assertTrue(instance.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell_valid() {
        assertTrue(instance.checkRecipientCell("0821234567"));
    }

    @Test
    public void testValidateMessageLength_withinLimit() {
        String result = instance.validateMessageLength("short message");
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testCreateMessageHash_notEmpty() {
        String hash = instance.createMessageHash();
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    @Test
    public void testPrintMessageDetails_containsMessageID() {
        String details = instance.printMessageDetails();
        assertTrue(details.contains("MessageID"));
    }

    @Test
    public void testSetAndGetRecipient() {
        instance.setRecipient("0725555555");
        assertEquals("0725555555", instance.getRecipient());
    }

    @Test
    public void testSetAndGetMessageText() {
        instance.setMessageText("New message text");
        assertEquals("New message text", instance.getMessageText());
    }

    @Test
    public void testGetMessageId_notNull() {
        assertNotNull(instance.getMessageId());
    }

    @Test
    public void testGetMessageNumber_returnsInt() {
        assertTrue(instance.getMessageNumber() > 0);
    }

    @Test
    public void testStoreMessage_createsJson() {
        // Should not throw any exception
        instance.storeMessage();
        assertTrue(true);
    }
}