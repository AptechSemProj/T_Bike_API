package se.pj.tbike.api.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = StdResponseJsonSerializer.class)
public class StdResponse<T>
		implements Response<T> {

	private final ResponseConfiguration conf;

	private T data;

	public StdResponse(ResponseConfiguration conf) {
		this.conf = conf;
	}

	public StdResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

	public final Object toJson() {
		ResponseTemplate template = conf.getTemplate();
		if ( template.isConfigured() ) {
			return template.body( data ).toJson();
		}
		return data;
	}
}
