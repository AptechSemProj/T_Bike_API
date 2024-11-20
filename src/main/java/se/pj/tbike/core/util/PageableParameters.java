package se.pj.tbike.core.util;

import com.ank.japi.Request;
import org.springframework.util.NumberUtils;

import java.beans.ConstructorProperties;

public class PageableParameters implements Request<Object> {

    private final String page;
    private final String size;

    @ConstructorProperties({"page", "size"})
    public PageableParameters(String page, String size) {
        this.page = page;
        this.size = size;
    }

    public int getPageNumber(int def) {
        try {
            return NumberUtils.parseNumber(page, Integer.class);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    public int getPageSize(int def) {
        try {
            return NumberUtils.parseNumber(size, Integer.class);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }
}