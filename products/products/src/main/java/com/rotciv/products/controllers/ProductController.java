package com.rotciv.products.controllers;

import com.rotciv.products.dto.*;
import com.rotciv.products.entities.Product;
import com.rotciv.products.entities.Variant;
import com.rotciv.products.services.GalleryService;
import com.rotciv.products.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/api/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private GalleryService galleryService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createProduct(@RequestBody() CreateProductDto createProductDto) {
        try{
            Product createdProduct = productService.createProduct(createProductDto);
            galleryService.saveGallery(createdProduct.getId(), createProductDto.getGallery());
            return ResponseEntity.ok(new ResponseDto("201","Product created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto("400", e.getMessage() + createProductDto));
        }
    }

    @PostMapping("/get-all-products")
    public List<ResponseProductDto> getAllProducts(@RequestParam String page, @RequestParam String size, @RequestParam(required = false) String q, @RequestBody() FilterProductDto filterProductDto) {
        q = q == null ? "" : q;
        String categoryId = filterProductDto.getCategoryId() == null ? "" : filterProductDto.getCategoryId();
        return productService.getAllProducts(Integer.parseInt(page), Integer.parseInt(size), q, categoryId);
    }

    @PostMapping("/create-variant")
    public ResponseEntity<ResponseDto> createVariant(@RequestBody() ProductVariantDto productVariantDto) {
        try{
            productService.createVariant(productVariantDto);
            return ResponseEntity.ok(new ResponseDto("201","Variant created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto("400", e.getMessage()));
        }
    }

    @GetMapping("/get-all-variants")
    public List<Variant> getAllVariants() {
        return productService.getAllVariants();
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateProduct(@RequestBody() UpdateProductDto updateProductDto) {
        try{
            boolean updated = productService.updateProduct(updateProductDto);
            if(updated){
                return ResponseEntity.ok(new ResponseDto("200","Product updated successfully"));
            } else {
                return ResponseEntity.badRequest().body(new ResponseDto("400","Product not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto("400", e.getMessage()));
        }
    }

    @GetMapping("/update-status-product/{id}")
    public ResponseEntity<ResponseDto> stopProduct(@PathVariable String id, @RequestParam Boolean isStop) {
        try{
            boolean stopped = productService.updateStatus(id, isStop);
            if(stopped){
                return ResponseEntity.ok(new ResponseDto("200","Product stopped successfully"));
            } else {
                return ResponseEntity.badRequest().body(new ResponseDto("400","Product not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto("400", e.getMessage()));
        }
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
