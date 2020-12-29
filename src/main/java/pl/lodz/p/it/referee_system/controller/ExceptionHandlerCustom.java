package pl.lodz.p.it.referee_system.controller;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.lodz.p.it.referee_system.dto.StringDTO;
import pl.lodz.p.it.referee_system.exception.ApplicationException;
import pl.lodz.p.it.referee_system.exception.ExceptionMessages;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
class ExceptionHandlerCustom {
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({NoSuchElementException.class, MethodArgumentTypeMismatchException.class})
//    public void handleBadRequest(Exception e) {
//        Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
//    }
//
//    @ExceptionHandler(OptimisticLockingFailureException.class)
//    public void e(Exception e) {
//        throw ApplicationException.exceptionForOptimisticLock(e);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StringDTO> handleBadRequests(Exception e) {
        e.printStackTrace();
        Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        if (ExceptionMessages.exceptionMessages.contains(e.getMessage())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new StringDTO(ContextUtills.getMessage(e.getMessage())));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StringDTO(""));
    }

    }
