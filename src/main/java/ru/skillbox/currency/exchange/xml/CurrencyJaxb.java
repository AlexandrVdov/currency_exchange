package ru.skillbox.currency.exchange.xml;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CurrencyJaxb {
    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "NumCode")
    private Integer numCode;

    @XmlElement(name = "CharCode")
    private String charCode;

    @XmlElement(name = "Nominal")
    private Integer nominal;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Value")
    @XmlJavaTypeAdapter(DoubleAdapter.class)
    private Double value;

    @XmlElement(name = "VunitRate")
    @XmlJavaTypeAdapter(DoubleAdapter.class)
    private Double vunitRate;
}
