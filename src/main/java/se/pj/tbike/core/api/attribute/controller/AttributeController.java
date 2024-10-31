package se.pj.tbike.core.api.attribute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.util.RequestHandler;
import se.pj.tbike.api.util.SimpleRequestHandler;
import se.pj.tbike.core.api.attribute.conf.AttributeMapper;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.api.attribute.dto.AttributeRequest;
import se.pj.tbike.core.api.attribute.dto.AttributeResponse;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.core.util.SimpleController;

@RequiredArgsConstructor
@RestController
public class AttributeController
		implements SimpleController<Long> {

	private final AttributeService service;
	private final AttributeMapper mapper;
	private final ResponseMapping responseMapping;

	@GetMapping()
	public void getList() {
		RequestHandler<Attribute, AttributeRequest, AttributeResponse>
				handler = new SimpleRequestHandler<>();
		handler.handle( (res) -> {

		} );

		RequestHandler<Attribute, AttributeRequest, Long>
				handler1 = new SimpleRequestHandler<>();
		var resp = handler1.handle( (res, body) -> {
		}, null );

	}

	@Override
	public ResponseMapping getResponseMapping() {
		return responseMapping;
	}

	@Override
	public ValidatorsChain validateKey() {
		return null;
	}

	@Override
	public boolean isExists(Long key) {
		return false;
	}

}
