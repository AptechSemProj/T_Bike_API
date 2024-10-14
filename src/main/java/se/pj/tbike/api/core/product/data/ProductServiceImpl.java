package se.pj.tbike.api.core.product.data;

import java.util.List;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.service.impl.CrudServiceImpl;
import se.pj.tbike.util.result.impl.ResultPageImpl;
import se.pj.tbike.util.result.ResultPage;
import se.pj.tbike.util.cache.Storage;

public class ProductServiceImpl
		extends CrudServiceImpl<Product, Long>
		implements ProductService {


	public ProductServiceImpl( ProductRepository repository,
	                           Storage<Long, Product> storage ) {
		super( repository, IdentifiedEntity::getId,
				dummy( storage ) );
	}

	private static Storage<Long, Product> dummy(
			Storage<Long, Product> storage ) {
		List<Product> p = List.of(
				new Product(
//						1,
//						"SKU P01",
//						"Product 1",
//						"",
//						2_300_000
//						brands.get( 0 ),
//						categories.get( 0 )
				),
				new Product(
//						2,
//						"SKU P02",
//						"Product 2",
//						"",
//						2_300_000
//						brands.get( 1 ),
//						categories.get( 0 )
				),
				new Product(
//						3,
//						"SKU P03",
//						"Product 3",
//						"",
//						2_300_000
//						brands.get( 2 ),
//						categories.get( 2 )
				),
				new Product(
//						4,
//						"SKU P04",
//						"Product 4",
//						"",
//						2_300_000
//						brands.get( 3 ),
//						categories.get( 0 )
				),
				new Product(
//						5,
//						"SKU P05",
//						"Product 5",
//						"",
//						2_300_000
//						brands.get( 0 ),
//						categories.get( 1 )
				),
				new Product(
//						6,
//						"SKU P06",
//						"Product 6",
//						"",
//						2_300_000
//						brands.get( 1 ),
//						categories.get( 1 )
				),
				new Product(
//						7,
//						"SKU P07",
//						"Product 7",
//						"",
//						2_300_000
//						brands.get( 3 ),
//						categories.get( 2 )
				),
				new Product(
//						8,
//						"SKU P08",
//						"Product 8",
//						"",
//						2_300_000
//						brands.get( 1 ),
//						categories.get( 2 )
				),
				new Product(
//						9,
//						"SKU P09",
//						"Product 9",
//						"",
//						2_300_000
//						brands.get( 4 ),
//						categories.get( 1 )
				),
				new Product(
//						10,
//						"SKU P010",
//						"Product 10",
//						"",
//						2_300_000
//						brands.get( 3 ),
//						categories.get( 0 )
				),
				new Product(
//						11,
//						"SKU P011",
//						"Product 11",
//						"",
//						2_300_000
//						brands.get( 3 ),
//						categories.get( 1 )
				),
				new Product(
//						12,
//						"SKU P012",
//						"Product 12",
//						"",
//						2_300_000
//						brands.get( 1 ),
//						categories.get( 2 )
				),
				new Product(
//						13,
//						"SKU P013",
//						"Product 13",
//						"",
//						2_300_000
//						brands.get( 0 ),
//						categories.get( 0 )
				)
		);
//		p.forEach( storage::cache );
		return storage;
	}

	@Override
	public ResultPage<Product> findPage( int num, int size ) {
		int from = num * size, to = from + size;
		return new ResultPageImpl<>( storage.getRange( from, to ), num,
				storage.size(), size );
	}
}
