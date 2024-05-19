package com.sacidpak.courier.tracking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sacidpak.courier.tracking.dto.Store;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreService {

    private List<Store> stores;

    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws IOException {
        if (stores == null) {
            var resource = resourceLoader.getResource("classpath:stores.json");
            stores = Arrays.asList(objectMapper.readValue(resource.getFile(), Store[].class));
        }
    }

    public List<Store> getStores() {
        return stores;
    }
}
