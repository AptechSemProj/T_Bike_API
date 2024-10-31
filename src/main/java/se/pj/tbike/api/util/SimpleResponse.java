package se.pj.tbike.api.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = SimpleResponseJsonSerializer.class)
public class SimpleResponse<T>
		implements Response<T> {

	private final Configuration conf;

	private T data;

	public SimpleResponse(Configuration conf) {
		this.conf = conf;
	}

	public SimpleResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

	final Object toJson() {
		ResponseTemplate template = conf.getTemplate();
		if ( template.isConfigured() ) {
			return template.body( data ).toJson();
		}
		return data;
	}

	@Override
	public T getBody() {
		return null;
	}

	@Override
	public int getCode() {
		return 0;
	}
}
