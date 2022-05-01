package com.todaymeal.todaymeal.web.dto.responseDto;


import com.todaymeal.todaymeal.domain.user.User;
import com.todaymeal.todaymeal.global.dto.DtoMetaData;
import lombok.Data;

@Data
public class UserFetchResponseDto extends BaseResponseDto{
    private String name;
    private String email;
    private String phoneNumber;
    private String nickName;
    private String picture;

    public UserFetchResponseDto(DtoMetaData dtoMetaData, User user) {
        this.dtoMetaData = dtoMetaData;
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.nickName = user.getNickName();
        this.picture = user.getPicture();
    }

    public UserFetchResponseDto(DtoMetaData dtoMetaData) {
        this.dtoMetaData = dtoMetaData;
        this.name = null;
        this.email = null;
        this.phoneNumber = null;
        this.nickName = null;
        this.picture = null;
    }
}
