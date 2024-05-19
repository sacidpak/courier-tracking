package com.sacidpak.courier.tracking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sacidpak.courier.tracking.dto.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws IOException {
        var mockStores = List.of(
                new Store("Ataşehir", "ATA34", 40.9923307, 29.1244229),
                new Store("Novada", "NOV34", 40.986106, 29.1161293)
        );

        var mockResource = mock(Resource.class);
        var mockFile = mock(File.class);

        when(resourceLoader.getResource(anyString())).thenReturn(mockResource);
        when(mockResource.getFile()).thenReturn(mockFile);
        when(objectMapper.readValue(mockFile, Store[].class)).thenReturn(mockStores.toArray(new Store[0]));

        storeService.init();
    }

    @Test
    void shouldReturnStoreList_whenCallGetStores() {
        //given
        var storeCode = "ATA34";

        //when
        var response = storeService.getStores();

        //then
        assertThat(response).isNotEmpty()
                .extracting(Store::getCode)
                .contains(storeCode);
    }
}
