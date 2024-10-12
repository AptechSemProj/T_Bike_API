package com.sem.proj.tbike.api.core.product;

import com.sem.proj.tbike.api.util.PaginationResponse;
import com.sem.proj.tbike.api.util.Response;
import com.sem.proj.tbike.api.util.Response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/products" )
public class ProductController {

	private final ProductRepository repository;

	@GetMapping( path = { "", "/" } )
	public Response<Collection<Product>> getList(
			@RequestParam( name = "page", defaultValue = "0" ) String page,
			@RequestParam( name = "size", defaultValue = "10" ) String size ) {
		int p = Integer.parseInt( page ), s = Integer.parseInt( size );
		if ( p < 0 || s < 0 )
			throw new RuntimeException();

		Page<Product> paged = repository.findAll( PageRequest.of( p, s ) );

		return new PaginationResponse<>( 200, Message.OK, paged.toList() );
	}

	@GetMapping( path = "/{id}" )
	public Response<Product> getDetail( @PathVariable( name = "id" ) String id ) {
		long key = Long.parseLong( id );

		Optional<Product> product = repository.findById( key );

		return product.map( Response::ok )
				.orElseGet( Response::notFound );
	}
}
