package kirdreamer.serverproject.common_solution.controller.helper;

import kirdreamer.serverproject.common_solution.data.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class ControllerHelper {

    @Autowired
    public CommonData userData;

    private List<Integer> deletedIdStorage;
    private int maxId;

    public Boolean isIncorrectRequest(Map<String, String> userBody) {
        return (!userBody.containsKey("firstName") || userBody.get("firstName").equals("")) ||
                (!userBody.containsKey("lastName") || userBody.get("lastName").equals("")) ||
                !userBody.containsKey("email") ||
                !isEmail(userBody.get("email")) ||
                userBody.size() != 3;
    }

    public Boolean isEmail(String email) {
        return Pattern.matches("^\\S+@\\S+\\.\\S+$", email);
    }

    public Integer incrementIdIterator() {
        if (deletedIdStorage.isEmpty())
            return maxId++;
        return deletedIdStorage.remove(0);
    }

    public void decrementIdIterator(Integer deletedId) {
        if (deletedId == maxId) {
            maxId--;
            return;
        }
        deletedIdStorage.add(deletedId);
    }

    {
        deletedIdStorage = new ArrayList<>();
        maxId = 1;
    }
}
