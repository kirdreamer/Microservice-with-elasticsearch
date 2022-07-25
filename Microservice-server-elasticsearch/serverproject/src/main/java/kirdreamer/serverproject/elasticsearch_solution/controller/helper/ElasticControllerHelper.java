package kirdreamer.serverproject.elasticsearch_solution.controller.helper;

import kirdreamer.serverproject.elasticsearch_solution.data.User;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ElasticControllerHelper {

    public Boolean isIncorrectRequest(User userBody) {
        return (userBody.getFirstName() == null || userBody.getFirstName().equals("") ||
                userBody.getLastName() == null || userBody.getLastName().equals("") ||
                userBody.getEmail() == null || !isEmail(userBody.getEmail()));
    }

    public Boolean isEmail(String email) {
        return Pattern.matches("^\\S+@\\S+\\.\\S+$", email);
    }
}
