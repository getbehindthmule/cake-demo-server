package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.service.CakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class CakeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CakeController.class);
    private CakeService cakeService;

    @Autowired
    public void setCakeService(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @RequestMapping(value = "/cakes", method = RequestMethod.GET)
    public void getCakes(HttpServletResponse servletResponse) throws IOException{
        LOGGER.debug("GET /cakes from browser");
        servletResponse.sendRedirect("http://localhost:8080");

    }

    @RequestMapping(value = "/cakes", method = RequestMethod.GET, headers = "content-type=application/json")
    public List<Cake> getCakes() {
        LOGGER.debug("GET /cakes for json");
        return this.cakeService.getCakes();

    }

    @RequestMapping(value = "/cakes", method = RequestMethod.POST)
    public Boolean addCakes(@RequestBody Cake cake) {
        LOGGER.debug("POST /cakes" + cake.toString());

        this.cakeService.updateCake(cake);
        return true;

    }
}
