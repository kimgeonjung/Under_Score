package edu.du.pproject11.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemList {
    private Long id;
    private String name;
    private int price;
    private String imagePath;
}
