package se.pj.tbike.core.api.product.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.pj.tbike.core.api.brand.conf.BrandMapper;
import se.pj.tbike.core.api.product.dto.ProductRequest;
import se.pj.tbike.core.api.product.dto.ProductDetail;
import se.pj.tbike.core.api.product.dto.ProductResponse;
import se.pj.tbike.core.api.product.dto.mapper.ProductRequestMapper;
import se.pj.tbike.core.api.product.dto.mapper.ProductDetailMapper;
import se.pj.tbike.core.api.product.dto.mapper.ProductResponseMapper;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.attribute.conf.AttributeMapper;
import se.pj.tbike.core.api.category.conf.CategoryMapper;

@Service
@RequiredArgsConstructor
public class ProductMapper {

	private final BrandMapper brandMapper;
	private final CategoryMapper categoryMapper;
	private final AttributeMapper attributeMapper;

	private static volatile ProductRequestMapper creationMapper;
	private static volatile ProductResponseMapper responseMapper;

	public Product map(ProductRequest req) {
		var mapper = creationMapper();
		return mapper.map( req );
	}

	public ProductResponse map(Product product) {
		var mapper = responseMapper();
		return mapper.map( product );
	}

	public ProductDetail toDetail(Product product) {
		var mapper = detailMapper();
		return mapper.map( product );
	}

	public ProductRequestMapper creationMapper() {
		if ( creationMapper == null ) {
			synchronized ( this ) {
				if ( creationMapper == null ) {
					creationMapper = new ProductRequestMapper(
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
