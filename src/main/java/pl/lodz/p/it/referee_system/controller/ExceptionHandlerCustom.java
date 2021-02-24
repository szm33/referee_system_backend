package pl.lodz.p.it.referee_system.controller;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lodz.p.it.referee_system.dto.StringDTO;
import pl.lodz.p.it.referee_system.exception.ExceptionMessages;
import pl.lodz.p.it.referee_system.utill.ContextUtills;
;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
class ExceptionHandlerCustom {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StringDTO> handleBadRequests(Exception e) {
        e.printStackTrace();
        Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        if (ExceptionMessages.exceptionMessages.contains(e.getMessage())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new StringDTO(ContextUtills.getMessage(e.getMessage())));
        }
        else if (e instanceof CannotAcquireLockException) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new StringDTO(ContextUtills.getMessage(ExceptionMessages.SERIALIZABLE_ERROR)));
        }
        else if (e instanceof AccessDeniedException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    }
