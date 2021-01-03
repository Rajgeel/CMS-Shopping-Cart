package cms.project.CMSShoppingCart.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cms.project.CMSShoppingCart.models.PageRepository;
import cms.project.CMSShoppingCart.models.entity.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    private PageRepository pageRepository;

    public AdminPagesController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping
    public String index(Model model) {

        List<Page> pages = pageRepository.findAll();

        model.addAttribute("pages", pages);

        return "admin/pages/index";
    }
    
}
