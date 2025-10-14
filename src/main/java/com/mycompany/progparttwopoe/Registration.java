/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progparttwopoe;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * @param username entered by the user
 * @return true if valid, false otherwise
 * @author DC
 */
class Registration {
 
    private String username;
    private String password;
    private String cellNumber;
    private String firstName;
    private String lastName;

    // Regex for cellphone: requires + followed by exactly 11 digits 
    private static final Pattern CELL_REGEX = Pattern.compile("^(\\+\\d{11}|0\\d{9})$");

    public Registration() {
    }

    // Check username contains underscore and is no more than five characters long
    public boolean checkUserName(String user) {
        if (user == null) return false;
        return user.contains("_") && user.length() <= 5;
    }

    // Checks password complexity:
    // at least 8 chars, contains at least one uppercase, one number and one special character
    public boolean checkPasswordComplexity(String pwd) {
        if (pwd == null) return false;
        if (pwd.length() < 8) return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : pwd.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    // Checks cellphone number formatting (international code required)
    public boolean checkCellPhoneNumber(String cell) {
        if (cell == null) return false;
        return CELL_REGEX.matcher(cell).matches();
    }

    public String registerUser() {
        // Ask first name and last name (required so login welcome message can use them)
        while (true) {
            firstName = JOptionPane.showInputDialog(null, "Enter your first name:");
            if (firstName != null && !firstName.trim().isEmpty()) break;
            JOptionPane.showMessageDialog(null, "First name cannot be empty. Please try again.");
        }
        while (true) {
            lastName = JOptionPane.showInputDialog(null, "Enter your last name:");
            if (lastName != null && !lastName.trim().isEmpty()) break;
            JOptionPane.showMessageDialog(null, "Last name cannot be empty. Please try again.");
        }

        // USERNAME
        while (true) {
            String userInput = JOptionPane.showInputDialog(null, "Enter username (must contain an underscore and be no more than 5 characters):");
            if (userInput == null) {
                // user pressed cancel -> loop again asking explicitly
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
                continue;
            }
            if (checkUserName(userInput)) {
                username = userInput;
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // PASSWORD
        while (true) {
            String pwd = JOptionPane.showInputDialog(null, "Enter password (at least 8 chars, a capital letter, a number, and a special character):");
            if (pwd == null) {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                continue;
            }
            if (checkPasswordComplexity(pwd)) {
                password = pwd;
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // CELL PHONE
        while (true) {
            String cell = JOptionPane.showInputDialog(null, "Enter cell phone number (include international code, e.g. +27XXXXXXXXX):");
            if (cell == null) {
                JOptionPane.showMessageDialog(null, "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
                continue;
            }
            if (checkCellPhoneNumber(cell)) {
                cellNumber = cell;
                // using the exact wording from the test data examples:
                JOptionPane.showMessageDialog(null, "Cell number successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
            }
        }

        return "Registration complete";
    }

    // Verifies username/password against stored values
    public boolean loginUser(String userAttempt, String pwdAttempt) {
        if (username == null || password == null) return false;
        return username.equals(userAttempt) && password.equals(pwdAttempt);
    }

    // returnLoginStatus returns messages for login success/fail
    public String returnLoginStatus(boolean success) {
        if (success) {
            // follow exact wording from the spec:
            return "Welcome " + firstName + " ," + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // getters for tests or other modules
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCellNumber() { return cellNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
    
