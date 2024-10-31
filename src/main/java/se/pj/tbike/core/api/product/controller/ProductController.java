package se.pj.tbike.core.api.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.api.util.Response.Configuration;
import se.pj.tbike.api.util.SimpleResponse;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.api.product.conf.ProductApiUrls;
import se.pj.tbike.core.api.product.conf.ProductMapper;
import se.pj.tbike.core.api.product.dto.ProductCreation;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.product.data.ProductService;
import se.pj.tbike.core.api.product.dto.ProductResponse;
import se.pj.tbike.core.api.product.dto.ProductDetail;
import se.pj.tbike.core.api.attribute.conf.AttributeMapper;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.category.data.CategoryService;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.core.config.Urls;
import se.pj.tbike.core.httpclient.ImageHttpClient;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Arr;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.Val;
import se.pj.tbike.util.Output;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.core.util.SimpleController;
import se.pj.tbike.validation.validator.InvalidValidator;
import se.pj.tbike.validation.validator.LongValidator;
import se.pj.tbike.validation.error.NotExistError;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping({ ProductApiUrls.PRODUCT_API })
public class ProductController
		implements PageableController,
		SimpleController<Long> {

	private final BrandService brandService;
	private final ProductService service;
	private final CategoryService categoryService;
	private final AttributeService attributeService;
	private final ProductMapper productMapper;
	private final AttributeMapper attributeMapper;
	private final ResponseMapping responseMapping;
	private final ImageHttpClient imageClient;

	@GetMapping({ Urls.URL_LIST_1, Urls.URL_LIST_2 })
	public Response<Arr<ProductResponse>>
	getList(@RequestParam(defaultValue = "0") String page,
	        @RequestParam(defaultValue = "10") String size) {
		return paginated( page, size, (p, s) -> {
			Output.Pagination<Product> o = service.findPage( p, s );
			return o.map( productMapper::toResponse );
		} );
	}

	@GetMapping({ Urls.URL_INFO })
	public Response<ProductDetail>
	getDetail(@PathVariable String id) {
		return get( id, k -> {
			Output.Value<Product> r = service.findByKey( k );
			return r.map( productMapper::toDetail );
		} );
	}

	@PostMapping({ Urls.URL_LIST_1, Urls.URL_LIST_2 })
	public Response<Val<Object>>
	create(@ModelAttribute ProductCreation req) {
		return post( req, (r) -> {
			Output.Value<Brand> brand = brandService
					.findByKey( r.getBrandId() );
			if ( brand.isNull() ) {
				return responseMapping.getResponse(
						NotExistError.builder().build()
				);
			}

			Output.Value<Category> category = categoryService
					.findByKey( r.getCategoryId() );
			if ( category.isNull() ) {
				return responseMapping.getResponse(
						NotExistError.builder().build()
				);
			}

			Product p = productMapper.fromCreation( req );

			p.setBrand( brand.get() );
			p.setCategory( category.get() );

			List<Attribute> attrs = new ArrayList<>();
			p.setAttributes( attrs );
			Product created = service.create( p );

			req.getColors().forEach( attr -> {
				Attribute attribute = attributeMapper.map( attr );
				attribute.setProduct( created );
				attrs.add( attribute );
			} );

			attrs.forEach( attributeService::create );

			return created.getId();
		} );
	}

	@GetMapping({ "/test" })
	public se.pj.tbike.api.util.Response<Object> test() {
		return new SimpleResponse<>( new Configuration() )
				.setData( "Hello World" );
	}

	@Override
	public ResponseMapping getResponseMapping() {
		return responseMapping;
	}

	@Override
	public ValidatorsChain validateKey() {
		return ValidatorsChain.createChain()
				.addValidator( new LongValidator() )
				.addValidator( new InvalidValidator<Long>() {
					@Override
					protected boolean isValid(Long value) {
						return value > 0;
					}
				} );
	}

	@Override
	public boolean isExists(Long key) {
		return service.existsByKey( key );
	}
}
