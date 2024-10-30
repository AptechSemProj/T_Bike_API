package se.pj.tbike.core.api.product.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.pj.tbike.core.api.brand.conf.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandRepository;
import se.pj.tbike.core.api.product.data.ProductRepository;
import se.pj.tbike.core.api.product.dto.ProductCreation;
import se.pj.tbike.core.api.product.dto.ProductDetail;
import se.pj.tbike.core.api.product.dto.ProductResponse;
import se.pj.tbike.core.api.product.dto.mapper.ProductCreationMapper;
import se.pj.tbike.core.api.product.dto.mapper.ProductDetailMapper;
import se.pj.tbike.core.api.product.dto.mapper.ProductResponseMapper;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.attribute.conf.AttributeMapper;
import se.pj.tbike.core.api.category.conf.CategoryMapper;
import se.pj.tbike.core.api.category.data.CategoryRepository;

@Service
@RequiredArgsConstructor
public class ProductMapper {

	private final ProductRepository repository;
	private final BrandRepository brandRepository;
	private final CategoryRepository categoryRepository;
	private final BrandMapper brandMapper;
	private final CategoryMapper categoryMapper;
	private final AttributeMapper attributeMapper;

	private static volatile ProductCreationMapper creationMapper;
	private static volatile ProductResponseMapper responseMapper;

	public Product fromCreation(ProductCreation req) {
		var mapper = creationMapper();
		return mapper.map( req );
	}

	public ProductResponse toResponse(Product product) {
		var mapper = responseMapper();
		return mapper.map( product );
	}

	public ProductDetail toDetail(Product product) {
		var mapper = detailMapper();
		return mapper.map( product );
	}

	public ProductCreationMapper creationMapper() {
		if ( creationMapper == null ) {
			synchronized ( this ) {
				if ( creationMapper == null ) {
					creationMapper = new ProductCreationMapper(
					);
				}
			}
		}
		return creationMapper;
	}

	public ProductResponseMapper responseMapper() {
		if ( responseMapper == null ) {
			synchronized ( this ) {
				if ( responseMapper == null ) {
					responseMapper = new ProductResponseMapper(
							brandMapper.responseMapper(),
							categoryMapper.responseMapper()
					);
				}
			}
		}
		return responseMapper;
	}

	public ProductDetailMapper detailMapper() {
		return new ProductDetailMapper(
				brandMapper.responseMapper(),
				categoryMapper.responseMapper(),
				attributeMapper.responseMapper()
		);
	}
}
