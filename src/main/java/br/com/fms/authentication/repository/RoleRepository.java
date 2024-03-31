package br.com.fms.authentication.repository;

import br.com.fms.authentication.model.ERole;
import br.com.fms.authentication.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
