package com.vintrace.exam.controller;


import com.vintrace.exam.model.Wine;
import com.vintrace.exam.service.WineService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Slf4j
@RequestScoped
public class WineBean {

    @Inject
    private WineService service;
    private Wine wine;
    private String lotCodeSearch;
    private String descriptionSearch;
    private Double volumeSearch;
    private String tankCodeSearch;
    private String productStateSearch;
    private String ownerNameSearch;

    public Wine getWine() {
        return wine;
    }

    @PostConstruct
    public void init() {
        wine = Wine.builder().build();
    }

    public Wine searchWine() throws IOException {
         wine = service.searchWine(getLotCodeSearch(), getDescriptionSearch(), getOwnerNameSearch(), getProductStateSearch(), getTankCodeSearch());
         return wine;
    }

    public String getLotCodeSearch() {
        return lotCodeSearch;
    }

    public void setLotCodeSearch(String lotCodeSearch) {
        this.lotCodeSearch = lotCodeSearch;
    }

    public String getDescriptionSearch() {
        return descriptionSearch;
    }

    public void setDescriptionSearch(String descriptionSearch) {
        this.descriptionSearch = descriptionSearch;
    }

    public Double getVolumeSearch() {
        return volumeSearch;
    }

    public void setVolumeSearch(Double volumeSearch) {
        this.volumeSearch = volumeSearch;
    }

    public String getTankCodeSearch() {
        return tankCodeSearch;
    }

    public void setTankCodeSearch(String tankCodeSearch) {
        this.tankCodeSearch = tankCodeSearch;
    }

    public String getProductStateSearch() {
        return productStateSearch;
    }

    public void setProductStateSearch(String productStateSearch) {
        this.productStateSearch = productStateSearch;
    }

    public String getOwnerNameSearch() {
        return ownerNameSearch;
    }

    public void setOwnerNameSearch(String ownerNameSearch) {
        this.ownerNameSearch = ownerNameSearch;
    }
}
