package se.pj.tbike.api.core.product.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static se.pj.tbike.api.common.Urls.API_PREFIX;
import static se.pj.tbike.api.common.Urls.PRODUCT_API;
import static se.pj.tbike.api.common.Urls.URL_INFO;
import static se.pj.tbike.api.common.Urls.URL_LIST_1;
import static se.pj.tbike.api.common.Urls.URL_LIST_2;

import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.product.data.ProductService;
import se.pj.tbike.api.core.product.dto.ProductResponse;
import se.pj.tbike.api.core.product.dto.ProductDetail;
import se.pj.tbike.api.io.Arr;
import se.pj.tbike.api.io.Response;
import se.pj.tbike.api.io.ResponseMapper;
import se.pj.tbike.api.util.Error;
import se.pj.tbike.api.util.NumberValidator;
import se.pj.tbike.api.util.Validated;
import se.pj.tbike.api.util.controller.PageableController;
import se.pj.tbike.api.util.controller.StdController;
import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping( path = { API_PREFIX + PRODUCT_API } )
public class ProductController
		implements PageableController,
		StdController<Long> {

	private final ProductService service;
	private final ResponseMapper<Product, ProductResponse> productRespMapper;
	private final ResponseMapper<Product, ProductDetail> detailMapper;


	@GetMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Arr<ProductResponse>> getList(
			@RequestParam( name = "page", defaultValue = "0" ) String page,
			@RequestParam( name = "size", defaultValue = "10" ) String size ) {
		return paginated(
				page, size,
				( p, s ) -> service.findPage( p, s )
						.map( productRespMapper::map )
		);
	}

	@GetMapping( path = URL_INFO )
	public Response<ProductDetail> getDetail(
			@PathVariable( name = "id" ) String id ) {
		return get( id, k -> {
			Result<Product> r = service.findByKey( k );
			return r.map( detailMapper::map );
		} );
	}

	@Override
	public Validated<Long> validateKey( String keyInPath ) {
		return NumberValidator.validateLong( keyInPath )
				.thenTest( l -> l >= 0, Error.INVALID );
	}
}
