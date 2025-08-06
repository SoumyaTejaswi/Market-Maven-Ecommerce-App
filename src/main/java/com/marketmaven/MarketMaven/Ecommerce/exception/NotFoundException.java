package com.marketmaven.MarketMaven.Ecommerce.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
