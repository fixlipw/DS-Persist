package com.ufc.ds_persist.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "estande")
public class Leituras {

    @JacksonXmlElementWrapper(localName = "leituras")
    @JacksonXmlProperty(localName = "leitura")
    private List<Leitura> leituras;

    public Leituras(List<Leitura> leituras) {
        this.leituras = leituras;
    }

    public List<Leitura> getLeituras() {
        return leituras;
    }

    public void setLeituras(List<Leitura> leituras) {
        this.leituras = leituras;
    }

    @Override
    public String toString() {
        return "Leituras{" +
                "leituraList=" + leituras +
                '}';
    }
}