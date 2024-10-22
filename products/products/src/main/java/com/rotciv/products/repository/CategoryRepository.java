package com.rotciv.products.repository;

import com.rotciv.products.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{
    Category findByName(String name);
}
