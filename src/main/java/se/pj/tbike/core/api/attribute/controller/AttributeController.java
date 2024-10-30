package se.pj.tbike.core.api.attribute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.core.util.SimpleController;

@RequiredArgsConstructor
@RestController
public class AttributeController
		implements SimpleController<Long> {

	private final AttributeService service;
	private final ResponseMapping responseMapping;

	@GetMapping()
	public void getList() {

	}

	@Override
	public ResponseMapping getResponseMapping() {
		return responseMapping;
	}

	@Override
	public ValidatorsChain validateKey( ) {
		return null;
	}

	@Override
	public boolean isExists(Long key) {
		return false;
	}

}
