/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progparttwopoe;

import javax.swing.JOptionPane;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;

/**
 *
 * @author DC
 */
public class QuickChatApp {

    private Registration reg;
    private String loggedInUser;

    // arrays (sized at runtime)
    private String[] messageIDs;
    private String[] senders;
    private String[] recipients;
    private String[] messages;
    private String[] hashes;
    private String[] actions; // "SENT", "DISREGARDED", "STORED"

    private int capacity = 0;     // chosen by user
    private int count = 0;        // current number of stored messages in arrays

    public QuickChatApp() {
        reg = new Registration();
        // start with empty zero-length arrays to avoid NPEs
        capacity = 0;
        count = 0;
        messageIDs = new String[0];
        senders = new String[0];
        recipients = new String[0];
        messages = new String[0];
        hashes = new String[0];
        actions = new String[0];
    }

    public void run() {
        reg.registerUser();
        handleLogin();
        // capture username for "sender" reference (may be null if registration didn't set)
        loggedInUser = reg.getUsername();
        showMainMenu();
    }

    private void handleLogin() {
        boolean loggedIn = false;
        while (!loggedIn) {
            String userAttempt = JOptionPane.showInputDialog(null, "Login - Enter username:");
            String passAttempt = JOptionPane.showInputDialog(null, "Login - Enter password:");
            boolean success = reg.loginUser(userAttempt, passAttempt);
            String msg = reg.returnLoginStatus(success);
            JOptionPane.showMessageDialog(null, msg);
            if (success) loggedIn = true;
        }
    }

    private void showMainMenu() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        mainLoop:
        while (true) {
            String menu = """
                    Please choose an option:
                    1) Send Messages
                    2) Display sender & recipient of all sent messages
                    3) Display the longest sent message
                    4) Search for a message by Message ID
                    5) Search for all messages sent to a recipient
                    6) Delete a message using the message hash
                    7) Display full sent messages report
                    8) Load stored messages from JSON (optional)
                    9) Quit
                    """;
            String choice = JOptionPane.showInputDialog(null, menu);
            if (choice == null) continue;

            switch (choice.trim()) {
                case "1" -> sendMessages();
                case "2" -> displaySenderRecipientAll();
                case "3" -> displayLongestMessage();
                case "4" -> searchByMessageID();
                case "5" -> searchByRecipient();
                case "6" -> deleteByHash();
                case "7" -> displayFullReport();
                case "8" -> loadStoredMessages();
                case "9" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye.");
                    break mainLoop;
                }
                default -> JOptionPane.showMessageDialog(null, "Please select a number 1 - 9.");
            }
        }
    }

    // Ask user for capacity (only once), allocate arrays
    private boolean ensureCapacityInitialized() {
        if (capacity > 0) return true;
        while (true) {
            String capStr = JOptionPane.showInputDialog(null, "Enter maximum number of messages to support (e.g. 50):");
            if (capStr == null) return false; // user cancelled
            try {
                int cap = Integer.parseInt(capStr.trim());
                if (cap <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive integer for capacity.");
                    continue;
                }
                // allocate arrays with chosen capacity
                ensureCapacity(cap);
                count = 0;
                JOptionPane.showMessageDialog(null, "Capacity set to " + capacity + " messages.");
                return true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer for capacity.");
            }
        }
    }

    // ensure underlying arrays have at least 'required' capacity (expand if needed)
    private void ensureCapacity(int required) {
        if (capacity >= required) return;

        int newCapacity = required;

        String[] newMessageIDs = new String[newCapacity];
        String[] newSenders = new String[newCapacity];
        String[] newRecipients = new String[newCapacity];
        String[] newMessages = new String[newCapacity];
        String[] newHashes = new String[newCapacity];
        String[] newActions = new String[newCapacity];

        // copy existing content safely up to current count
        for (int i = 0; i < Math.min(count, newCapacity); i++) {
            if (i < messageIDs.length) newMessageIDs[i] = messageIDs[i];
            if (i < senders.length) newSenders[i] = senders[i];
            if (i < recipients.length) newRecipients[i] = recipients[i];
            if (i < messages.length) newMessages[i] = messages[i];
            if (i < hashes.length) newHashes[i] = hashes[i];
            if (i < actions.length) newActions[i] = actions[i];
        }

        messageIDs = newMessageIDs;
        senders = newSenders;
        recipients = newRecipients;
        messages = newMessages;
        hashes = newHashes;
        actions = newActions;

        capacity = newCapacity;
    }

    private void sendMessages() {
        if (!ensureCapacityInitialized()) return;

        int numMessages = getNumMessages();
        for (int i = 0; i < numMessages; i++) {
            if (count >= capacity) {
                JOptionPane.showMessageDialog(null, "Array capacity reached (" + capacity + "). Cannot add more messages.");
                break;
            }

            Message msg = new Message(count); // messageNumber uses current count

            // Collect recipient with immediate retry on invalid input
            collectRecipient(msg);

            // Collect message text with immediate retry on invalid input
            collectMessageText(msg);

            String action = msg.SentMessageAction();

            // Save into arrays (record regardless of action)
            messageIDs[count] = msg.getMessageId();
            senders[count] = (loggedInUser == null ? "Unknown" : loggedInUser);
            recipients[count] = msg.getRecipient();
            messages[count] = msg.getMessageText();
            hashes[count] = msg.createMessageHash();
            actions[count] = action;
            count++;

            // If stored, also write to JSON
            if ("STORED".equals(action)) {
                msg.storeMessage();
                JOptionPane.showMessageDialog(null, "Message stored to JSON.");
            }

            if ("SENT".equals(action)) {
                JOptionPane.showMessageDialog(null, msg.printMessageDetails());
            }
        }

        JOptionPane.showMessageDialog(null, "Total number of messages currently stored in arrays: " + count);
    }

    private int getNumMessages() {
        while (true) {
            String input = JOptionPane.showInputDialog(null, "How many messages would you like to enter now?");
            if (input == null) return 0;
            try {
                int num = Integer.parseInt(input.trim());
                if (num > 0) return num;
                JOptionPane.showMessageDialog(null, "Please enter a positive number.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    }

    private void collectRecipient(Message msg) {
        while (true) {
            String recipient = JOptionPane.showInputDialog(null, "Enter recipient number (include +27…):");
            if (recipient == null) continue;
            if (msg.checkRecipientCell(recipient)) {
                msg.setRecipient(recipient);
                break;
            } else {
                // immediate retry message as required
                JOptionPane.showMessageDialog(null, "Cell phone number invalid — please retry now.");
            }
        }
    }

    private void collectMessageText(Message msg) {
        while (true) {
            String text = JOptionPane.showInputDialog(null, "Enter message text (max " + Message.MAX_MESSAGE_LENGTH + " chars):");
            if (text == null) continue;
            String validation = msg.validateMessageLength(text);
            if ("Message ready to send.".equals(validation)) {
                msg.setMessageText(text);
                break;
            } else {
                // immediate retry notice
                JOptionPane.showMessageDialog(null, validation + " Please reduce and re-enter now.");
            }
        }
    }

    // A) Display sender + recipient of all SENT messages
    private void displaySenderRecipientAll() {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No messages recorded yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("Sender -> Recipient (Only SENT messages):\n\n");
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (i < actions.length && "SENT".equals(actions[i])) {
                sb.append(i < senders.length ? senders[i] : "Unknown").append(" -> ")
                  .append(i < recipients.length ? recipients[i] : "Unknown").append("\n");
                found = true;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "No messages with action SENT found.");
        } else {
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // B) Display the longest sent message
    private void displayLongestMessage() {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No messages recorded yet.");
            return;
        }
        int idx = -1;
        int maxLen = -1;
        for (int i = 0; i < count; i++) {
            if (i < actions.length && "SENT".equals(actions[i]) && i < messages.length && messages[i] != null) {
                int len = messages[i].length();
                if (len > maxLen) {
                    maxLen = len;
                    idx = i;
                }
            }
        }
        if (idx == -1) {
            JOptionPane.showMessageDialog(null, "No SENT messages to evaluate.");
        } else {
            String out = "Longest SENT message (" + maxLen + " chars):\n\n"
                    + "Sender: " + (idx < senders.length ? senders[idx] : "Unknown")
                    + "\nRecipient: " + (idx < recipients.length ? recipients[idx] : "Unknown")
                    + "\nMessage: " + (idx < messages.length ? messages[idx] : "");
            JOptionPane.showMessageDialog(null, out);
        }
    }

    // C) Search for a message by ID and display recipient + message
    private void searchByMessageID() {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No messages recorded yet.");
            return;
        }
        String id = JOptionPane.showInputDialog(null, "Enter Message ID to search for:");
        if (id == null) return;
        for (int i = 0; i < count; i++) {
            if (i < messageIDs.length && id.equals(messageIDs[i])) {
                String out = "Message found:\nRecipient: " + (i < recipients.length ? recipients[i] : "Unknown")
                        + "\nMessage: " + (i < messages.length ? messages[i] : "");
                JOptionPane.showMessageDialog(null, out);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    // D) Search for all messages sent to a particular recipient
    private void searchByRecipient() {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No messages recorded yet.");
            return;
        }
        String rcpt = JOptionPane.showInputDialog(null, "Enter recipient number to search for:");
        if (rcpt == null) return;
        StringBuilder sb = new StringBuilder("Messages sent to " + rcpt + ":\n\n");
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (i < recipients.length && rcpt.equals(recipients[i])) {
                sb.append("ID: ").append(i < messageIDs.length ? messageIDs[i] : "Unknown")
                        .append(" | Sender: ").append(i < senders.length ? senders[i] : "Unknown")
                        .append(" | Message: ").append(i < messages.length ? messages[i] : "")
                        .append("\n\n");
                found = true;
            }
        }
        if (!found) JOptionPane.showMessageDialog(null, "No messages found for that recipient.");
        else JOptionPane.showMessageDialog(null, sb.toString());
    }

    // E) Delete a message using the message hash
    private void deleteByHash() {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No messages recorded yet.");
            return;
        }
        String hashToDelete = JOptionPane.showInputDialog(null, "Enter message hash to delete (case sensitive):");
        if (hashToDelete == null) return;
        for (int i = 0; i < count; i++) {
            if (i < hashes.length && hashToDelete.equals(hashes[i])) {
                // remove by shifting left
                for (int j = i; j < count - 1; j++) {
                    if (j + 1 < messageIDs.length) messageIDs[j] = messageIDs[j + 1];
                    if (j + 1 < senders.length) senders[j] = senders[j + 1];
                    if (j + 1 < recipients.length) recipients[j] = recipients[j + 1];
                    if (j + 1 < messages.length) messages[j] = messages[j + 1];
                    if (j + 1 < hashes.length) hashes[j] = hashes[j + 1];
                    if (j + 1 < actions.length) actions[j] = actions[j + 1];
                }
                // null out last slot safely
                int last = count - 1;
                if (last < messageIDs.length) messageIDs[last] = null;
                if (last < senders.length) senders[last] = null;
                if (last < recipients.length) recipients[last] = null;
                if (last < messages.length) messages[last] = null;
                if (last < hashes.length) hashes[last] = null;
                if (last < actions.length) actions[last] = null;
                count--;
                JOptionPane.showMessageDialog(null, "Message deleted successfully.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "No message matched that hash.");
    }

    // F) Display a full report that lists full details of all the sent messages
    private void displayFullReport() {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No messages recorded yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("Full Sent Messages Report (only SENT action shown):\n\n");
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (i < actions.length && "SENT".equals(actions[i])) {
                sb.append("Message Hash: ").append(i < hashes.length ? hashes[i] : "Unknown").append("\n");
                sb.append("Recipient: ").append(i < recipients.length ? recipients[i] : "Unknown").append("\n");
                sb.append("Message: ").append(i < messages.length ? messages[i] : "").append("\n");
                sb.append("Sender: ").append(i < senders.length ? senders[i] : "Unknown").append("\n");
                sb.append("-------------------------------\n");
                found = true;
            }
        }
        if (!found) JOptionPane.showMessageDialog(null, "No SENT messages to report.");
        else JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Load stored messages from messages.json and add to arrays (auto-expand if needed)
    private void loadStoredMessages() {

    // Load JSON file
    JSONArray stored = new JSONArray();
    try (FileReader reader = new FileReader("messages.json")) {
        JSONParser parser = new JSONParser();
        stored = (JSONArray) parser.parse(reader);
    } catch (IOException | ParseException e) {
        JOptionPane.showMessageDialog(null, "No stored messages found or error reading JSON.");
        return;
    }

    int storedCount = stored.size();
    if (storedCount == 0) {
        JOptionPane.showMessageDialog(null, "Stored messages file is empty.");
        return;
    }

    // IMPORTANT: Never shrink capacity.
    // If arrays were never initialized, create MIN capacity = storedCount or user-defined
    if (capacity == 0) {
        ensureCapacity(storedCount + 10);  // give extra space
    } else if (storedCount > capacity) {
        ensureCapacity(storedCount + 10);  // expand but never shrink
    }

    // Load JSON into arrays starting from index 0
    for (int i = 0; i < storedCount; i++) {
        JSONObject obj = (JSONObject) stored.get(i);

        messageIDs[i] = (String) obj.get("MessageID");
        recipients[i] = (String) obj.get("Recipient");
        messages[i] = (String) obj.get("Message");
        hashes[i] = (String) obj.get("MessageHash");

        // We do NOT know original sender, so default:
        senders[i] = (loggedInUser == null ? "Unknown" : loggedInUser);
        actions[i] = "STORED";
    }

    count = storedCount;

    JOptionPane.showMessageDialog(null,
            "Loaded " + storedCount + " stored messages.\n" +
            "Capacity remains " + capacity + ".\n" +
            "You can now send new messages normally.");
}

}