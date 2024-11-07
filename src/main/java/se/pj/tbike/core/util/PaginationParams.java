package se.pj.tbike.core.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaginationParams {

    private Integer page;

    private Integer size;

    public int getPage(int def) {
        return page != null ? page : def;
    }

    public int getSize(int def) {
        return size != null ? size : def;
    }

}
