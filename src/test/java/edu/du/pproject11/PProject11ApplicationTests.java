package edu.du.pproject11;

import edu.du.pproject11.config.session.SessionConst;
import edu.du.pproject11.dto.LoginForm;
import edu.du.pproject11.entity.*;
import edu.du.pproject11.repository.CartRepository;
import edu.du.pproject11.repository.ItemImageRepository;
import edu.du.pproject11.repository.ItemRepository;
import edu.du.pproject11.repository.MemberRepository;
import edu.du.pproject11.service.ItemService;
import edu.du.pproject11.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;


@SpringBootTest
class PProject11ApplicationTests {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemImageRepository itemImageRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    CartRepository CartRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    void 상품만들기_사이즈추가() {
        String[] categories = {"Tops", "Outerwear", "Knits", "Shirts", "Tees", "Pants", "Denim", "Short"};
        String sizeList = "S,M,L,XL";  // 예시 사이즈 (콤마로 구분된 문자열)

        IntStream.range(1, 9).forEach(i -> {
            IntStream.range(1, 6).forEach(j -> {
                // Item 생성
                Item item = Item.builder()
                        .name(categories[i - 1] + " Sale " + j)
                        .season("Sale")  // 시즌
                        .category(categories[i - 1])
                        .description(categories[i - 1] + " Sale " + j + "은 이번 Sale 상품입니다.")
                        .price(12000 + j * 10)
                        .quantity(10)
                        .size(sizeList)  // 사이즈를 콤마로 구분된 문자열로 저장
                        .build();

                // Item을 저장하여 ID가 생성되게 함
                itemRepository.save(item);

                // 140.jpg부터 150.jpg까지 이미지를 ItemImage로 저장
                for (int k = 140; k <= 150; k++) {
                    ItemImage itemImage = ItemImage.builder()
                            .imagePath(k + ".jpg")
                            .item(item) // Item과 연결
                            .build();
                    itemImageRepository.save(itemImage); // ItemImage 저장
                }
            });
        });
    }

    @Test
    void 세일품목_출력(){
        itemService.findItemsBySale().forEach(System.out::println);
    }

    @Test
    void 신상품목_출력(){
        itemService.findItemsBySale().forEach(System.out::println);
    }

//    @Test
//    void 로그인테스트(){
//        Optional<Member> loginUser = memberRepository.findByLoginId("qwer");
//        System.out.println(loginUser.get());
//        Member member1 = memberRepository.findByLoginIdAndPassword("qwer", "1234");
//        Member member2 = memberRepository.findByLoginIdAndPassword("qwer", "5555");
//        if (member1 != null) {
//            System.out.println(member1);
//        }
//        if (member2 != null) {
//            System.out.println(member2);
//        }else {
//            System.out.println("null이지롱");
//        }
//    }

    @Test
    @Transactional
    void 카트테스트(){
        Cart cart = cartRepository.findByMemberId(1L);
        System.out.println(cart);
        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem : cartItems) {
            System.out.println(cartItem);
        }
    }

    @Test
    void chPass(){
        Member member = memberService.findById(2L);
        member.setPassword("1234");
        member.setEmail("kim123@asdf.com");
        System.out.println(member);
    }
}
