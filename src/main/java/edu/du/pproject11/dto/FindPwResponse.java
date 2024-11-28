package edu.du.pproject11.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindPwResponse {
    private String receiveAddress;
    private String mailTitle;
    private String mailContent;
}
