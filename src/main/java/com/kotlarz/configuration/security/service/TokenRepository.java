package com.kotlarz.configuration.security.service;

import com.kotlarz.backend.domain.system.RememberMeTokenEntity;
import com.kotlarz.backend.repository.system.RememberMeTokenRepository;
import com.kotlarz.backend.service.system.UserService;
import com.kotlarz.configuration.security.exception.UserNotFoundException;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@SpringComponent
public class TokenRepository {
    public static final Long TOKEN_VALID_TIME_SEC = 7L * 24 * 3600;

    @Autowired
    private RememberMeTokenRepository rememberMeTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Transactional
    public String store(Long userId) {
        String token = UUID.randomUUID().toString();

        rememberMeTokenRepository.save(RememberMeTokenEntity.builder()
                .token(token)
                .user(userService.getUser(userId).orElseThrow(UserNotFoundException::new))
                .validTo(new Date(System.currentTimeMillis() + TOKEN_VALID_TIME_SEC * 1000))
                .build());

        return token;
    }

    @Transactional
    public Optional<Long> get(String token) {
        Optional<RememberMeTokenEntity> optionalToken = rememberMeTokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            RememberMeTokenEntity rememberMeToken = optionalToken.get();
            if (rememberMeToken.getValidTo().before(new Date())) {
                rememberMeTokenRepository.delete(rememberMeToken);
                return Optional.empty();
            } else {
                return Optional.ofNullable(rememberMeToken.getUser().getId());
            }
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public void invalidate(Long userId) {
        rememberMeTokenRepository.invalidate(userId);
    }

    @Scheduled(fixedRate = 60 * 1000)
    protected void cleanup() {
        new TransactionTemplate(platformTransactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Long nowMs = System.currentTimeMillis();
                Long period = TOKEN_VALID_TIME_SEC * 1000;
                Date olderThan = new Date(nowMs - period);

                rememberMeTokenRepository.cleanOlderThan(olderThan);
            }
        });
    }
}
