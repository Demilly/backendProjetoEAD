//package br.com.ead.controller.exception;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ExceptionHandlerController {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }
//}
