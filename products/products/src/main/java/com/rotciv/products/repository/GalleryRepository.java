package com.rotciv.products.repository;

import com.rotciv.products.entities.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, String> {

}
