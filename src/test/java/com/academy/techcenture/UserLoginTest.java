package com.academy.techcenture;

import org.testng.annotations.Test;

public class UserLoginTest {


    @Test
    public void loginValidCredentialsTest(){
  //      "user@gmail.com" "user123"
        System.out.println("Test login functionality with valid credentials");

    }

    @Test
    public void loginWrongCredentialsTest(){
    //    "user@gmail.com" "user125"
        System.out.println("Test login functionality with valid credentials");
    }

    @Test
    public void loginEmptyCredentialsTest(){
        System.out.println("Test login functionality with valid credentials");
    }

    @Test
    public void loginInValidCredentialsTest(){
 //       "usergmail.com" "user125"
        System.out.println("Test login functionality with valid credentials");
    }
    @Test
    public void loginWithUserNameTest(){
  //      "usergmail.com" "user125"
        System.out.println("Test login functionality with valid credentials");
    }
    @Test
    public void loginWithPasswordTest(){
    //    "usergmail.com" "user125"
        System.out.println("Test login functionality with valid credentials");
    }
}
