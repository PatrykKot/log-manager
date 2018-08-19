package com.kotlarz.backend.repository.system;

import com.kotlarz.backend.domain.system.RememberMeTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface RememberMeTokenRepository extends JpaRepository<RememberMeTokenEntity, Long> {
    Optional<RememberMeTokenEntity> findByToken(String token);

    @Modifying
    @Query("delete from RememberMeTokenEntity remTok where remTok.id in " +
            "(select remTokTemp.id from RememberMeTokenEntity  remTokTemp where remTokTemp.user.id = :userId)")
    void invalidate(@Param("userId") Long userId);

    @Modifying
    @Query("delete from RememberMeTokenEntity remTok where remTok.validTo < :olderThan")
    void cleanOlderThan(@Param("olderThan") Date olderThan);
}
