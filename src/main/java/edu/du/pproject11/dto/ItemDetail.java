package edu.du.pproject11.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetail {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int quantity;
}
