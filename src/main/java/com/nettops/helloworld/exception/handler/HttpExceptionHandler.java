package com.nettops.helloworld.exception.handler;

import com.nettops.helloworld.exception.ResourceNotFoundException;
import com.nettops.helloworld.exception.helper.RestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String UNEXPECTED_ERROR = "Exception.unexpected";
    private final MessageSource messageSource;

    @Autowired
    public HttpExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenericExceptions(Locale locale) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new RestMessage(messageSource.getMessage(UNEXPECTED_ERROR, null, locale))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        BindingResult result = e.getBindingResult();
        List<String> errorMessages =
                result.getAllErrors()
                        .stream()
                        .map(error -> messageSource.getMessage(error, locale))
                        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new RestMessage(errorMessages)
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFound(ResourceNotFoundException e, Locale locale){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new RestMessage(messageSource.getMessage(e.getMessage(), e.getArgs(), locale))
        );
    }

}
