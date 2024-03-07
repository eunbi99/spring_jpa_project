package com.europehang.europe.user.dto;

import com.europehang.europe.common.util.SchemaDescriptionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    @Schema(description = SchemaDescriptionUtils.User.email, example = "test@gmail.com")
    private String email;

    @Schema(description = SchemaDescriptionUtils.User.password)
    private String password;
}
