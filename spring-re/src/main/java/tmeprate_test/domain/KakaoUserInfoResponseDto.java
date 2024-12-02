package tmeprate_test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("has_signed_up")
    private Boolean hasSignedUp;

    @JsonProperty("connected_at")
    private Instant connectedAt;

    @JsonProperty("synched_at")
    private Instant synchedAt;

    @JsonProperty("properties")
    private HashMap<String, String> properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @JsonProperty("for_partner")
    private Partner partner;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {

        @JsonProperty("profile_needs_agreement")
        private Boolean isProfileAgree;

        @JsonProperty("profile_nickname_needs_agreement")
        private Boolean isNickNameAgree;

        @JsonProperty("profile_image_needs_agreement")
        private Boolean isProfileImageAgree;

        @JsonProperty("profile")
        private Profile profile;

        @JsonProperty("name_needs_agreement")
        private Boolean isNameAgree;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email_needs_agreement")
        private Boolean isEmailAgree;

        @JsonProperty("is_email_valid")
        private Boolean isEmailValid;

        @JsonProperty("is_email_verified")
        private Boolean isEmailVerified;

        @JsonProperty("email")
        private String email;

        @JsonProperty("age_range_needs_agreement")
        private Boolean isAgeAgree;

        @JsonProperty("age_range")
        private String ageRange;

        @JsonProperty("birthyear_needs_agreement")
        private Boolean isBirthYearAgree;

        @JsonProperty("birthyear")
        private String birthYear;

        @JsonProperty("birthday_needs_agreement")
        private Boolean isBirthDayAgree;

        @JsonProperty("birthday")
        private String birthDay;

        @JsonProperty("birthday_type")
        private String birthDayType;

        @JsonProperty("gender_needs_agreement")
        private Boolean isGenderAgree;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("phone_number_needs_agreement")
        private Boolean isPhoneNumberAgree;

        @JsonProperty("phone_number")
        private String phoneNumber;

        @JsonProperty("ci_needs_agreement")
        private Boolean isCIAgree;

        @JsonProperty("ci")
        private String ci;

        @JsonProperty("ci_authenticated_at")
        private Instant ciCreatedAt;
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {

        @JsonProperty("nickname")
        private String nickName;

        @JsonProperty("thumbnail_image_url")
        private String thumbnailImageUrl;

        @JsonProperty("profile_image_url")
        private String profileImageUrl;

        @JsonProperty("is_default_image")
        private Boolean isDefaultImage;

        @JsonProperty("is_default_nickname")
        private Boolean isDefaultNickName;
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Partner {

        @JsonProperty("uuid")
        private String uuid;
    }
}