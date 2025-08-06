package com.marketmaven.MarketMaven.Ecommerce.exception;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException(String message){
        super(message);
    }
}
