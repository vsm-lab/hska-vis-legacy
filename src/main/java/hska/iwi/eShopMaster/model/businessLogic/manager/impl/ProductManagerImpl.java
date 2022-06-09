package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ProductManagerImpl implements ProductManager {

    private final String productBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ProductManagerImpl() {
        this.productBaseUrl = System.getenv("products-service.base-url");

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
    }

    public List<Product> getProducts() {
        ResponseEntity<Product[]> response = restTemplate.exchange(productBaseUrl + "/products", HttpMethod.GET, null, Product[].class);
        assert (response.getStatusCode().is2xxSuccessful());
        return fetchCategoriesForProducts(response.getBody());
    }

    public List<Product> getProductsForSearchValues(String searchDescription,
                                                    Double searchMinPrice, Double searchMaxPrice) {
        ResponseEntity<Product[]> response = restTemplate.exchange(productBaseUrl + "/products/search?" +
                        (searchDescription != null ? "description=" + searchDescription : "") +
                        (searchMinPrice != null ? "&minPrice=" + searchMinPrice : "") +
                        (searchMaxPrice != null ? "&maxPrice=" + searchMaxPrice : ""),
                HttpMethod.GET, null, Product[].class);
        assert (response.getStatusCode().is2xxSuccessful());
        return fetchCategoriesForProducts(response.getBody());
    }

    public Product getProductById(int id) {
        ResponseEntity<Product> response = restTemplate.exchange(productBaseUrl + "/products/" + id, HttpMethod.GET, null, Product.class);
        assert (response.getStatusCode().is2xxSuccessful());
        return fetchCategoryForProduct(response.getBody());
    }

    public int addProduct(String name, double price, int categoryId, String details) {
        Product product = new Product(name, price, categoryId, details);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Product> requestBody = new HttpEntity<>(product, headers);
        ResponseEntity<Integer> response = restTemplate.exchange(productBaseUrl + "/products", HttpMethod.POST, requestBody, Integer.class);
        assert (response.getStatusCode().is2xxSuccessful());
        return response.getBody();
    }

    public void deleteProductById(int id) {
        ResponseEntity<Void> response = restTemplate.exchange(productBaseUrl + "/products/" + id, HttpMethod.DELETE, null, Void.class);
        assert (response.getStatusCode().is2xxSuccessful());
    }

    private Product fetchCategoryForProduct(Product product) {
        List<Product> products = fetchCategoriesForProducts(new Product[]{product});
        return products.get(0);
    }

    private List<Product> fetchCategoriesForProducts(Product[] products) {
        CategoryManager categoryManager = new CategoryManagerImpl();
        List<Category> categories = categoryManager.getCategories();
        Map<Integer, Category> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getId(), category);
        }
        for (Product product : products) {
            product.setCategory(categoryMap.get(product.getCategoryId()));
        }
        return Arrays.asList(products);
    }

}
