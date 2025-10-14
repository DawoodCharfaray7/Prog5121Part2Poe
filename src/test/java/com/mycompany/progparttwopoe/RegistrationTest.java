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
public class RegistrationTest {

    public RegistrationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Prevent HeadlessException on GitHub Actions
        System.setProperty("java.awt.headless", "true");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    
    

    /**
     * Test of checkUserName method, of class Registration.
     */
    @Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        String user = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.checkUserName(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of checkPasswordComplexity method, of class Registration.
     */
    @Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        String pwd = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity(pwd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of checkCellPhoneNumber method, of class Registration.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        String cell = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber(cell);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of registerUser method, of class Registration.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.registerUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of loginUser method, of class Registration.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String userAttempt = "";
        String pwdAttempt = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.loginUser(userAttempt, pwdAttempt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of returnLoginStatus method, of class Registration.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean success = false;
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.returnLoginStatus(success);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getUsername method, of class Registration.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getUsername();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of getPassword method, of class Registration.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of getCellNumber method, of class Registration.
     */
    @Test
    public void testGetCellNumber() {
        System.out.println("getCellNumber");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getCellNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
     
    }

    /**
     * Test of getFirstName method, of class Registration.
     */
    @Test
    public void testGetFirstName() {
        System.out.println("getFirstName");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getFirstName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of getLastName method, of class Registration.
     */
    @Test
    public void testGetLastName() {
        System.out.println("getLastName");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getLastName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
