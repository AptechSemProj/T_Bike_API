package com.sem.proj.tbike.api.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class PaginationResponse<E>
		extends Response<Collection<E>> {

	private Metadata metadata;

	public PaginationResponse( int status, Message message, Collection<E> data ) {
		super( status, message.getValue(), data );
	}

	public PaginationResponse( int status, String message, Collection<E> data ) {
		super( status, message, data );
	}

	@Override
	public Map<String, Object> toJson() {
		Map<String, Object> json = super.toJson();
		json.put( "metadata", metadata );
		return json;
	}

	@AllArgsConstructor
	@Data
	public static class Metadata {

		@JsonProperty( "current_page" )
		private int currentPage;

		@JsonProperty( "page_size" )
		private int pageSize;

		private Integer next, previous;

		@JsonProperty( "total_pages" )
		private int totalPage;

		@JsonProperty( "total_elements" )
		private int totalElements;

	}
}
