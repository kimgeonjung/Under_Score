package edu.du.pproject11.service;

import edu.du.pproject11.dto.ItemDetail;
import edu.du.pproject11.dto.ItemList;
import edu.du.pproject11.entity.Item;
import edu.du.pproject11.mapper.ItemMapper;
import edu.du.pproject11.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;

    // 모든 카테고리 목록을 가져오는 메소드
    public List<String> findAllCategories() {
        return itemMapper.findAllCategories();
    }

    public List<ItemList> findItemsBySale() {
        return itemMapper.findItemsBySale();
    }

    public List<ItemList> findItemsByArrivals(){
        return itemMapper.findItemsByArrivals();
    }

    public List<ItemList> findItemsBySaleAndCategory(String category){
        return itemMapper.findItemsBySaleAndCategory(category);
    }

    public List<ItemList> findItemsByArrivalsAndCategory(String category){
        return itemMapper.findItemsByArrivalsAndCategory(category);
    }

    public List<ItemList> getAllItems() {
        return itemMapper.getAllItems();
    }

    public List<ItemList> findItemImageByCategory(String category) {
        return itemMapper.findItemImageByCategory(category);
    }

    public ItemDetail findItemById(Long id){
        return itemMapper.findItemById(id);
    }

    public List<String> findItemImageById(Long id) {
        return itemMapper.findItemImageById(id);
    }

    public Item getCartItemById(Long id) {
        return itemMapper.getCartItemById(id);
    }

}
