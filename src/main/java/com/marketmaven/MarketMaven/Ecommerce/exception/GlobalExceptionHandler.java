package com.marketmaven.MarketMaven.Ecommerce.exception;
//Exception Handling across the entire application.
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice //intercepts any error that has been thrown in our application
public class GlobalExceptionHandler{
    @ExceptionHandler(value = Exception.class)//Catches all unhandled exception(parent exception)
    public ResponseEntity<Response> handleAllException(Exception ex, WebRequest request){
        Response errorResponse=Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())//returns 500(internal server error)
                .message(ex.getMessage())
                .build();//creates an errorResponse Object which includes status and message
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)//Specifically handles NotFoundException
    public ResponseEntity<Response> NotFoundException(Exception ex,WebRequest request){
        Response response=Response.builder()
                .status(HttpStatus.NOT_FOUND.value())//Returns HTTP 404 (Not Found)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidCredentialException.class)//Handles authentication/credential related exceptions
    public ResponseEntity<Response> InvalidCredentialsException(InvalidCredentialException ex, WebRequest request){
        Response response=Response.builder()
                .status((HttpStatus.BAD_REQUEST.value()))//Returns HTTP 400 (Bad Request)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
