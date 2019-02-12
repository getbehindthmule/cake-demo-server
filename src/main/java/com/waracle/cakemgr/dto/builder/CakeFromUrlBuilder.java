package com.waracle.cakemgr.dto.builder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.dto.Cake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application-config.yml")
public class CakeFromUrlBuilder implements CakeBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CakeFromUrlBuilder.class);
    private final String url;


    public CakeFromUrlBuilder(@Value("${url.path}") String url) {
        this.url = url;
    }

    @Override
    public List<Cake> buildCakeList() {
        List<Cake> cakeList = new ArrayList<>();
        try {
            InputStream inputStream = new URL(url).openStream();

            String buffer = getJsonAsString(inputStream);

            LOGGER.debug("json is " + buffer);

            cakeList = new ObjectMapper().readValue(buffer, new TypeReference<List<Cake>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        return cakeList;
    }


    private String getJsonAsString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuffer buffer = new StringBuffer();
        String line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            line = reader.readLine();
        }
        return buffer.toString();
    }
}
