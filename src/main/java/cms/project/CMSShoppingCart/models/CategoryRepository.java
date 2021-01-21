package cms.project.CMSShoppingCart.models;

import org.springframework.data.jpa.repository.JpaRepository;

import cms.project.CMSShoppingCart.models.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
