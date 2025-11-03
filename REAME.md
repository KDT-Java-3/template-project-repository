# Demo Project
- 재직자 부트캠프 프로젝트 초기 설정

## MapStruct 사용 예시
```java
// TODO: UserEntity, UserDto 클래스 생성 이후 실습 예정

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // MapStruct가 생성할 구현체를 스프링 Bean으로 등록
public interface UserMapper {

    // 필드명이 다를 경우 @Mapping으로 직접 지정
    @Mapping(source = "email", target = "emailAddress")
    UserDto toDto(UserEntity entity);

    // 필드명이 같다면 별도 매핑 없이 자동으로 변환
    UserEntity toEntity(UserDto dto);
}
```
