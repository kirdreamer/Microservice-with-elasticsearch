package kirdreamer.serverproject.common_solution.controller;

import kirdreamer.serverproject.common_solution.controller.helper.ControllerHelper;
import kirdreamer.serverproject.exceptions.NotFoundException;
import kirdreamer.serverproject.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class ServerController {

    @Autowired
    private ControllerHelper controllerHelper;

    @GetMapping
    public List<Map<String, String>> getAllUsers() {
        return controllerHelper.userData.getUser();
    }

    @GetMapping("/{id}")
    public Map<String, String> getUserById(@PathVariable(required = false) String id) {
        return controllerHelper.userData.getUser().stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> createUser(@RequestBody Map<String, String> newUser) {
        if (controllerHelper.isIncorrectRequest(newUser))
            throw new UnprocessableEntityException();

        newUser.put("id", String.valueOf(controllerHelper.incrementIdIterator()));
        controllerHelper.userData.setUser(newUser);
        return newUser;
    }

    @PatchMapping("{id}")
    public Map<String, String> updateUser(@PathVariable String id, @RequestBody Map<String, String> updatedUser) {
        if (controllerHelper.isIncorrectRequest(updatedUser))
            throw new UnprocessableEntityException();

        Map<String, String> user = getUserById(id);
        user.putAll(updatedUser);
        return user;
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id) {
        controllerHelper.userData.deleteUser(getUserById(id));
        controllerHelper.decrementIdIterator(Integer.valueOf(id));
    }
}
