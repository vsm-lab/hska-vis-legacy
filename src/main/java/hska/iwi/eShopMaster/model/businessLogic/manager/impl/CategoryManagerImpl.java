package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CategoryManagerImpl implements CategoryManager {

    private final String categoryBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public CategoryManagerImpl() {
        categoryBaseUrl = System.getenv("categories-service.base-url");

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
    }

    public List<Category> getCategories() {
        ResponseEntity<Category[]> response = restTemplate.exchange(categoryBaseUrl + "/categories", HttpMethod.GET, null, Category[].class);
        assert (response.getStatusCode().is2xxSuccessful());
        return Arrays.asList(response.getBody());
    }

    public Category getCategory(int id) {
        ResponseEntity<Category> response = restTemplate.exchange(categoryBaseUrl + "/categories/" + id, HttpMethod.GET, null, Category.class);
        assert (response.getStatusCode().is2xxSuccessful());
        return response.getBody();
    }

    public void addCategory(String name) {
        ResponseEntity<Void> response = restTemplate.exchange(categoryBaseUrl + "/categories?name=" + name, HttpMethod.POST, null, Void.class);
        assert (response.getStatusCode().is2xxSuccessful());
    }

    public void delCategoryById(int id) {
        ResponseEntity<Void> response = restTemplate.exchange(categoryBaseUrl + "/categories/" + id, HttpMethod.DELETE, null, Void.class);
        assert (response.getStatusCode().is2xxSuccessful());
    }
}
