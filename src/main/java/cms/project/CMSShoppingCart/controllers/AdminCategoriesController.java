package cms.project.CMSShoppingCart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cms.project.CMSShoppingCart.models.CategoryRepository;
import cms.project.CMSShoppingCart.models.entity.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model) {

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("categories", categories);

        return "admin/categories/index";
    }
}
