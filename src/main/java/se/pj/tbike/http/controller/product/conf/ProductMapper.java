package se.pj.tbike.http.controller.product.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.pj.tbike.http.controller.attribute.AttributeMapper;
import se.pj.tbike.http.model.brand.BrandMapper;
import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.http.controller.product.dto.ProductRequest;
import se.pj.tbike.http.controller.product.dto.ProductDetail;
import se.pj.tbike.http.controller.product.dto.ProductResponse;
import se.pj.tbike.http.controller.product.dto.mapper.ProductRequestMapper;
import se.pj.tbike.http.controller.product.dto.mapper.ProductDetailMapper;
import se.pj.tbike.http.controller.product.dto.mapper.ProductResponseMapper;
import se.pj.tbike.domain.entity.Product;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final AttributeMapper attributeMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;

    private static volatile ProductRequestMapper creationMapper;
    private static volatile ProductResponseMapper responseMapper;

    public Product map(ProductRequest req) {
        var mapper = creationMapper();
        return mapper.map(req);
    }

    public ProductResponse map(Product product) {
        var mapper = responseMapper();
        return mapper.map(product);
    }

    public ProductDetail toDetail(Product product) {
        var mapper = detailMapper();
        return mapper.map(product);
    }

    public ProductRequestMapper creationMapper() {
        if (creationMapper == null) {
            synchronized (this) {
                if (creationMapper == null) {
                    creationMapper = new ProductRequestMapper(
                    );
                }
            }
        }
        return creationMapper;
    }

    public ProductResponseMapper responseMapper() {
        if (responseMapper == null) {
            synchronized (this) {
                if (responseMapper == null) {
                    responseMapper = new ProductResponseMapper(
                            brandMapper,
                            categoryMapper
                    );
                }
            }
        }
        return responseMapper;
    }

    public ProductDetailMapper detailMapper() {
        return new ProductDetailMapper(
                brandMapper,
                categoryMapper,
                attributeMapper
        );
    }
}
