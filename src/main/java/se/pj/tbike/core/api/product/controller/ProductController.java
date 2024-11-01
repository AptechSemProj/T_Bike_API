package se.pj.tbike.core.api.product.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.api.product.conf.ProductApiUrls;
import se.pj.tbike.core.api.product.conf.ProductMapper;
import se.pj.tbike.core.api.product.dto.ProductRequest;
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
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Arr;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.Val;
import se.pj.tbike.util.Output;
import se.pj.tbike.util.Output.Value;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.core.util.SimpleController;
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

	@GetMapping({ Urls.URL_LIST_1, Urls.URL_LIST_2 })
	public Response<Arr<ProductResponse>>
	getList(@RequestParam(defaultValue = "0") String page,
	        @RequestParam(defaultValue = "10") String size) {
		return paginated( page, size, (p, s) -> {
			Output.Pagination<Product> o = service.findPage( p, s );
			return o.map( productMapper::map );
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
	create(@RequestBody @Valid ProductRequest req) {
		return post( req, (r) -> {
			brandService.existsByKey( r.getBrandId() );

//			Output.Value<Brand> brand = brandService
//					.findByKey( r.getBrandId() );
//			if ( brand.isNull() ) {
//				return responseMapping.getResponse(
//						NotExistError.builder().build()
//				);
//			}
//			Output.Value<Category> category = categoryService
//					.findByKey( r.getCategoryId() );
//			if ( category.isNull() ) {
//				return responseMapping.getResponse(
//						NotExistError.builder().build()
//				);
//			}

			Product p = productMapper.map( r );

			Brand b = new Brand();
			b.setId( r.getBrandId() );

			Category c = new Category();
			c.setId( r.getCategoryId() );

			p.setBrand( b );
			p.setCategory( c );

			List<Attribute> attrs = new ArrayList<>();
			p.setAttributes( attrs );
			Product created = service.create( p );

			r.getColors().forEach( attr -> {
				Attribute attribute = attributeMapper.map( attr );
				attribute.setProduct( created );
				attrs.add( attribute );
			} );

			attrs.forEach( attributeService::create );

			return created.getId();
		} );
	}

	@PutMapping({ Urls.URL_INFO })
	public void update(@PathVariable String id,
	                   @RequestBody @Valid ProductRequest req) {
		put( id, null, k -> {
			Value<Product> old = service.findByKey( k );
			Product newProduct = productMapper.map( req );
			return null;
		} );
	}

	@Override
	public ResponseMapping getResponseMapping() {
		return responseMapping;
	}

	@Override
	public ValidatorsChain validateKey() {
		return ValidatorsChain.createChain()
				.addValidator( new LongValidator().acceptOnlyPositive() );
	}

	@Override
	public boolean isExists(Long key) {
		return service.existsByKey( key );
	}
}
