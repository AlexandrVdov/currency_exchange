package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.client.BankClient;
import ru.skillbox.currency.exchange.client.ValCurs;
import ru.skillbox.currency.exchange.client.CurrencyJaxb;
import ru.skillbox.currency.exchange.dto.CurrencyDto;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateCurrenciesService {
    private final BankClient bankClient;
    private final CurrencyService currencyService;

    @Async
    @Scheduled(fixedRateString = "${bank.update.repeat.value}", timeUnit = TimeUnit.MINUTES)
    public void updateCurrencies() {
        log.info("update currencies started");
        try {
            bankClient.getCurrencies().forEach(currencyJaxb -> {
                CurrencyDto currencyDto = currencyService.getByIsoCharCode(currencyJaxb.getCharCode());
                if(currencyDto == null) {
                    currencyService.create(convertToDto(currencyJaxb));
                    return;
                }
                currencyDto.setIsoNumCode(Long.valueOf(currencyJaxb.getNumCode()));
                currencyDto.setNominal(Long.valueOf(currencyJaxb.getNominal()));
                currencyDto.setName(currencyJaxb.getName());
                currencyDto.setValue(currencyJaxb.getValue());
                currencyService.update(currencyDto);
            });
        } catch (Exception e) {
            log.error("Ошибка обнавления валют", e);
        }
    }

    private CurrencyDto convertToDto(CurrencyJaxb currencyJaxb) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setName(currencyJaxb.getName());
        currencyDto.setValue(currencyJaxb.getValue());
        currencyDto.setNominal(Long.valueOf(currencyJaxb.getNominal()));
        currencyDto.setIsoNumCode(Long.valueOf(currencyJaxb.getNumCode()));
        currencyDto.setIsoCharCode(currencyJaxb.getCharCode());
        return currencyDto;
    }
}
