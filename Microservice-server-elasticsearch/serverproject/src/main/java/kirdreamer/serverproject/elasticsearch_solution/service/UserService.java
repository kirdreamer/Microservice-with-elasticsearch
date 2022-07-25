package kirdreamer.serverproject.elasticsearch_solution.service;

import kirdreamer.serverproject.elasticsearch_solution.data.User;
import kirdreamer.serverproject.elasticsearch_solution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public void save(final User user) {
        repository.save(user);
    }

    public User findById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(final String id) {
        repository.deleteById(id);
    }
}
