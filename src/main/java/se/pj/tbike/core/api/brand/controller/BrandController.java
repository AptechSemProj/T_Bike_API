package se.pj.tbike.core.api.brand.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.config.Urls;
import se.pj.tbike.core.api.brand.conf.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Arr;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.ResponseType;
import se.pj.tbike.io.Val;
import se.pj.tbike.core.util.SimpleController;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.util.Output;
import se.pj.tbike.validation.validator.IntegerValidator;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.validation.validator.LongValidator;

@RequiredArgsConstructor
@RestController
@RequestMapping({ BrandApiUrls.BRAND_API })
public class BrandController
		implements PageableController, SimpleController<Long> {

	private final BrandService service;
	private final BrandMapper brandMapper;
	private final ResponseMapping responseMapping;

	@Override
	public ValidationResult validatePageSize(String pageSize) {
		ValidatorsChain chain = ValidatorsChain
				.createChain()
				.addValidator( new IntegerValidator().setMin( 0 ) );
		return chain.handle( pageSize );
	}

	@GetMapping({ Urls.URL_LIST_1, Urls.URL_LIST_2 })
	public Response<Arr<BrandResponse>>
	getList(@RequestParam(defaultValue = "0") String page,
	        @RequestParam(defaultValue = "0") String size) {
		return paginated( page, size, (p, s) -> {
			Output.Array<Brand> o;
			if ( s == 0 ) {
				o = service.findAll();
			} else {
				o = service.findPage( p, s );
			}
			return o.map( brandMapper::map );
		} );
	}

	@GetMapping({ Urls.URL_INFO })
	public Response<BrandResponse>
	getOne(@PathVariable String id) {
		return get( id, k -> {
			Output.Value<Brand> o = service.findByKey( k );
			return o.map( brandMapper::map );
		} );
	}

	@PostMapping({ Urls.URL_LIST_1, Urls.URL_LIST_2 })
	public Response<Val<Object>>
	create(@RequestBody BrandRequest req) {
		return post( req, (r) -> {
			Brand b = brandMapper.map( r );
			return service.create( b ).getId();
		} );
	}

	@PutMapping({ Urls.URL_INFO })
	public Response<ResponseType>
	update(@PathVariable String id,
	       @RequestBody BrandRequest req) {
		return put( id, null, k -> {
			Brand b = brandMapper.map( req );
			b.setId( k );
			return service.update( b );
		} );
	}

	@DeleteMapping({ Urls.URL_INFO })
	public Response<ResponseType>
	delete(@PathVariable String id) {
		return delete( id, service::removeByKey );
	}

	@Override
	public ResponseMapping getResponseMapping() {
		return responseMapping;
	}

	@Override
	public ValidatorsChain validateKey() {
		return ValidatorsChain.createChain()
				.addValidator( new LongValidator().setMin( 1L ) );
	}

	@Override
	public boolean isExists(Long key) {
		return service.existsByKey( key );
	}
}
