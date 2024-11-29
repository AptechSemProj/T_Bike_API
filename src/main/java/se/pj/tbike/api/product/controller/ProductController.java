package se.pj.tbike.api.product.controller;

import com.ank.japi.QueryParamsReader;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import com.ank.japi.impl.StdRequestHandler;
import com.ank.japi.validation.CollectionRequirement;
import com.ank.japi.validation.Requirements;
import com.ank.japi.validation.Validators;
import com.ank.japi.validation.error.NotExistError;
import com.ank.japi.validation.error.UnexpectedTypeError;
import com.ank.japi.validation.error.UnknownError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.api.attribute.dto.AttributeMapper;
import se.pj.tbike.api.attribute.dto.AttributeRequest;
import se.pj.tbike.api.brand.data.BrandService;
import se.pj.tbike.api.brand.entity.Brand;
import se.pj.tbike.api.product.conf.ProductApiUrls;
import se.pj.tbike.api.product.conf.ProductMapper;
import se.pj.tbike.api.product.dto.ProductListRequest;
import se.pj.tbike.api.product.dto.ProductRequest;
import se.pj.tbike.api.product.entity.Product;
import se.pj.tbike.api.product.data.ProductService;
import se.pj.tbike.api.product.dto.ProductResponse;
import se.pj.tbike.api.product.dto.ProductDetail;
import se.pj.tbike.api.attribute.data.AttributeService;
import se.pj.tbike.api.category.data.CategoryService;
import se.pj.tbike.api.category.entity.Category;
import se.pj.tbike.impl.ResponseConfigurerImpl;
import se.pj.tbike.util.PageableController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping({ProductApiUrls.PRODUCT_API})
public class ProductController
        implements PageableController<ProductResponse> {

    private final BrandService brandService;
    private final ProductService service;
    private final CategoryService categoryService;
    private final AttributeService attributeService;
    private final ProductMapper mapper;
    private final AttributeMapper attributeMapper;

    @Override
    public int defaultPageSize() {
        return 10;
    }

    @PostMapping({"/list", "/search"})
    public com.ank.japi.Response<List<ProductResponse>>
    getList(@RequestBody ProductListRequest req) {
        return paginated(req, (page, size) -> {
            Page<Product> o = service.search(
                    PageRequest.of(page, size),
                    req.getName(), req.getBrandId(), req.getCategoryId(),
                    req.getPriceRange()
            );
            return o.map(mapper::map);
        });
    }

    @GetMapping({"/{id}"})
    public Response<ProductDetail>
    getDetail(@PathVariable String id) {
        return new StdRequestHandler<ProductRequest, ProductDetail>(
                ResponseConfigurerImpl::new
        ).handle(null, (res, req) -> {
            long pid;
            try {
                pid = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException e) {
                return res.badRequest();
            }
            Optional<Product> r = service.findByKey(pid);
            if (r.isEmpty()) {
                return res.notFound();
            }
            return res.ok(mapper.toDetail(r.get()));
        });
    }

    private void validateRequest(ProductRequest body, Validators validators) {
        validators.bind("name", body.getName()).require(
                Requirements.notBlank(255)
        );
        validators.bind("brandId", body.getBrandId()).require(
                Requirements.positiveLong(false, false)
        );
        validators
                .bind("categoryId", body.getCategoryId())
                .require(
                        Requirements.positiveLong(false, false)
                );
        validators.bind("colors", body.getColors()).require(
                CollectionRequirement.elements().allMatch(o -> {
                    if (o instanceof AttributeRequest a) {
                        String s = a.getName();
                        return s != null
                                && !s.isBlank()
                                && s.length() <= 255;
                    }
                    throw UnexpectedTypeError.builder().build();
                }).allMatch(o -> {
                    if (o instanceof AttributeRequest a) {
                        String s = a.getImageUrl();
                        return s != null && !s.isBlank();
                    }
                    throw UnexpectedTypeError.builder().build();
                }).allMatch(o -> {
                    if (o instanceof AttributeRequest a) {
                        long p = a.getPrice();
                        return p >= 0;
                    }
                    throw UnexpectedTypeError.builder().build();
                }).allMatch(o -> {
                    if (o instanceof AttributeRequest a) {
                        int q = a.getQuantity();
                        return q >= 0;
                    }
                    throw UnexpectedTypeError.builder().build();
                })
        );
        validators.bind("specifications", body.getSpecifications()).require(
                Requirements.notNull()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping({"", "/"})
    public com.ank.japi.Response<Long> create(@RequestBody ProductRequest req) {
        return new StdRequestHandler<ProductRequest, Long>(
                ResponseConfigurerImpl::new
        ).handle(req, (res, body, validators) -> {
            Optional<Brand> b =
                    brandService.findByKey(body.getBrandId());
            Optional<Category> c = categoryService
                    .findByKey(body.getCategoryId());
            if (b.isEmpty()) {
                String s =
                        "Brand with id " + body.getBrandId() + " not found";
                throw NotExistError.builder().reason(s).build();
            }
            if (c.isEmpty()) {
                String s = "Category with id " + body.getBrandId() +
                        " not found";
                throw NotExistError.builder().reason(s).build();
            }
            Product p = mapper.map(body);
            p.setBrand(b.get());
            p.setCategory(c.get());
            Product created = service.create(p);
            body.getColors()
                    .parallelStream()
                    .map(attributeMapper::map)
                    .forEach(a -> {
                        a.setProduct(created);
                        attributeService.create(a);
                    });
            return res.created(created.getId());
        }, (body, validators) -> {
            validators.bind("colors", body.getColors()).require(
                    CollectionRequirement.elements().allMatch(o -> {
                        if (o instanceof AttributeRequest a) {
                            return a.getId() == null;
                        }
                        throw UnexpectedTypeError.builder().build();
                    }).count(1, o -> {
                        if (o instanceof AttributeRequest a) {
                            return a.getRepresent();
                        }
                        throw UnexpectedTypeError.builder().build();
                    })
            );
            this.validateRequest(body, validators);
        });
    }

    private Product findProduct(QueryParamsReader params) {
        Long productId = params.getLong("id");
        if (productId == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST.value(),
                    "Cannot parse id to long."
            );
        }
        Optional<Product> old = service.findByKey(productId);
        return old.orElseThrow(() -> new HttpException(
                HttpStatus.NOT_FOUND.value(),
                "Product with id '" + productId + "' not found"
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    public com.ank.japi.Response<Long>
    update(@PathVariable String id, @RequestBody ProductRequest req) {
        return new StdRequestHandler<ProductRequest, Long>(
                ResponseConfigurerImpl::new
        ).setQueryParams(writer -> writer.set(
                "id", id,
                Requirements.positiveLong(false, false)
        )).handle(req, (res, body, params) -> {
            Product oldProduct = findProduct(params);
            Product newProduct = mapper.map(req);
            newProduct.setId(oldProduct.getId());
            String sku = oldProduct.getSku();
            String nSku = newProduct.getSku();
            check_sku:
            {
                if (sku == null || nSku == null) {
                    break check_sku;
                }
                // sku != null && nSku != null
                if (sku.equals(nSku)) {
                    break check_sku;
                }
                String s = "Product's sku already exist. Cannot change sku.";
                return res.conflict(s);
            }
            if (body.getBrandId() != oldProduct.getBrand().getId()) {
                String s = "Cannot change product brand.";
                return res.badRequest(s);
            }
            newProduct.setBrand(oldProduct.getBrand());
            if (body.getCategoryId() != oldProduct.getCategory().getId()) {
                String s = "Cannot change product category.";
                return res.badRequest(s);
            }
            newProduct.setCategory(oldProduct.getCategory());
            if (service.update(newProduct)) {
                body.getColors()
                        .parallelStream()
                        .map(attributeMapper::map)
                        .forEach(a -> {
                            a.setProduct(newProduct);
                            if (a.getId() != null) {
                                attributeService.update(a);
                            } else {
                                attributeService.create(a);
                            }
                        });
                return res.noContent();
            }
            return res.internalServerError("Error while performing update.");
        }, this::validateRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    public com.ank.japi.Response<Object> delete(@PathVariable String id) {
        return new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        ).setQueryParams(writer -> writer.set(
                "id", id,
                Requirements.positiveLong(false, false)
        )).handle(null, (res, body, params) -> {
            Long productId = params.getLong("id");
            if (productId == null) {
                String s = "Cannot parse " + id + " to long.";
                return res.internalServerError(s);
            }
            Optional<Product> old = service.findByKey(productId);
            if (old.isEmpty()) {
                String s = "Product with id '" + productId + "' not found";
                return res.notFound(s);
            }
            Product p = old.get();
            p.getAttributes().forEach(a -> {
                if (!attributeService.delete(a)) {
                    throw UnknownError
                            .builder()
                            .reason("Error while performing delete.")
                            .build();
                }
            });
            if (service.delete(p)) {
                return res.noContent();
            }
            return res.internalServerError("Error while performing delete.");
        });
    }
}
