package exceptions;


import java.sql.Timestamp;
import java.time.Instant;

public class UserAccountException extends Exception{
    private final String message;
    private final Timestamp time;
    public UserAccountException(String message) {
        this.message = message;
        this.time = Timestamp.from(Instant.now());
    }

    @Override
    public String toString() {
        return "EXCEPTION: " + this.message + " " + this.time;
    }

}
