package com.kotlarz.backend.mock;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import com.kotlarz.backend.domain.logs.EventEntity;
import com.kotlarz.backend.domain.logs.ReportEntity;
import com.thedeanda.lorem.LoremIpsum;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CustomerMockService {
    private LoremIpsum textGenerator = new LoremIpsum();

    private static <T> T any(List<T> collection) {
        return collection.get(new Random().nextInt(collection.size()));
    }

    public CustomerEntity mockCustomer() {
        return CustomerEntity.builder()
                .name(textGenerator.getName())
                .clearLogsAfterDays(40L)
                .reports(mockLogReports())
                .build();
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

}
