package se.pj.tbike.http;

public interface Routes {
    String CREATE_BRAND_PATH = "/api/brands";
    String DELETE_BRAND_PATH = "/api/brands/{id}";
    String FIND_BRAND_PATH = "/api/brands/{id}";
    String QUERY_BRAND_PATH = "/api/brands";
    String UPDATE_BRAND_PATH = "/api/brands/{id}";
    String CREATE_CATEGORY_PATH = "/api/categories";
    String GET_CART_PATH = "/api/cart";
    String ADD_CART_ITEM_PATH = "/api/cart";
}
