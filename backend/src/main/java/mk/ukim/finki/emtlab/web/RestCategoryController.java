package mk.ukim.finki.emtlab.web;


import mk.ukim.finki.emtlab.model.enumeration.Category;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/categories")
public class RestCategoryController {
    @GetMapping()
    public List<Category> listCategories(){
        return List.of(Category.values());
    }
}
