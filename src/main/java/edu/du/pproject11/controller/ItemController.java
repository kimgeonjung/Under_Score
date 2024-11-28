package edu.du.pproject11.controller;

import edu.du.pproject11.dto.ItemDetail;
import edu.du.pproject11.dto.ItemList;
import edu.du.pproject11.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/shop")
    public String shopPage(Model model) {
        List<ItemList> items = itemService.getAllItems();
        System.out.println(items);
        List<String> categories = itemService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", "All");
        model.addAttribute("items", items);
        model.addAttribute("pageType", "shop");
        return "main/page";
    }

    // 카테고리별로 필터링된 아이템을 반환
    @GetMapping("/shop/items")
    public String filterItemsByCategory(@RequestParam String category, Model model) {
        List<ItemList> items;
        if ("All".equals(category)) {
            items = itemService.getAllItems();
            model.addAttribute("items", items);
        } else {
            System.out.println(category);
            items = itemService.findItemImageByCategory(category);
            System.out.println(items);
            model.addAttribute("items", items);
        }
        List<String> categories = itemService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("pageType", "shop");
        return "main/page";
    }

    @GetMapping("/sale")
    public String salePage(Model model) {
        List<ItemList> items = itemService.findItemsBySale();
        List<String> categories = itemService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", "All");
        model.addAttribute("items", items);
        model.addAttribute("pageType", "sale");
        return "main/page";
    }

    @GetMapping("/sale/items")
    public String saleFilterItemsByCategory(@RequestParam String category, Model model) {
        List<ItemList> items;
        if ("All".equals(category)) {
            items = itemService.findItemsBySale();
        }else {
            items = itemService.findItemsBySaleAndCategory(category);
        }
        List<String> categories = itemService.findAllCategories();
        model.addAttribute("items", items);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categories);
        model.addAttribute("pageType", "sale");
        return "main/page";
    }

    @GetMapping("/newArrivals")
    public String arrivalsPage(Model model) {
        List<ItemList> items = itemService.findItemsByArrivals();
        List<String> categories = itemService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", "All");
        model.addAttribute("items", items);
        model.addAttribute("pageType", "newArrivals");
        return "main/page";
    }

    @GetMapping("/newArrivals/items")
    public String newArrivalsFilterItemsByCategory(@RequestParam String category, Model model) {
        List<ItemList> items;
        if ("All".equals(category)) {
            items = itemService.findItemsByArrivals();
        }else {
            items = itemService.findItemsByArrivalsAndCategory(category);
        }
        List<String> categories = itemService.findAllCategories();
        model.addAttribute("items", items);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categories);
        model.addAttribute("pageType", "newArrivals");
        return "main/page";
    }

    @GetMapping("/shop/item")
    public String shopItemPage(@RequestParam Long id, Model model) {
        ItemDetail item = itemService.findItemById(id);
        List<String> images = itemService.findItemImageById(id);
        model.addAttribute("item", item);
        model.addAttribute("images", images);
        return "main/item";
    }
}