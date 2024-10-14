package se.pj.tbike.api.core.product.controller;

import java.util.Collection;
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
import se.pj.tbike.api.io.Pagination;
import se.pj.tbike.api.io.Response;
import se.pj.tbike.api.io.ResponseMapper;
import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultPage;

@RequiredArgsConstructor
@RestController
@RequestMapping( path = { API_PREFIX + PRODUCT_API } )
public class ProductController {

	private final ProductService productService;
	private final ResponseMapper<Product, ProductResponse> productRespMapper;

	@GetMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Arr<ProductResponse>> getList(
			@RequestParam( name = "page", defaultValue = "0" ) String page,
			@RequestParam( name = "size", defaultValue = "10" ) String size ) {
		int p = Integer.parseInt( page ), s = Integer.parseInt( size );
		if ( p < 0 || s < 0 )
			throw new RuntimeException();
		ResultPage<Product> r = productService.findPage( p, s );
		return new Pagination<>( r.map( productRespMapper::map ) );
	}

	@GetMapping( path = URL_INFO )
	public Response<ProductDetail> getDetail(
			@PathVariable( name = "id" )
			String id ) {
		long key = Long.parseLong( id );
		Result<Product> r = productService.findByKey( key );
		Optional<ProductDetail> p = Optional.empty();
//				r.map( productResponseMapper::map )
//				.toOptional();

		return p.map( Response::ok )
				.orElseGet( Response::notFound );
	}
}
