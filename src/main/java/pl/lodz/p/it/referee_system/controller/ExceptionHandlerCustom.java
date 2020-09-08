package pl.lodz.p.it.referee_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.PersistenceException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
class ExceptionHandlerCustom {

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(PersistenceException.class)
    public void handleConflict(Exception e) {
        Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        //problemy z bazą
    }

    }
