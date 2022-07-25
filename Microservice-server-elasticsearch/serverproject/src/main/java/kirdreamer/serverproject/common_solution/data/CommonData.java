package kirdreamer.serverproject.common_solution.data;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class CommonData {
    private List<Map<String, String>> user = new ArrayList<>();

    public void setUser(Map<String, String> newData) {
        user.add(newData);
    }

    public void deleteUser(Map<String, String> dataToDelete) {
        user.remove(dataToDelete);
    }
}
