package se.pj.tbike.api.core.product.controller;

import static se.pj.tbike.api.common.Urls.API_PREFIX;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.core.product.data.AttributeService;
import se.pj.tbike.api.util.Validated;
import se.pj.tbike.api.util.controller.StdController;

@RequiredArgsConstructor
@RestController
public class AttributeController
		implements StdController<Long> {

	private final AttributeService service;

	@GetMapping()
	public void getList() {

	}

	@Override
	public Validated<Long> validateKey( String keyInPath ) {
		return null;
	}

}
