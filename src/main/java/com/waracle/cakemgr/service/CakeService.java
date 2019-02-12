package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.dto.builder.CakeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CakeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CakeService.class);

    @Autowired
    public void setCakeRepository(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Autowired
    public void setCakeBuilder(CakeBuilder cakeBuilder) {
        this.cakeBuilder = cakeBuilder;
    }

    private CakeRepository cakeRepository;

    private CakeBuilder cakeBuilder;


    public CakeService() {

    }

    @PostConstruct
    public void initialiseCakeList() {
        LOGGER.debug("initialiseCakeList");

        List<Cake> cakeList = this.cakeBuilder.buildCakeList();


        Set<CakeEntity> uniqueCakes = cakeList.stream()
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(Cake::getTitle)))).stream()
                .map(cakeDto -> new CakeEntity(cakeDto))
                .collect(Collectors.toSet());

        uniqueCakes.forEach(cakeEntity -> this.cakeRepository.save(cakeEntity));

    }

    public List<Cake> getCakes() {
        LOGGER.debug("getCakes");

        List<Cake> cakes = StreamSupport.stream(cakeRepository.findAll().spliterator(), false)
                .map(cakeEntity -> CakeBuilder.mapToCake(cakeEntity))
                .sorted(Comparator.comparing(Cake::getTitle))
                .collect(Collectors.toList());

        LOGGER.debug("cakes returned: " + cakes);

        return cakes;
    }

    public void updateCake(Cake cake) {
        LOGGER.debug("updateCake: " + cake.toString());

        CakeEntity cakeEntity = Optional.ofNullable(cakeRepository.findByTitle(cake.getTitle())).orElseGet(CakeEntity::new);

        LOGGER.debug("original cakeEntity: " + cakeEntity.toString());

        cakeEntity.setTitle(cake.getTitle());
        cakeEntity.setDescription(cake.getDesc());
        cakeEntity.setImage(cake.getImage());

        LOGGER.debug("updated cakeEntity: " + cakeEntity.toString());

        cakeRepository.save(cakeEntity);

    }


}
