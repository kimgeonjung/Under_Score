package edu.du.pproject11.service;

import edu.du.pproject11.entity.Cart;
import edu.du.pproject11.entity.CartItem;
import edu.du.pproject11.entity.Item;
import edu.du.pproject11.entity.Member;
import edu.du.pproject11.repository.CartItemRepository;
import edu.du.pproject11.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니에 상품 추가
    public void addCart(Member member, Item item, int quantity, String size) {
        Cart cart = cartRepository.findByMemberId(member.getId());
        Optional<CartItem> existingCartItem = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(item.getId()) && cartItem.getSize().equals(size))
                .findFirst();

        if (existingCartItem.isPresent()) {
            existingCartItem.get().addQuantity(quantity);
            cartItemRepository.save(existingCartItem.get());
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, quantity, size);
            cartItemRepository.save(cartItem);
        }
    }

    // 장바구니에서 상품 삭제
    public void removeCart(Member member, Item deletedItem) {
        Cart cart = cartRepository.findByMemberId(member.getId());
        cart.getCartItems()
                .stream()
                .filter(item -> item.getItem().getId().equals(deletedItem.getId()))
                .findFirst().ifPresent(cartItemRepository::delete);
    }

    public List<String> getSize(List<CartItem> cartItems){
        return cartItems.stream()
                .map(CartItem::getSize)
                .collect(Collectors.toList());
    }

    public List<Integer> getQuantity(List<CartItem> cartItems){
        return cartItems.stream()
                .map(CartItem::getQuantity)
                .collect(Collectors.toList());
    }
}
