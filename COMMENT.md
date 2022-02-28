#
####
#Mock을 사용하는 이유
    UserService를 테스트해야 하는데 UseService는 UserRepository와 의존성을 가지고 있기 때문에
    테스트를 할 때 전체를 모두 돌려버리면 시간이 오래걸리게 된다.
    따라서 빠른 단위테스트를 위해 Mock을 사용하여 가짜 객체를 만들고 테스트를 진행한다.
    서비스 레이어의 테스트는 단위테스트가 되어야한다.
    단위테스트 F.I.R.S.T 원칙
    Fast, Independent, Repeatable, Self Validating, Timely
    따라서 가짜로 의존객체를 생성해주는 Mock을 사용한다.
##given
    사용자 요청을 받은 뒤 컨트롤러와 서비스에서 처리하므로 UserRequest(dto)를
    생성해준다.
##when
    해당하는 메서드가 실행되었을 때 수행할 작업을 세팅한다.
    예를 들어 가짜 객체 @Mock으로 지정된 UserRepository를 사용한다고 할 때
    when(userRepository.findById(1L))
    .thenReturn(Optional.empty());
    라는 코드를 작성한다면 이후 userService 메서드를 실행하면서
    when에 해당한다면 해당되는 값을 리턴해준다.
    혹은 given(T).willReturn(T)로도 사용가능하다.
    when() -> mockito.Mockito
    given() -> mockito.BDDMockito
    BDD : Behavior-Driven Development의 약자로 행위주도 개발을 말하며
    여기서 권장하는 기본 패턴은 Given, When, Then이다.
    기존 mockito.Mockito를 사용하면 given의 위치에 when()이 사용되므로
    좀 더 이해가 쉽게 되어있는 BDDMockito를 사용하는 것이 편할 것 같다.
##verify, then
    또한 BDDMockito는 Mockito를 상속하여 구현한 것이기 때문에 메서드의 사용법을 제외하고는
    차이가 없다. 또한 Mockito의 verify()도 BDDMockito의 then().should()로 대체할 수 있다.

