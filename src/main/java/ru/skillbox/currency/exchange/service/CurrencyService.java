package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.TotalCurrency;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;

    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return mapper.convertToDto(currency);
    }

    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = repository.findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return  mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public TotalCurrency getAll() {
        log.info("CurrencyService method getAll executed");
        TotalCurrency totalCurrency = new TotalCurrency();
        totalCurrency.setCurrencies(repository.findAll()
                .stream()
                .map(mapper::convertToShortDto)
                .toList());
        return totalCurrency;
    }

    public void update(CurrencyDto dto) {
        log.info("CurrencyService method update executed");
        repository.save(mapper.convertToEntity(dto));
    }

    public CurrencyDto getByIsoCharCode(String charCode) {
        log.info("CurrencyService method getByCharNumCode executed");
        return mapper.convertToDto(repository.findByIsoCharCode(charCode));
    }
}
