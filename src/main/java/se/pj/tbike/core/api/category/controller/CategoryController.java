package se.pj.tbike.core.api.category.controller;

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
import se.pj.tbike.core.api.category.conf.CategoryApiUrls;
import se.pj.tbike.core.config.Urls;
import se.pj.tbike.core.api.category.conf.CategoryMapper;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.core.api.category.data.CategoryService;
import se.pj.tbike.core.api.category.dto.CategoryRequest;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Arr;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.ResponseType;
import se.pj.tbike.io.Val;
import se.pj.tbike.util.Output;
import se.pj.tbike.validation.validator.IntegerValidator;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.core.util.SimpleController;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.validation.validator.LongValidator;

@RequiredArgsConstructor
@RestController
@RequestMapping({ CategoryApiUrls.CATEGORY_API })
public class CategoryController
		implements PageableController, SimpleController<Long> {

	private final CategoryService service;
	private final CategoryMapper categoryMapper;
	private final ResponseMapping responseMapping;

	@Override
	public ValidationResult validatePageSize(String pageSize) {
		ValidatorsChain chain = ValidatorsChain
				.createChain()
				.addValidator( new IntegerValidator().acceptMinValue( 0 ) );
		return chain.handle( pageSize );
	}

	@GetMapping({ Urls.URL_LIST_1, Urls.URL_LIST_2 })
	public Response<Arr<CategoryResponse>>
	getList(@RequestParam(defaultValue = "0") String page,
	        @RequestParam(defaultValue = "0") String size) {
		return paginated( page, size, (p, s) -> {
			Output.Array<Category> o;
			if ( s == 0 ) {
				o = service.findAll();
			} else {
				o = service.findPage( p, s );
			}
			return o.map( categoryMapper::map );
		} );
	}

	@GetMapping({ Urls.URL_INFO })
	public Response<CategoryResponse>
	getById(@PathVariable String id) {
		return get( id, k -> {
			Output.Value<Category> o = service.findByKey( k );
			return o.map( categoryMapper::map );
		} );
	}

	@PostMapping({ Urls.URL_LIST_1, Urls.URL_LIST_2 })
	public Response<Val<Object>>
	create(@RequestBody CategoryRequest req) {
		return post( req, (r) -> {
			Category c = categoryMapper.map( r );
			return service.create( c ).getId();
		} );
	}

	@PutMapping({ Urls.URL_INFO })
	public Response<ResponseType>
	update(@PathVariable String id,
	       @RequestBody CategoryRequest req) {
		return put( id, null, k -> {
			Category c = categoryMapper.map( req );
			c.setId( k );
			return service.update( c );
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
				.addValidator( new LongValidator().acceptMinValue( 1L ) );
	}

	@Override
	public boolean isExists(Long key) {
		return service.existsByKey( key );
	}
}
