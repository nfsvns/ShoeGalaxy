package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.AddressDAO;
import com.poly.entity.Address;
import com.poly.entity.Category;
@Controller
public class AddressController {
	@Autowired
	AddressDAO addDAO;
    @RequestMapping("/addressAdmin")
    public String index(Model model,Address add){
        Address item = new Address();
        
        model.addAttribute("item", item);
        model.addAttribute("addressItems", addDAO.findAll());
        return "addressAdmin";
    }
}
