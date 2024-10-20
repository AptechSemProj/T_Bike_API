package se.pj.tbike.api.core.product.mapper;

import org.modelmapper.ModelMapper;
import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.data.BrandRepository;
import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.product.category.data.CategoryRepository;
import se.pj.tbike.api.core.product.dto.ProductCreation;
import se.pj.tbike.api.io.RequestMapper;

import java.util.Optional;

public class ProductCreationMapper
		implements RequestMapper<Product, ProductCreation> {

	private final ModelMapper specificationsMapper;
	private final BrandRepository brandRepository;
	private final CategoryRepository categoryRepository;

	public ProductCreationMapper( BrandRepository brandRepository,
	                              CategoryRepository categoryRepository ) {
		this.brandRepository = brandRepository;
		this.categoryRepository = categoryRepository;
		specificationsMapper = new ModelMapper();
	}

	@Override
	public Product map( ProductCreation req ) {
		Optional<Brand> brand = brandRepository.findById( req.getBrandId() );
		if ( brand.isEmpty() )
			throw new RuntimeException();


		Product product = specificationsMapper.map(
				req.getSpecifications(),
				Product.class );

//		product.set


		return null;
	}
}
