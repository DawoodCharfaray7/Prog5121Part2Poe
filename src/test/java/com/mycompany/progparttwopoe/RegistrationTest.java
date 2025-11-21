/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.progparttwopoe;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegistrationTest {

@Test
    public void testCheckUserName() {
        Registration r = new Registration();

        assertTrue(r.checkUserName("ab_c"));     // valid
        assertFalse(r.checkUserName("abc"));      // no underscore
        assertFalse(r.checkUserName("abc_def"));  // too long
    }

    @Test
    public void testCheckPasswordComplexity() {
        Registration r = new Registration();

        assertTrue(r.checkPasswordComplexity("Pass@123"));   
        assertFalse(r.checkPasswordComplexity("pass123"));   
        assertFalse(r.checkPasswordComplexity("PASSWORD!")); 
    }

    @Test
    public void testCheckCellPhoneNumber() {
        Registration r = new Registration();

        //regex accepts +27 + 9 digits OR 0 + 9 digits
        assertTrue(r.checkCellPhoneNumber("+27821234567"));
        assertTrue(r.checkCellPhoneNumber("0821234567"));
        assertFalse(r.checkCellPhoneNumber("12345"));
    }

    @Test
    public void testLoginUser() {
        Registration r = new Registration();

        try {
            var uField = Registration.class.getDeclaredField("username");
            var pField = Registration.class.getDeclaredField("password");

            uField.setAccessible(true);
            pField.setAccessible(true);

            uField.set(r, "dc_1");
            pField.set(r, "Pass@123");

        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        assertTrue(r.loginUser("dc_1", "Pass@123"));
        assertFalse(r.loginUser("wrong", "Pass@123"));
        assertFalse(r.loginUser("dc_1", "wrong"));
    }

    @Test
    public void testReturnLoginStatus() {
        Registration r = new Registration();

        try {
            var fField = Registration.class.getDeclaredField("firstName");
            var lField = Registration.class.getDeclaredField("lastName");

            fField.setAccessible(true);
            lField.setAccessible(true);

            fField.set(r, "John");
            lField.set(r, "Cena");

        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        assertEquals("Welcome John Cena it is great to see you.",
                r.returnLoginStatus(true));

        assertEquals("Username or password incorrect, please try again.",
                r.returnLoginStatus(false));
    }

    @Test
    public void testGetters() {
        Registration r = new Registration();

        try {
            var uField = Registration.class.getDeclaredField("username");
            var pField = Registration.class.getDeclaredField("password");
            var cField = Registration.class.getDeclaredField("cellNumber");
            var fField = Registration.class.getDeclaredField("firstName");
            var lField = Registration.class.getDeclaredField("lastName");

            uField.setAccessible(true);
            pField.setAccessible(true);
            cField.setAccessible(true);
            fField.setAccessible(true);
            lField.setAccessible(true);

            uField.set(r, "dc_1");
            pField.set(r, "Pass@123");
            cField.set(r, "+27821234567");
            fField.set(r, "John");
            lField.set(r, "Cena");

        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        assertEquals("dc_1", r.getUsername());
        assertEquals("Pass@123", r.getPassword());
        assertEquals("+27821234567", r.getCellNumber());
        assertEquals("John", r.getFirstName());
        assertEquals("Cena", r.getLastName());
    }
}