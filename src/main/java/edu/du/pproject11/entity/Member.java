package edu.du.pproject11.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;    // 가능
    @NotEmpty
    private String loginId; // 불가능
    @NotEmpty
    private String phone;   // 가능
    @NotEmpty
    private String email;   // 불가능
    @NotEmpty
    private String password;// 불가능
    @NotEmpty
    private String address; // 가능 // 도로명 주소
    private String postcode;      // 우편번호
    private String detailAdr;      // 상세 주소

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
