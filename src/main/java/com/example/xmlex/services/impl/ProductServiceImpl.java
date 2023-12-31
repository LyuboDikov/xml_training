package com.example.xmlex.services.impl;

import com.example.xmlex.models.dtos.ProductSeedDto;
import com.example.xmlex.models.dtos.ProductViewRootDto;
import com.example.xmlex.models.dtos.ProductWithSellerDto;
import com.example.xmlex.models.entities.Product;
import com.example.xmlex.repositories.ProductRepository;
import com.example.xmlex.services.CategoryService;
import com.example.xmlex.services.ProductService;
import com.example.xmlex.services.UserService;
import com.example.xmlex.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper,
                              ValidationUtil validationUtil, UserService userService,
                              CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public long getProductsCount() {
        return productRepository.count();
    }

    @Override
    public void seedProducts(List<ProductSeedDto> products) {
        products.stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.getRandomUser());

                    if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
                        product.setBuyer(userService.getRandomUser());
                    }
                    product.setCategories(categoryService.getRandomCategories());
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductViewRootDto findProductInRangeWithNoBuyer() {

        ProductViewRootDto productViewRootDto = new ProductViewRootDto();

        productViewRootDto.setProducts(
                productRepository.findAllByPriceBetweenAndBuyerIsNull(
                                BigDecimal.valueOf(500L),
                                BigDecimal.valueOf(1000L))
                        .stream()
                        .map(product -> {

                            ProductWithSellerDto productWithSellerDto =
                                    modelMapper.map(product, ProductWithSellerDto.class);

                            if (product.getSeller().getFirstName() != null) {
                                productWithSellerDto.setSeller(String.format("%s %s",
                                        product.getSeller().getFirstName(),
                                        product.getSeller().getLastName()));
                            } else {
                                productWithSellerDto.setSeller(product.getSeller().getLastName());
                            }

                            return productWithSellerDto;
                        })
                        .toList());

        return productViewRootDto;
    }
}
