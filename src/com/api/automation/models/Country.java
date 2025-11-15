package com.api.automation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

/**
 * POJO for Country data from REST Countries API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private Map<String, Object> name;
    private List<String> capital;
    private String region;
    private String subregion;
    private List<Double> latlng;
    private String area;
    private List<String> timezones;
    private List<String> borders;
    private Map<String, Object> currencies;
    private Map<String, Object> languages;
    private String flag;
    private String cca2;
    private String cca3;
    private String ccn3;
    private String cioc;
    private Map<String, Object> translations;
    private Map<String, Object> demonyms;
    private boolean independent;
    private String status;
    private String unMember;
    private Map<String, Object> maps;
    private Object gini;
    private String fifa;
    private Map<String, Object> car;
    private Object coatOfArms;

    // Constructors
    public Country() {}

    // Getters and Setters
    public Map<String, Object> getName() {
        return name;
    }

    public void setName(Map<String, Object> name) {
        this.name = name;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public Map<String, Double> getLatlng() {
        return null; // latlng is an array, not a map
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public List<Double> getLatLngCoordinates() {
        return latlng;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public Map<String, String> getCurrencies() {
        return (Map<String, String>) (Object) currencies;
    }

    public void setCurrencies(Map<String, Object> currencies) {
        this.currencies = currencies;
    }

    public Map<String, String> getLanguages() {
        return (Map<String, String>) (Object) languages;
    }

    public void setLanguages(Map<String, Object> languages) {
        this.languages = languages;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCca2() {
        return cca2;
    }

    public void setCca2(String cca2) {
        this.cca2 = cca2;
    }

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public String getCcn3() {
        return ccn3;
    }

    public void setCcn3(String ccn3) {
        this.ccn3 = ccn3;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }

    public Map<String, Object> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, Object> translations) {
        this.translations = translations;
    }

    public Map<String, String> getDemonyms() {
        return (Map<String, String>) (Object) demonyms;
    }

    public void setDemonyms(Map<String, Object> demonyms) {
        this.demonyms = demonyms;
    }

    public boolean isIndependent() {
        return independent;
    }

    public void setIndependent(boolean independent) {
        this.independent = independent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnMember() {
        return unMember;
    }

    public void setUnMember(String unMember) {
        this.unMember = unMember;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public String getGini() {
        return String.valueOf(gini);
    }

    public void setGini(Object gini) {
        this.gini = gini;
    }

    public String getFifa() {
        return fifa;
    }

    public void setFifa(String fifa) {
        this.fifa = fifa;
    }

    public Map<String, Object> getCar() {
        return car;
    }

    public void setCar(Map<String, Object> car) {
        this.car = car;
    }

    public List<String> getCoatOfArms() {
        return coatOfArms == null ? null : (coatOfArms instanceof List ? (List<String>) coatOfArms : null);
    }

    public void setCoatOfArms(Object coatOfArms) {
        this.coatOfArms = coatOfArms;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name=" + name +
                ", capital=" + capital +
                ", region='" + region + '\'' +
                ", cca2='" + cca2 + '\'' +
                '}';
    }
}
