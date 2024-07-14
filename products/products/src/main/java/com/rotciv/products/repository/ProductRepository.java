package com.rotciv.products.repository;

import com.rotciv.products.entities.Category;
import com.rotciv.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    Product findByName(String name);
}
