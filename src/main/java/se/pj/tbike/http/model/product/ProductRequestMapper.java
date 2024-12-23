package se.pj.tbike.http.model.product;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import se.pj.tbike.domain.entity.Product;

public class ProductRequestMapper {

    private final TypeMap<ProductSpecifications, Product> specificationsMapper;

    public ProductRequestMapper() {
        this.specificationsMapper = new ModelMapper()
                .typeMap( ProductSpecifications.class, Product.class );
    }

    public Product map(ProductRequest req) {
        Product product = new Product();
        specificationsMapper.map( req.getSpecifications(), product );
        product.setSku( req.getSku() );
        product.setName( req.getName() );
        return product;
    }
}
