package se.pj.tbike.core.api.product.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import se.pj.tbike.core.api.product.dto.ProductSpecifications;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.product.dto.ProductCreation;
import se.pj.tbike.io.RequestMapper;

public class ProductCreationMapper
		implements RequestMapper<Product, ProductCreation> {

	private final TypeMap<ProductSpecifications, Product> specificationsMapper;

	public ProductCreationMapper() {
		this.specificationsMapper = new ModelMapper()
				.typeMap( ProductSpecifications.class, Product.class );
	}

	@Override
	public Product map(ProductCreation req) {
		Product product = new Product();
		specificationsMapper.map( req.getSpecifications(), product );

		product.setSku( req.getSku() );
		product.setName( req.getName() );
//
//		List<Attribute> attrs = new ArrayList<>();
//		req.getColors().forEach( attr -> {
//			boolean represent = attr.getRepresent() != null
//					? attr.getRepresent()
//					: false;
//			Attribute attribute = new Attribute();
//			attribute.setProduct( product );
//			attribute.setColor( attr.getName() );
//			attribute.setImageUrl( attr.getImageUrl() );
//			attribute.setPrice( attr.getPrice() );
//			attribute.setQuantity( attr.getQuantity() );
//			attribute.setRepresent( represent );
//			attrs.add( attribute );
//		} );
//
//		product.setAttributes( attrs );

		return product;
	}
}
