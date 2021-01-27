package cms.project.CMSShoppingCart.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cms.project.CMSShoppingCart.models.CategoryRepository;
import cms.project.CMSShoppingCart.models.ProductRepository;
import cms.project.CMSShoppingCart.models.entity.Category;
import cms.project.CMSShoppingCart.models.entity.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository CategoryRepository;

    @GetMapping
    public String index(Model model) {

        List<Product> products = productRepository.findAll();
        List<Category> categories = CategoryRepository.findAll();

        HashMap<Integer, String> cats = new HashMap<>();
        for(Category cat : categories) {
            cats.put(cat.getId(), cat.getName());
        }

        model.addAttribute("products", products);
        model.addAttribute("cats", cats);

        return "admin/products/index";
    }

    @GetMapping("/add")
    public String add(Product product, Model model) {

        List<Category> categories = CategoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "admin/products/add";
    }

    @PostMapping("/add")
    public String add(@Valid Product product, BindingResult bindingResult,MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws IOException {

        List<Category> categories = CategoryRepository.findAll();

        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if(filename.endsWith("jpg") || filename.endsWith("png")) {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExists = productRepository.findBySlug(slug);

        if(! fileOK) {

            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png exists");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        }

        else if(productExists != null) {

            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);


        }
        else {
            product.setSlug(slug);
            product.setImage(filename);


            productRepository.save(product);
            Files.write(path, bytes);

        }


        return "redirect:/admin/products/add";
    }
    

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        List<Category> categories = CategoryRepository.findAll();
        Product product = productRepository.getOne(id);

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/products/edit";

    }

}
