package com.main.infrastructure.config;

import com.main.model.entity.Company;
import com.main.model.entity.Subscription;
import com.main.model.entity.User;
import com.main.model.enums.SubscriptionStatus;
import com.main.model.enums.UserRole;
import com.main.repository.CompanyRepository;
import com.main.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
@Profile({"dev", "default"})
public class DevDataInitializer {

    @Bean
    public CommandLineRunner createDefaultAdminUser(
            CompanyRepository companyRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Optional<User> existing = userRepository.findFirstByEmail("admin@teste.com");
            if (existing.isPresent()) {
                return;
            }

            Company company = new Company();
            company.setId(UUID.fromString("a0000000-0000-0000-0000-000000000001"));
            company.setTradeName("Empresa Teste");
            company.setCorporateName("Empresa Teste Ltda");
            company.setCnpj("12345678000199");
            company.setWhatsapp("11999999999");
            company.setActive(true);
            company.setMaxWaiters(10);

            Subscription subscription = new Subscription();
            subscription.setId(UUID.randomUUID());
            subscription.setCompany(company);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusYears(1));
            subscription.setStatus(SubscriptionStatus.ACTIVE);
            subscription.setAmountPaid(BigDecimal.ZERO);

            company.setSubscriptions(List.of(subscription));

            companyRepository.save(company);

            User user = new User();
            user.setCompany(company);
            user.setEmail("admin@teste.com");
            user.setPasswordHash(passwordEncoder.encode("password"));
            user.setName("Admin Teste");
            user.setRole(UserRole.COMPANY_ADMIN);
            user.setActive(true);

            userRepository.save(user);
        };
    }
}

