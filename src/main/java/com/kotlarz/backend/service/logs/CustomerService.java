package com.kotlarz.backend.service.logs;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import com.kotlarz.backend.domain.logs.FormatterConfigEntity;
import com.kotlarz.backend.domain.system.User;
import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.backend.mock.CustomerMockService;
import com.kotlarz.backend.repository.logs.CustomerRepository;
import com.kotlarz.backend.repository.logs.FormatterConfigRepository;
import com.kotlarz.backend.service.system.UserService;
import com.kotlarz.configuration.security.exception.UserNotFoundException;
import com.kotlarz.frontend.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FormatterConfigRepository formatterConfigRepository;

    @Autowired
    private CustomerMockService customerMockService;

    @Autowired
    private UserService userService;

    @PostConstruct
    @Transactional
    void memInit() {
        if (customerRepository.count() == 0) {
            LongStream.range(0, 10).forEach(clientId -> {
                CustomerEntity customer = customerMockService.mockCustomer();

                FormatterConfigEntity formatterConfig = FormatterConfigEntity.builder()
                        .customer(customer)
                        .pattern("{date} {thread} {user} {level} {classname} {content}")
                        .fill(true)
                        .build();

                customer.setFormatter(formatterConfig);
                customer.getReports().forEach(report -> report.setCustomer(customer));
                customerRepository.save(customer);
            });
        }
    }

    @Transactional
    public Optional<CustomerEntity> getCustomer(Long id) {
        return Optional.ofNullable(customerRepository.getOne(id));
    }

    @Transactional
    public List<CustomerEntity> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public List<CustomerEntity> getCustomersForUser(Long userId) {
        User user = userService.getUser(userId).orElseThrow(UserNotFoundException::new);
        if (user.getType() == UserType.ADMIN) {
            return getCustomers();
        } else {
            List<CustomerEntity> availableCustomers = user.getAvailableCustomers();
            Hibernate.initialize(availableCustomers);
            return availableCustomers;
        }
    }

    @Transactional
    public Optional<FormatterConfigEntity> getFormatterConfig(Long customerId) {
        return Optional.ofNullable(formatterConfigRepository.findByCustomer_Id(customerId));
    }

    @Transactional
    public void create(CustomerEntity entity) {
        customerRepository.save(entity);
    }

    @Transactional
    public void update(CustomerDto dto) {
        CustomerEntity toUpdate = getCustomer(dto.getId()).orElseThrow(RuntimeException::new);
        toUpdate.setName(dto.getName());
        toUpdate.setClearLogsAfterDays(dto.getClearLogsAfterDays());

        FormatterConfigEntity formatter = toUpdate.getFormatter();
        formatter.setFill(dto.getFillPattern());
        formatter.setPattern(dto.getPattern());


        customerRepository.save(toUpdate);
    }

    @Transactional
    public void delete(Long customerId) {
        customerRepository.delete(customerId);
    }
}
