package com.badminton.winzz.exeception;


public class InvalidTokenException extends Exception {

    public String message;

    public InvalidTokenException(String message){
        this.message=message;
    }
}
