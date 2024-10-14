package se.pj.tbike.util.json;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize( using = DynamicJsonSerializer.class )
public interface DynamicJson {

	Map<String, Object> toJson();

}
