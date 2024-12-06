package se.pj.tbike.http;

public interface Routes {

    //    Auth Api Paths
    String AUTH_LOGIN_PATH = "/api/auth/authenticate";
    String AUTH_REGISTER_PATH = "/api/auth/register";
    String AUTH_CHANGE_PASSWORD_PATH = "/api/auth/change-password";

    //    Brands Api Paths
    String CREATE_BRAND_PATH = "/api/brands";
    String DELETE_BRAND_PATH = "/api/brands/{id}";
    String FIND_BRAND_PATH = "/api/brands/{id}";
    String QUERY_BRAND_PATH = "/api/brands";
    String UPDATE_BRAND_PATH = "/api/brands/{id}";

    //    Category Api Paths
    String CREATE_CATEGORY_PATH = "/api/categories";
    String FIND_CATEGORY_PATH = "/api/categories/{id}";
    String DELETE_CATEGORY_PATH = "/api/categories/{id}";
    String QUERY_CATEGORY_PATH = "/api/categories";
    String UPDATE_CATEGORY_PATH = "/api/categories/{id}";

    //    Cart Api Paths
    String GET_OR_CREATE_CART_PATH = "/api/cart";
    String ADD_CART_ITEM_PATH = "/api/cart";

    //    Order Api Paths
    String CREATE_ORDER_PATH = "/api/orders";
    String FIND_ORDER_PATH = "/api/orders/{id}";

    //    User Api Paths
    String UPDATE_USER_INFO_PATH = "/api/user";
    String GET_USER_INFO_PATH = "/api/user";

}
