package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.dto.builder.CakeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.*;

public class CakeServiceTest {
    private CakeRepository cakeRepository = mock(CakeRepository.class);
    private CakeBuilder cakeBuilder = mock(CakeBuilder.class);

    private CakeService sut;

    @Before
    public void setUp() {
        sut = new CakeService();
        sut.setCakeBuilder(cakeBuilder);
        sut.setCakeRepository(cakeRepository);
    }

    private Cake addCake(String title, String description, String imageName) {
        Cake cake = new Cake();
        cake.setTitle(title);
        cake.setDesc(description);
        cake.setImage(imageName);
        return cake;
    }


    private List<CakeEntity> createCakeEntities() {
        CakeEntity cakeEntity1 = new CakeEntity();
        cakeEntity1.setTitle("title-1");
        cakeEntity1.setDescription("description-1");
        cakeEntity1.setImage("image-1");
        CakeEntity cakeEntity2 = new CakeEntity();
        cakeEntity2.setTitle("title-2");
        cakeEntity2.setDescription("description-2");
        cakeEntity2.setImage("image-2");
        return new ArrayList<CakeEntity>(Arrays.asList(cakeEntity1, cakeEntity2));
    }

    @Test
    public void testInitialiseCakeList() {
        // arrange
        List<Cake> inputCakeList = Arrays.asList(
                addCake("title-1", "description-1", "image-1"),
                addCake("title-2", "description-2", "image-2"));

        when(cakeBuilder.buildCakeList()).thenReturn(inputCakeList);

        // act
        sut.initialiseCakeList();

        // assert
        verify(cakeRepository, times(2)).save(any());


    }


    @Test
    public void testInitialiseCakeListWithDuplicates() {
        // arrange
        List<Cake> inputCakeList = Arrays.asList(
                addCake("title-1", "description-1", "image-1"),
                addCake("title-1", "description-3", "image-3"),
                addCake("title-2", "description-2", "image-2"));

        when(cakeBuilder.buildCakeList()).thenReturn(inputCakeList);

        // act
        sut.initialiseCakeList();

        // assert
        verify(cakeRepository, times(2)).save(any());

    }


    @Test
    public void testInitialiseCakeListWithEmptyList() {
        // arrange
        List<Cake> inputCakeList = Collections.emptyList();

        when(cakeBuilder.buildCakeList()).thenReturn(inputCakeList);

        // act
        sut.initialiseCakeList();

        // assert
        verify(cakeRepository, never()).save(any());


    }

    @Test
    public void testGetCakes() {
        //arrange
        when(cakeRepository.findAll()).thenReturn(this.createCakeEntities());

        // act
        List<Cake> actualCakes = sut.getCakes();

        // assert
        assertThat(actualCakes).hasSize(2)
                .extracting("title", "desc", "image")
                .contains(
                        tuple("title-1", "description-1", "image-1"),
                        tuple("title-2", "description-2", "image-2"));

    }

    @Test
    public void testGetCakesEmptyList() {
        //arrange
        when(cakeRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        // act
        List<Cake> actualCakes = sut.getCakes();

        // assert
        assertThat(actualCakes).hasSize(0);

    }


    @Test
    public void testAddNewCake() {
        // arrange
        Cake cake = addCake("title-1", "description-1", "image-1");
        ArgumentCaptor<CakeEntity> cakeEntityArgument = ArgumentCaptor.forClass(CakeEntity.class);
        when(cakeRepository.findByTitle(cake.getTitle())).thenReturn(null);

        // act
        sut.updateCake(cake);

        // assert
        verify(cakeRepository).save(cakeEntityArgument.capture());
        assertThat(cakeEntityArgument.getValue().getTitle()).isEqualTo(cake.getTitle());
        assertThat(cakeEntityArgument.getValue().getDescription()).isEqualTo(cake.getDesc());
        assertThat(cakeEntityArgument.getValue().getImage()).isEqualTo(cake.getImage());
    }


    @Test
    public void testUpdateExistingCake() {
        // arrange
        Cake cake = addCake("title-1", "description-1", "image-1");
        ArgumentCaptor<CakeEntity> cakeEntityArgument = ArgumentCaptor.forClass(CakeEntity.class);
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setTitle(cake.getTitle());
        cakeEntity.setDescription("another description");
        cakeEntity.setImage("anothe image");
        when(cakeRepository.findByTitle(cake.getTitle())).thenReturn(cakeEntity);

        // act
        sut.updateCake(cake);

        // assert
        verify(cakeRepository).save(cakeEntityArgument.capture());
        assertThat(cakeEntityArgument.getValue().getTitle()).isEqualTo(cake.getTitle());
        assertThat(cakeEntityArgument.getValue().getDescription()).isEqualTo(cake.getDesc());
        assertThat(cakeEntityArgument.getValue().getImage()).isEqualTo(cake.getImage());
    }
}