package com.example.turboaz.scheduler;

import com.example.turboaz.dao.entity.PasswordResetTokenEntity;
import com.example.turboaz.dao.repository.PasswordResetTokenRepository;
import com.example.turboaz.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final CarService carService;
    private final PasswordResetTokenRepository tokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleCarDeletionTask() {
        LocalDate thirtyDaysAgo = LocalDate.now().minus(30, ChronoUnit.DAYS);
        carService.deleteCarsOlderThan(thirtyDaysAgo);
        System.out.println("Scheduled task: Deleting cars older than 30 days.");
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void cleanupExpiredTokens() {
        Date now = new Date();
        List<PasswordResetTokenEntity> expiredTokens = tokenRepository.findByExpiryDateBefore(now);
        tokenRepository.deleteAll(expiredTokens);
        System.out.println("Scheduled task: Cleaning up expired password reset tokens.");
    }

}