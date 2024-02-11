package com.europehang.europe.common.converts;

import com.europehang.europe.common.enums.RecruitStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.extern.slf4j.Slf4j;

@Convert
@Slf4j
public class RecruitStatusConvert implements AttributeConverter<RecruitStatus, String> {


    @Override
    public String convertToDatabaseColumn(RecruitStatus recruitStatus) {
        if(recruitStatus == null ) {
            return null;
        }
        return recruitStatus.getStatus();
    }

    @Override
    public RecruitStatus convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
       try {
           return RecruitStatus.fromCode(dbData);
       } catch (IllegalArgumentException e) {
           log.error("코드 변환 실패 [{}]", dbData,e);
           throw e;
       }
    }
}
