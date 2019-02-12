package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPutCakes() throws Exception {
        String cakeAsJson = "{\"title\" : \"victoria sponge\", \"desc\":\"description-1\", \"image\":\"image-1\"}";

        MvcResult mvcResult = this.mockMvc.perform(post("/cakes")
                .contentType(MediaType.APPLICATION_JSON).content(cakeAsJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase("true");

    }

    @Test
    public void testGetCakeJson() throws Exception {

        this.mockMvc.perform(get("/cakes").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Banana cake"))
                .andExpect(jsonPath("$[1].title").value("Birthday cake"))
                .andExpect(jsonPath("$[2].title").value("Carrot cake"))
                .andExpect(jsonPath("$[3].title").value("Lemon cheesecake"))
                .andExpect(jsonPath("$[4].title").value("victoria sponge"));
    }

    @Test
    public void testGetCakeBrowser() throws Exception {

        this.mockMvc.perform(get("/cakes").contentType(MediaType.TEXT_PLAIN))
                .andExpect(redirectedUrl("http://localhost:8080"));

    }



}