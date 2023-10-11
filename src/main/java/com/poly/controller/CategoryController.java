package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.entity.Category;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryDAO cateDAO;
    @RequestMapping("/categoryAdmin")
    public String index(Model model,Category cate){
        Category item = new Category();
        
        model.addAttribute("item", item);
        model.addAttribute("categoryItems", cateDAO.findAll());
        return "categoryAdmin";
    }

    @RequestMapping("/categoryAdmin/create")
    public String create(Category item){
        cateDAO.save(item);
        return "redirect:/categoryAdmin";
    }

    @RequestMapping("/categoryAdmin/update")
    public String update(@ModelAttribute("item")  Category item){
        cateDAO.save(item);
        return "redirect:/categoryAdmin/edit/" + item.getId();
    }

    @RequestMapping("/categoryAdmin/delete/{id}")
    public String delete(@PathVariable("id") String id){
        cateDAO.deleteById(id);
        return "redirect:/categoryAdmin";
    }

    @RequestMapping("/categoryAdmin/edit/{id}")
    public String edit(Model model, @PathVariable("id") String id) {
        Category item = cateDAO.findById(id).get();
        model.addAttribute("item", item);
        List<Category> items = cateDAO.findAll();
        model.addAttribute("categoryItems", items);
        return "categoryAdmin";
    }
}