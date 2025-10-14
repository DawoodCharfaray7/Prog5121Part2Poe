/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progparttwopoe;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
/**
 *
 * @author DC
 */
class Message {
    
    private String messageId;        
    private int messageNumber;       
    private String recipient;       
    private String messageText;

    private static final int MESSAGE_ID_LENGTH = 10;
    
    private static final Pattern CELL_REGEX = Pattern.compile("^(\\+\\d{11}|0\\d{9})$");

    public Message(int messageNumber) {
        this.messageNumber = messageNumber;
        this.messageId = generateMessageId();
    }

    private String generateMessageId() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MESSAGE_ID_LENGTH; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    // Ensure message ID length not more than 10
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }
    
    public boolean checkRecipientCell(String cellNumber) {
    if (CELL_REGEX.matcher(cellNumber).matches()) {
        return true;
    } else {
        JOptionPane.showMessageDialog(null, "Invalid cell number format. Please try again.");
        return false;
    }
    }

    public static final int MAX_MESSAGE_LENGTH = 250;

    public String validateMessageLength(String text) {
        if (text == null) return "Message exceeds " + MAX_MESSAGE_LENGTH + " characters by " + MAX_MESSAGE_LENGTH + ", please reduce size.";
        int len = text.length();
        if (len <= MAX_MESSAGE_LENGTH) {
            return "Message ready to send.";
        } else {
            int over = len - MAX_MESSAGE_LENGTH;
            return "Message exceeds " + MAX_MESSAGE_LENGTH + " characters by " + over + ", please reduce size.";
        }
    }

    public String createMessageHash() {
        String firstTwo = (messageId.length() >= 2) ? messageId.substring(0,2) : String.format("%02d", 0);
        String firstLast = extractFirstAndLastWords(messageText);
        return (firstTwo + ":" + messageNumber + ":" + firstLast).toUpperCase();
    }

    private String extractFirstAndLastWords(String text) {
        if (text == null || text.trim().isEmpty()) return "";
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return first + last;
    }

    // Present options for send/disregard/store and return the user's choice string
    public String SentMessageAction() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an action for this message:",
                "Send / Discard / Store",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            JOptionPane.showMessageDialog(null, "Message successfully sent.");
            return "SENT";
        } else if (choice == 1) {
//            JOptionPane.showMessageDialog(null, "Press 0 to delete message."); - Part 3
            return "DISREGARDED";
        } else if (choice == 2) {
            JOptionPane.showMessageDialog(null, "Message successfully stored.");
            return "STORED";
        } else {
            // if closed, treat as disregarded
//            JOptionPane.showMessageDialog(null, "Press 0 to delete message."); - Part 3
            return "DISREGARDED";
        }
    }

    public String printMessageDetails() {
        // Display MessageID, Message Hash, Recipient, Message in this order
        String details = "MessageID: " + messageId + "\n" +
                "Message Hash: " + createMessageHash() + "\n" +
                "Recipient: " + recipient + "\n" +
                "Message: " + messageText;
        return details;
    }

    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    
    @SuppressWarnings("unchecked")  // allows use of raw JSONSimple types

public void storeMessage() {
    JSONObject messageObj = new JSONObject();
    messageObj.put("MessageID", messageId);
    messageObj.put("Recipient", recipient);
    messageObj.put("Message", messageText);
    messageObj.put("MessageHash", createMessageHash());

    // Read existing JSON array (if file already exists)
    JSONArray messagesArray = new JSONArray();
    try (FileReader reader = new FileReader("messages.json")) {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(reader);
        messagesArray = (JSONArray) obj;
    } catch (IOException | ParseException e) {
       
    }

    // Add the new message
    messagesArray.add(messageObj);

    // Write back to file
    try (FileWriter file = new FileWriter("messages.json")) {
        file.write(messagesArray.toJSONString());
        file.flush();
        JOptionPane.showMessageDialog(null, "Message successfully stored to JSON file.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error saving message to JSON: " + e.getMessage());
    }
}
}
    
