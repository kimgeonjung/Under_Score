package edu.du.pproject11.controller;

import edu.du.pproject11.entity.Cart;
import edu.du.pproject11.entity.CartItem;
import edu.du.pproject11.entity.Item;
import edu.du.pproject11.entity.Member;
import edu.du.pproject11.repository.CartRepository;
import edu.du.pproject11.service.CartService;
import edu.du.pproject11.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;
    private final CartRepository cartRepository;

    // cart 화면 출력
    @GetMapping("/cart")
    public String cartPage(HttpServletRequest request, Model model) {

        Member member = (Member) request.getAttribute("loginUser");
        Cart cart = cartRepository.findByMemberId(member.getId());
        List<CartItem> cartItems = cart.getCartItems();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("itemAmount", cartItems.size());
        model.addAttribute("sizes", cartService.getSize(cartItems));
        model.addAttribute("quantities", cartService.getQuantity(cartItems));
        model.addAttribute("totalPrice", cartItems.stream()
                .mapToInt(ci -> ci.getItem().getPrice()).sum());
        log.info("totalPrice: {}", model.getAttribute("totalPrice"));
        return "main/cart";
    }

    // 물품 추가하기
    @PostMapping("/cart/{itemId}")
    public String AddCart(@PathVariable Long itemId,
                          @RequestParam int quantity,
                          @RequestParam String size,
                          HttpServletRequest request) {

        Member member = (Member) request.getAttribute("loginUser");
        Item item = itemService.getCartItemById(itemId);
        cartService.addCart(member, item, quantity, size);
        return "redirect:/cart";
    }

    // 물품 삭제하기
    @GetMapping("/cart/delete/{itemId}")
    public String deleteItemAtCart(@PathVariable Long itemId,
                                   HttpServletRequest request) {

        Member member = (Member) request.getAttribute("loginUser");
        Item item = itemService.getCartItemById(itemId);
        cartService.removeCart(member, item);
        return "redirect:/cart";
    }
}
