package se.pj.tbike.api.io;

import java.util.HashMap;
import java.util.Map;

import se.pj.tbike.util.json.DynamicJson;
import se.pj.tbike.util.result.ResultPage;

public class Pagination<E extends ResponseType>
		extends Response<Arr<E>> {

	private static final int STATUS_CODE = 200;

	private final Metadata metadata;

	private final Arr<E> data;

	public Pagination( ResultPage<E> page ) {
		this( page, Message.OK.getValue() );
	}

	public Pagination( ResultPage<E> page, String message ) {
		super( STATUS_CODE, Arr.of( page.toList() ), message );
		this.metadata = new Metadata( page );
		this.data = Arr.of( page.toList() );
	}

	@Override
	public Map<String, Object> toJson() {
		Map<String, Object> json = super.toJson();
		json.put( "data", data.get() );
		json.put( "metadata", metadata.toJson() );
		return json;
	}

	private static class Metadata implements DynamicJson {

		private final long totalElements;
		private final int totalPages, size, current;
		private final Integer next, previous;

		public Metadata( ResultPage<?> r ) {
			this.totalElements = r.getTotalElements();
			this.totalPages = r.getTotalPages();
			this.size = r.getSize();
			this.current = r.getNumber();
			this.next = r.next();
			this.previous = r.previous();
		}

		@Override
		public Map<String, Object> toJson() {
			return new HashMap<>() {{
				put( "total_elements", totalElements );
				put( "total_pages", totalPages );
				put( "page_size", size );
				put( "current_page", current );
				put( "next", next );
				put( "previous", previous );
			}};
		}
	}
}
