package br.com.fms.authentication;

import br.com.fms.authentication.model.ERole;
import br.com.fms.authentication.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import br.com.fms.authentication.repository.RoleRepository;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed initial roles if not already present
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            Role roleUser = new Role();
            roleUser.setName(ERole.ROLE_USER);
            roleRepository.save(roleUser);
        }

        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setName(ERole.ROLE_ADMIN);
            roleRepository.save(roleAdmin);
        }

        if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()) {
            Role roleModerator = new Role();
            roleModerator.setName(ERole.ROLE_MODERATOR);
            roleRepository.save(roleModerator);
        }
    }
}
