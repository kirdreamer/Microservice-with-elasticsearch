package kirdreamer.serverproject.elasticsearch_solution.repository;

import kirdreamer.serverproject.elasticsearch_solution.data.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User, String> {
}
