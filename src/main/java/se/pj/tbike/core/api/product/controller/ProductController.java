package se.pj.tbike.core.api.product.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.api.product.conf.ProductApiUrls;
import se.pj.tbike.core.api.product.conf.ProductMapper;
import se.pj.tbike.core.api.product.dto.ProductListRequest;
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
import se.pj.tbike.util.Output.Pagination;
import se.pj.tbike.util.Output.Value;
import se.pj.tbike.validation.Requirements;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.core.util.SimpleController;
import se.pj.tbike.validation.validator.LongValidator;

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

	@PostMapping({ "/list", "/search" })
	public Response<Arr<ProductResponse>>
	getList(@RequestBody ProductListRequest req) {
		int page = req.getPage( 0 );
		int size = req.getSize( 10 );
		return paginated( page, size, (p, s) -> {
			Pagination<Product> o = service.search(
					p, s, req.getName(),
					req.getMaxPrice(), req.getMinPrice(),
					req.getBrandId(), req.getCategoryId()
			);
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
//            brandService.existsByKey( r.getBrandId() );

			Product p = productMapper.map( r );
			Brand b = new Brand();
			b.setId( r.getBrandId() );
			p.setBrand( b );
			Category c = new Category();
			c.setId( r.getCategoryId() );
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
			if ( old.isNull() ) {
				throw new RuntimeException( "Product not found" );
			}
			Product oldProduct = old.get();
			Product newProduct = productMapper.map( req );
			newProduct.setId( oldProduct.getId() );
			String sku = oldProduct.getSku();
			String nSku = newProduct.getSku();
			check_sku: {
				if (sku == null) {
					break check_sku;
				}
				if ( nSku != null ) {
					// return 409
				}
			}

			service.update( newProduct );
			return null;
		} );
	}

	@Override
	public ResponseMapping getResponseMapping() {
		return responseMapping;
	}

	@Override
	public ValidatorsChain validateKey() {
		LongValidator validator = new LongValidator();
		validator.accept( Requirements.positiveLong( false, false ) );
		return ValidatorsChain.createChain()
				.addValidator( validator );
	}

	@Override
	public boolean isExists(Long key) {
		return service.existsByKey( key );
	}
}
