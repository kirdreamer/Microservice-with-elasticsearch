package kirdreamer.serverproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class LockedException extends RuntimeException{
}
