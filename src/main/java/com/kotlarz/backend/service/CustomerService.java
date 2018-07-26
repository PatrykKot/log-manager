package com.kotlarz.backend.service;

import com.kotlarz.backend.domain.CustomerEntity;
import com.kotlarz.backend.domain.FormatterConfigEntity;
import com.kotlarz.backend.mock.CustomerMockService;
import com.kotlarz.backend.repository.CustomerRepository;
import com.kotlarz.backend.repository.FormatterConfigRepository;
import lombok.extern.slf4j.Slf4j;
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

    @PostConstruct
    @Transactional
    void memInit() {
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

    @Transactional
    public Optional<CustomerEntity> getCustomer(Long id) {
        return Optional.ofNullable(customerRepository.getOne(id));
    }

    @Transactional
    public List<CustomerEntity> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Optional<FormatterConfigEntity> getFormatterConfig(Long customerId) {
        return Optional.ofNullable(formatterConfigRepository.findByCustomer_Id(customerId));
    }
}
