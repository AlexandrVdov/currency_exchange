package ru.skillbox.currency.exchange.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.skillbox.currency.exchange.xml.CurrencyJaxb;
import ru.skillbox.currency.exchange.xml.ValCursJaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyLoader {
    @Value("${bank.api.getCurrencies}")
    private String uri;

    public List<CurrencyJaxb> getCurrencies() {
        String xmlData = loadData(uri);
        ValCursJaxb valCursJaxb = null;
        try {
            valCursJaxb = unmarshal(xmlData);
        } catch (JAXBException e) {
            log.error("Error while getting curses from bank", e);
        }
        return (valCursJaxb != null) ? valCursJaxb.getCurrencyJaxbList() : new ArrayList<>();
    }

    private String loadData(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    private ValCursJaxb unmarshal(String xmlData) throws JAXBException {
        StringReader reader = new StringReader(xmlData);

        JAXBContext context = JAXBContext.newInstance(ValCursJaxb.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (ValCursJaxb) unmarshaller.unmarshal(reader);
    }
}
