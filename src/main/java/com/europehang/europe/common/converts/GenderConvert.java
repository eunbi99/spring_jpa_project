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
        // 엔티티의 데이터를 데이터베이스 컬럼에 저장할 데이터로 변환
        if(gender == null ) {
            return null;
        }
        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        // 데이터베이스에서 조회한 컬럼 데이터를 엔티티의 데이터로 변환
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
