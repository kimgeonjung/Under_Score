package edu.du.pproject11.mapper;

import edu.du.pproject11.dto.ItemDetail;
import edu.du.pproject11.dto.ItemList;
import edu.du.pproject11.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<ItemList> getAllItems();
    List<String> findAllCategories();
    List<ItemList> findItemsBySale();
    List<ItemList> findItemsByArrivals();
    List<ItemList> findItemsByArrivalsAndCategory(String category);
    List<ItemList> findItemsBySaleAndCategory(String category);
    List<ItemList> findItemImageByCategory(String category);
    ItemDetail findItemById(Long id);
    List<String> findItemImageById(Long id);
    Item getCartItemById(Long id);
}
