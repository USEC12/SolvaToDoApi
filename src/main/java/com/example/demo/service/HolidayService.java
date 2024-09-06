package com.example.demo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService {

    private final WebClient webClient;

    public HolidayService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://date.nager.at").build();
    }

    @Cacheable("holidays")
    public List<LocalDate> getHolidays(String countryCode, int year) {
        try {
            return webClient.get()
                    .uri("/Api/v2/PublicHoliday/{year}/{countryCode}", year, countryCode)
                    .retrieve()
                    .bodyToFlux(LocalDate.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            // Логируем ошибку и возвращаем пустой список или кидаем исключение
            System.out.println("Holidays not found for year " + year + " and country " + countryCode);
            return new ArrayList<>();
        }
    }
}