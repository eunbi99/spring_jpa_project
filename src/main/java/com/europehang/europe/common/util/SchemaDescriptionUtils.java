package com.europehang.europe.common.util;
public class SchemaDescriptionUtils {

    public static class User {
        public static final String email = "이메일";
        public static final String username = "이름";
        public static final String password = "비밀번호";
        public static final String nickname = "닉네임";
        public static final String gender = "성별(FEMALE|MALE)";
    }

    public static class Post {
        public static final String title = "제목";

        public static final String content = "내용";
        public static final String kakao_url="오픈카톡 URL";
        public static final String parentCategoryId="상위카테고리 ID";
        public static final String childCategoryId = "하위카테고리 ID";
        public static final String recruitmentLimit = "모집인원";

        public static final String travelStartDate = "여행일자";

        public static final String recruitStatus = "모집완료 여부(RECRUITING|RECRUIT_COMPLETE)";
    }
}
