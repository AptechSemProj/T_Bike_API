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
    String CREATE_ORDER_PATH = "/api/orders";
    String FIND_CATEGORY_PATH = "/api/categories/{id}";
    String DELETE_CATEGORY_PATH = "/api/categories/{id}";
    String UPDATE_CATEGORY_PATH = "/api/categories/{id}";
    String FIND_ORDER_PATH = "/api/orders/{id}";
    String AUTH_LOGIN_PATH = "/api/auth/authenticate";
    String AUTH_REGISTER_PATH = "/api/auth/register";
    String UPDATE_USER_INFO_PATH = "/api/user";
}
