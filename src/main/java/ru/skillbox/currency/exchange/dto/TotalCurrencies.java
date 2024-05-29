package ru.skillbox.currency.exchange.dto;

import lombok.Data;

import java.util.List;

@Data
public class TotalCurrencies {
    private List<CurrenciesItem> currencies;
}
