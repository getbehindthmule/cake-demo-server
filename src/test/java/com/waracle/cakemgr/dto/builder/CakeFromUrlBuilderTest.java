package com.waracle.cakemgr.dto.builder;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dto.Cake;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class CakeFromUrlBuilderTest {

    @Test
    public void testSuccessfulRetreivel() {
        // arrange
        String validUrl = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
        CakeFromUrlBuilder cakeFromUrlBuilder = new CakeFromUrlBuilder(validUrl);

        // act
        List<Cake> actualList = cakeFromUrlBuilder.buildCakeList();

        // assert
        assertThat(actualList)
                .hasSize(20);
    }


    @Test
    public void testFailedConnection() {
        // arrange
        String validUrl = "";
        CakeFromUrlBuilder cakeFromUrlBuilder = new CakeFromUrlBuilder(validUrl);

        // act
        List<Cake> actualList = cakeFromUrlBuilder.buildCakeList();

        // assert
        assertThat(actualList)
                .hasSize(0);
    }

    @Test
    public void testBuildFromCakeEntityList() {
        // arrange
        CakeEntity cakeEntity1 = new CakeEntity();
        cakeEntity1.setTitle("title-1");
        cakeEntity1.setDescription("description-1");
        cakeEntity1.setImage("image-1");
        Cake expectedCake = new Cake("title-1", "description-1", "image-1");

        // act
        Cake actualCake = CakeBuilder.mapToCake(cakeEntity1);

        assertThat(actualCake).isEqualTo(expectedCake);
    }

}