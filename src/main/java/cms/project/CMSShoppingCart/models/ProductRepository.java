package cms.project.CMSShoppingCart.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cms.project.CMSShoppingCart.models.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Product findBySlug(String slug);

	Product findBySlugAndIdNot(String slug, int id);

	Page<Product> finadAll(Pageable pagable);
    
}
