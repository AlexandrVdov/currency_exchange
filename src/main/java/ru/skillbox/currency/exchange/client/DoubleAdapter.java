package ru.skillbox.currency.exchange.client;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String value) throws Exception {
        value = value.replace(",", ".");
        return Double.parseDouble(value);
    }

    @Override
    public String marshal(Double value) throws Exception {
        return value.toString();
    }
}
