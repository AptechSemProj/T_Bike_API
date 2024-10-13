package com.sem.proj.tbike.api.core.product;

import com.sem.proj.tbike.api.core.brand.dto.BrandDTO;
import com.sem.proj.tbike.api.core.product.dto.CategoryDTO;
import com.sem.proj.tbike.api.core.product.dto.ProductBasicInfo;
import com.sem.proj.tbike.api.core.product.dto.ProductFullInfo;
import com.sem.proj.tbike.api.util.Response.Pagination;
import com.sem.proj.tbike.api.util.Response;
import com.sem.proj.tbike.api.util.Response.Message;
import com.sem.proj.tbike.api.util.Response.Pagination.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/products" )
public class ProductController {

	private final ProductRepository repository;

	@GetMapping( path = { "", "/" } )
	public Response<Collection<ProductBasicInfo>> getList(
			@RequestParam( name = "page", defaultValue = "0" ) String page,
			@RequestParam( name = "size", defaultValue = "10" ) String size ) {
		int p = Integer.parseInt( page ), s = Integer.parseInt( size );
		if ( p < 0 || s < 0 )
			throw new RuntimeException();

//		Page<Product> paged = repository.findAll( PageRequest.of( p, s ) );

		List<BrandDTO> brands = List.of(
				new BrandDTO( 1, "Brand 1", "Intro 1" ),
				new BrandDTO( 2, "Brand 2", "Intro 2" ),
				new BrandDTO( 3, "Brand 3", "Intro 3" ),
				new BrandDTO( 4, "Brand 4", "Intro 4" ),
				new BrandDTO( 5, "Brand 5", "Intro 5" )
		);

		List<CategoryDTO> categories = List.of(
				new CategoryDTO( 1, "Xe dap tre em", "" ),
				new CategoryDTO( 2, "Xe dap dia hinh", "" ),
				new CategoryDTO( 3, "Xe dap dua", "" )
		);

		List<ProductBasicInfo> products = List.of(
				new ProductBasicInfo(
						1,
						"SKU P01",
						"Product 1",
						"",
						2_300_000,
						brands.get( 0 ),
						categories.get( 0 )
				),
				new ProductBasicInfo(
						2,
						"SKU P02",
						"Product 2",
						"",
						2_300_000,
						brands.get( 1 ),
						categories.get( 0 )
				),
				new ProductBasicInfo(
						3,
						"SKU P03",
						"Product 3",
						"",
						2_300_000,
						brands.get( 2 ),
						categories.get( 2 )
				),
				new ProductBasicInfo(
						4,
						"SKU P04",
						"Product 4",
						"",
						2_300_000,
						brands.get( 3 ),
						categories.get( 0 )
				),
				new ProductBasicInfo(
						5,
						"SKU P05",
						"Product 5",
						"",
						2_300_000,
						brands.get( 0 ),
						categories.get( 1 )
				),
				new ProductBasicInfo(
						6,
						"SKU P06",
						"Product 6",
						"",
						2_300_000,
						brands.get( 1 ),
						categories.get( 1 )
				),
				new ProductBasicInfo(
						7,
						"SKU P07",
						"Product 7",
						"",
						2_300_000,
						brands.get( 3 ),
						categories.get( 2 )
				),
				new ProductBasicInfo(
						8,
						"SKU P08",
						"Product 8",
						"",
						2_300_000,
						brands.get( 1 ),
						categories.get( 2 )
				),
				new ProductBasicInfo(
						9,
						"SKU P09",
						"Product 9",
						"",
						2_300_000,
						brands.get( 4 ),
						categories.get( 1 )
				),
				new ProductBasicInfo(
						10,
						"SKU P010",
						"Product 10",
						"",
						2_300_000,
						brands.get( 3 ),
						categories.get( 0 )
				),
				new ProductBasicInfo(
						11,
						"SKU P011",
						"Product 11",
						"",
						2_300_000,
						brands.get( 3 ),
						categories.get( 1 )
				),
				new ProductBasicInfo(
						12,
						"SKU P012",
						"Product 12",
						"",
						2_300_000,
						brands.get( 1 ),
						categories.get( 2 )
				),
				new ProductBasicInfo(
						13,
						"SKU P013",
						"Product 13",
						"",
						2_300_000,
						brands.get( 0 ),
						categories.get( 0 )
				)
		);

		int from = p * s, to = from + s;

		Supplier<List<ProductBasicInfo>> supplier =
				() -> products.subList( from,
						Math.min( to, products.size() ) );

		List<ProductBasicInfo> result = from >= products.size()
				? List.of()
				: supplier.get();

		return new Pagination<>( Message.OK,
				result,
				new Metadata( products.size(), p, s )
		);
	}

	@GetMapping( path = "/{id}" )
	public Response<ProductFullInfo> getDetail( @PathVariable( name = "id" ) String id ) {
		long key = Long.parseLong( id );

		Optional<ProductFullInfo> product = Optional.of( new ProductFullInfo() );

		return product.map( Response::ok )
				.orElseGet( Response::notFound );
	}
}
