package com.europehang.europe.common.converts;

import com.europehang.europe.common.enums.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.extern.slf4j.Slf4j;

@Convert
@Slf4j
public class GenderConvert implements AttributeConverter<Gender, String> {


    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if(gender == null ) {
            return null;
        }
        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
       try {
           return Gender.fromCode(dbData);
       } catch (IllegalArgumentException e) {
           log.error("코드 변환 실패 [{}]", dbData,e);
           throw e;
       }
    }
}
