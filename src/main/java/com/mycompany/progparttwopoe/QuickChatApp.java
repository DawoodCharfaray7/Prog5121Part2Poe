/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progparttwopoe;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author DC
 */
class QuickChatApp {
    
    private Registration reg;
    private int totalSentMessages;
    private ArrayList<String> sentMessagesSummary;

    public QuickChatApp() {
        reg = new Registration();
        totalSentMessages = 0;
        sentMessagesSummary = new ArrayList<>();
    }

    public void run() {
        reg.registerUser();
        handleLogin();
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
                    2) Show recently sent messages
                    3) Quit
                    """;
            String choice = JOptionPane.showInputDialog(null, menu);
            if (choice == null) continue;

            switch (choice.trim()) {
                case "1" -> sendMessages();
                case "2" -> JOptionPane.showMessageDialog(null, "Coming Soon.");
                case "3" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye.");
                    break mainLoop;
                }
                default -> JOptionPane.showMessageDialog(null, "Please select option 1, 2 or 3.");
            }
        }
    }

    private void sendMessages() {
        int numMessages = getNumMessages();
        for (int i = 0; i < numMessages; i++) {
            Message msg = new Message(i);
            collectRecipient(msg);
            collectMessageText(msg);
            String action = msg.SentMessageAction();
            if (action.equals("SENT")) {
                JOptionPane.showMessageDialog(null, msg.printMessageDetails());
                totalSentMessages++;
                sentMessagesSummary.add(msg.printMessageDetails());
            }
        }
        JOptionPane.showMessageDialog(null, "Total number of messages sent: " + totalSentMessages);
    }

    private int getNumMessages() {
        while (true) {
            String input = JOptionPane.showInputDialog(null, "How many messages would you like to enter?");
            if (input == null) continue;
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
                JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
            }
        }
    }

    private void collectMessageText(Message msg) {
        while (true) {
            String text = JOptionPane.showInputDialog(null, "Enter message text (max " + Message.MAX_MESSAGE_LENGTH + " chars):");
            if (text == null) continue;
            String validation = msg.validateMessageLength(text);
            if (validation.equals("Message ready to send.")) {
                msg.setMessageText(text);
                JOptionPane.showMessageDialog(null, "Message ready to send.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, validation);
            }
        }
    }
 }
    

