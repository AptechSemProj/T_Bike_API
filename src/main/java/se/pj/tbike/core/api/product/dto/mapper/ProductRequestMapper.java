package se.pj.tbike.core.api.product.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import se.pj.tbike.core.api.product.dto.ProductSpecifications;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.product.dto.ProductRequest;
import se.pj.tbike.io.RequestMapper;

public class ProductRequestMapper
        implements RequestMapper<Product, ProductRequest> {

    private final TypeMap<ProductSpecifications, Product> specificationsMapper;

    public ProductRequestMapper() {
        this.specificationsMapper = new ModelMapper()
                .typeMap( ProductSpecifications.class, Product.class );
    }

    @Override
    public Product map(ProductRequest req) {
        Product product = new Product();
        specificationsMapper.map( req.getSpecifications(), product );
        product.setSku( req.getSku() );
        product.setName( req.getName() );
        return product;
    }
}
