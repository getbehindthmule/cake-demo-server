package com.waracle.cakemgr.dto.builder;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dto.Cake;

import java.util.List;

// abstract behind an interface because it may be likely that alternate sources of the cake list are needed, eg file based
public interface CakeBuilder {
    List<Cake> buildCakeList();

    static Cake mapToCake(CakeEntity entity) {
        return new Cake(entity.getTitle(), entity.getDescription(), entity.getImage());
    }
}
