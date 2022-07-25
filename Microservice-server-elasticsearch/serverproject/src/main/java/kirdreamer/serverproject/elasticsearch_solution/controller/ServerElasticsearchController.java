package kirdreamer.serverproject.elasticsearch_solution.controller;

import kirdreamer.serverproject.elasticsearch_solution.controller.helper.ElasticControllerHelper;
import kirdreamer.serverproject.elasticsearch_solution.data.User;
import kirdreamer.serverproject.elasticsearch_solution.service.UserService;
import kirdreamer.serverproject.exceptions.LockedException;
import kirdreamer.serverproject.exceptions.NotFoundException;
import kirdreamer.serverproject.exceptions.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elasticsearch/user")
@RequiredArgsConstructor
public class ServerElasticsearchController {
    private final UserService service;

    private final ElasticControllerHelper elasticControllerHelper;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(required = false) String id) {
        return service.findById(id);
    }

    @PostMapping("/{id}")
    public void createUser(@PathVariable String id, @RequestBody User newUser) {
        if (elasticControllerHelper.isIncorrectRequest(newUser))
            throw new UnprocessableEntityException();
        if (service.findById(id) != null)
            throw new LockedException();
        newUser.setId(id);
        service.save(newUser);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        if (elasticControllerHelper.isIncorrectRequest(updatedUser))
            throw new UnprocessableEntityException();
        updatedUser.setId(id);
        service.save(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        if (service.findById(id) == null)
            throw new NotFoundException();

        service.deleteById(id);
    }
}
