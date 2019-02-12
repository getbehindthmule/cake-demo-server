package com.waracle.cakemgr.dao;

import com.waracle.cakemgr.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class)
public class CakeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CakeRepository cakeRepository;

    @Before
    public void clearDB() {
        cakeRepository.findAll().forEach(entry -> entityManager.remove(entry));
    }


    @Test
    public void testThatEntriesCanBeRetreived() {
        // arrange
        CakeEntity expectedCake = createCakeEntity();
        entityManager.persist(expectedCake);

        // act
        List<CakeEntity> actualCakes = StreamSupport.stream(cakeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        // assert
        assertThat(actualCakes).hasSize(1)
                .contains(expectedCake);
    }

    @Test
    public void testWhenNoEntries() {
        // arrange

        // act
        List<CakeEntity> actualCakes = StreamSupport.stream(cakeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        // assert
        assertThat(actualCakes).hasSize(0);

    }

    private CakeEntity createCakeEntity() {
        CakeEntity cakeEntity1 = new CakeEntity();
        cakeEntity1.setTitle("title-1");
        cakeEntity1.setDescription("description-1");
        cakeEntity1.setImage("image-1");
        return cakeEntity1;
    }
}