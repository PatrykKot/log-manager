package com.kotlarz.backend.service;

import com.kotlarz.backend.domain.CustomerEntity;
import com.kotlarz.backend.domain.EventEntity;
import com.kotlarz.backend.domain.FormatterConfigEntity;
import com.kotlarz.backend.domain.ReportEntity;
import com.kotlarz.backend.repository.CustomerRepository;
import com.kotlarz.backend.repository.FormatterConfigRepository;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FormatterConfigRepository formatterConfigRepository;

    @PostConstruct
    @Transactional
    void memInit() {
        LoremIpsum textGenerator = LoremIpsum.getInstance();

        LongStream.range(0, 10).forEach(clientId -> {
            CustomerEntity customer = CustomerEntity.builder()
                    .name(textGenerator.getName())
                    .clearLogsAfterDays(40L)
                    .reports(mockLogReports())
                    .build();

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

    private List<ReportEntity> mockLogReports() {
        return IntStream.range(3, new Random().nextInt(50) + 3)
                .mapToObj(val -> ReportEntity.builder()
                        .date(new Date())
                        .events(mockLogEvents())
                        .build())
                .peek(report -> report.getEvents().forEach(event -> event.setReport(report)))
                .collect(Collectors.toList());
    }

    private List<EventEntity> mockLogEvents() {
        LoremIpsum textGenerator = LoremIpsum.getInstance();
        List<String> someThreadNames = Arrays.asList("user-thread", "http-thread", "system-thread");
        List<String> someUsernames = Arrays.asList("admin", "user1", "system");
        List<String> someLevels = Arrays.asList("DEBUG", "TRACE", "INFO", "ERROR", "WARNING");
        List<String> someClassNames = Arrays.asList("String", "Long", "StringUtils", "XmlInterface");

        return IntStream.range(10, new Random().nextInt(300) + 10)
                .mapToObj(val -> EventEntity.builder()
                        .content(textGenerator.getWords(5, 10).getBytes(StandardCharsets.UTF_8))
                        .classname(any(someClassNames))
                        .level(any(someLevels))
                        .threadName(any(someThreadNames))
                        .username(any(someUsernames))
                        .date(new Date())
                        .build())
                .collect(Collectors.toList());
    }

    private static <T> T any(List<T> collection) {
        return collection.get(new Random().nextInt(collection.size()));
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
