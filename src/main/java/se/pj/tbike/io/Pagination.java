package se.pj.tbike.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import se.pj.tbike.util.json.DynamicJson;
import se.pj.tbike.util.result.ResultPage;

@Deprecated
public class Pagination<E extends ResponseType>
        extends Response<Arr<E>> {

    /**
     * pagination always have one status code is 200.
     */
    private static final Status STATUS = Status.OK;

    private final Metadata metadata;

    public Pagination(E[] data, Metadata metadata,
                      String message) {
        super(STATUS, Arr.of(data),
                message != null ? message : STATUS.getMessage());
        this.metadata = metadata;
    }

    public Pagination(E[] data, Metadata metadata) {
        this(data, metadata, STATUS.getMessage());
    }

    public Pagination(Collection<E> data, Metadata metadata,
                      String message) {
        super(STATUS, Arr.of(data),
                message != null ? message : STATUS.getMessage());
        this.metadata = metadata;
    }

    public Pagination(Collection<E> data, Metadata metadata) {
        this(data, metadata, STATUS.getMessage());
    }

    @Deprecated
    public Pagination(ResultPage<E> page, String message) {
        super(STATUS, Arr.of(page.toList()),
                message != null ? message : STATUS.getMessage());
        this.metadata = new Metadata(page);
    }

    @Deprecated
    public Pagination(ResultPage<E> page) {
        this(page, STATUS.getMessage());
    }

    //********************* Implements DynamicJson *********************//

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> json = super.toJson();
        json.put("metadata", metadata.toJson());
        return json;
    }

    @Deprecated
    public static class Metadata implements DynamicJson {

        private final long totalElements;
        private final int totalPages, size, current;
        private final Integer next, previous;

        public Metadata(long totalElements, int totalPages,
                        int size, int current,
                        Integer next, Integer previous) {
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.size = size;
            this.current = current;
            this.next = next;
            this.previous = previous;
        }

        @Deprecated
        public Metadata(ResultPage<?> r) {
            this.totalElements = r.getTotalElements();
            this.totalPages = r.getTotalPages();
            this.size = r.getSize();
            this.current = r.getNumber();
            this.next = r.next();
            this.previous = r.previous();
        }

        //********************* Implements DynamicJson *********************//

        @Override
        public Map<String, Object> toJson() {
            return new HashMap<>() {{
                put("total_elements", totalElements);
                put("total_pages", totalPages);
                put("page_size", size);
                put("current_page", current);
                put("next", next);
                put("previous", previous);
            }};
        }
    }
}
