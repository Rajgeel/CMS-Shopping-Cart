package cms.project.CMSShoppingCart.models;

import org.springframework.data.jpa.repository.JpaRepository;

import cms.project.CMSShoppingCart.models.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
