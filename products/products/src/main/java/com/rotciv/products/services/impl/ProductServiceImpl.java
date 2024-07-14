package com.rotciv.products.services.impl;

import com.rotciv.products.dto.CreateProductDto;
import com.rotciv.products.dto.ProductVariantDto;
import com.rotciv.products.dto.ResponseProductDto;
import com.rotciv.products.dto.UpdateProductDto;
import com.rotciv.products.entities.Category;
import com.rotciv.products.entities.Product;
import com.rotciv.products.entities.ProductVariant;
import com.rotciv.products.entities.Variant;
import com.rotciv.products.mapper.ProductMapper;
import com.rotciv.products.mapper.ProductVariantMapper;
import com.rotciv.products.mapper.VariantMapper;
import com.rotciv.products.repository.ProductRepository;
import com.rotciv.products.repository.ProductVariantRepository;
import com.rotciv.products.repository.VariantRepository;
import com.rotciv.products.services.ProductService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@DynamicUpdate
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private VariantRepository variantRepository;
    private ProductVariantRepository productVariantRepository;


    @Override
    public Product createProduct(CreateProductDto createProductDto) {
        Product createProduct = ProductMapper.createDtoMapToEntity(createProductDto, new Product());
        Product product = productRepository.findByName(createProduct.getName());
        if (product != null) {
            throw new RuntimeException("Product already exists");
        }
        createProduct.setCreatedAt(LocalDateTime.now());
        createProduct.setUpdatedAt(LocalDateTime.now());
        Product p = productRepository.save(createProduct);
        List<ProductVariantDto> productVariantDto = createProductDto.getProductVariants();
        for (ProductVariantDto pVariantDto : productVariantDto) {
            Variant variant = variantRepository.findById(pVariantDto.getVariantId()).orElseThrow(() -> new RuntimeException("Variant not found"));
            ProductVariant productVariant = ProductVariantMapper.toEntity(pVariantDto, new ProductVariant(), p, variant);
            productVariant.setCreatedAt(LocalDateTime.now());
            productVariant.setUpdatedAt(LocalDateTime.now());
            productVariantRepository.save(productVariant);
        }
        return p;
    }

    @Override
    public List<ResponseProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ResponseProductDto> responseProductDtos = new ArrayList<>();
        for (Product product : products) {
            ResponseProductDto responseProductDto = ProductMapper.mapToDto(product);
            responseProductDtos.add(responseProductDto);
        }
        return responseProductDtos;
    }

    @Override
    public void createVariant(ProductVariantDto productVariantDto) {
        Variant variant = VariantMapper.toEntity(productVariantDto, new Variant());
        variant.setCreatedAt(LocalDateTime.now());
        variant.setUpdatedAt(LocalDateTime.now());
        Variant existVariant = variantRepository.findByVariantValueAndVariantKey(variant.getVariantValue(), variant.getVariantKey());
        if (existVariant != null) {
            throw new RuntimeException("Variant already exists");
        }
        variantRepository.save(variant);
    }

    @Override
    public List<Variant> getAllVariants() {
        List<Variant> variants = variantRepository.findAll();
        if (variants.isEmpty()) {
            throw new RuntimeException("Variants not found");
        }
        return variants;
    }

    @Override
    public boolean updateProduct(UpdateProductDto updateProductDto) {
        Product existingProduct = productRepository.findById(updateProductDto.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = new Category();
        category.setId(updateProductDto.getCategoryId());

        existingProduct.setName(updateProductDto.getName());
        existingProduct.setDescription(updateProductDto.getDesc());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        productRepository.save(existingProduct);

        List<ProductVariant> productVariants = existingProduct.getProductVariants();
//        Delete all product-variants that not exists
        for (ProductVariant productVariant : productVariants) {
            boolean isExist = false;
            for (ProductVariantDto productVariantDto : updateProductDto.getProductVariants()) {
                if (existingProduct.getId().equals(productVariant.getProduct().getId()) && productVariantDto.getVariantId().equals(productVariant.getVariant().getId())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                productVariantRepository.delete(productVariant);
            }
        }
        List<ProductVariantDto> productVariantDtos = updateProductDto.getProductVariants();
        for (ProductVariantDto productVariantDto : productVariantDtos) {
            ProductVariant productVariant = productVariantRepository.findByProductIdAndVariantId(existingProduct.getId(), productVariantDto.getVariantId());
            if (productVariant == null) {
                productVariant = new ProductVariant();
                productVariant.setCreatedAt(LocalDateTime.now());
            }
            Variant variant = variantRepository.findById(productVariantDto.getVariantId()).orElseThrow(() -> new RuntimeException("Variant not found"));
            productVariant.setVariant(variant);
            productVariant.setProduct(existingProduct);
            productVariant.setPrice(productVariantDto.getPrice());
            productVariant.setUpdatedAt(LocalDateTime.now());
            productVariantRepository.save(productVariant);
        }
        return true;
    }

    @Override
    public Product getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.getProductVariants().sort((ProductVariant pv1, ProductVariant pv2) -> pv2.getPrice() - pv1.getPrice() > 0 ? -1 : 1);
        return product;
    }


}
