package edu.du.pproject11.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id")
    private Item item;

    private int quantity; // 주문 개수
    private String size;  // 주문 사이즈

    public static CartItem createCartItem(Cart cart, Item item, int quantity, String size) {
        return CartItem.builder()
                .cart(cart)
                .item(item)
                .quantity(quantity)
                .size(size)
                .build();
    }

    @Override
    public String toString() {
        return "CartItem [CartItemId=" + id +
                ",\n CartId=" + cart.getId() +
                ",\n MemberId=" + cart.getMember().getId() +
                ", MemberName=" + cart.getMember().getName() +
                ",\n ItemId=" + item.getId() +
                ", ItemName=" + item.getName() +
                ", ItemSeason=" + item.getSeason() +
                ", ItemPrice=" + item.getPrice() +
                ", ItemQuantity=" + item.getQuantity() +
                ", ItemSize=" + item.getSize() +
                ", Quantity=" + getQuantity() +
                ", Size=" + getSize() +
                "]";
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
