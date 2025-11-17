# 커밋 컨벤션

Git 커밋 메시지 작성 규칙 가이드

---

## 기본 구조

커밋 메시지 형식:

```bash
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

**예시**:

```bash
feat(auth): 로그인 토큰 만료 처리 추가
```

---

## type (커밋 유형, 필수)

| Type       | 설명                                     |
|------------|----------------------------------------|
| `feat`     | 새로운 기능 추가                              |
| `fix`      | 버그 수정                                  |
| `docs`     | 문서 수정                                  |
| `style`    | 포맷팅, 세미콜론 누락, 공백 등 코드 변경 없는 스타일 변경     |
| `refactor` | 리팩토링 (기능 변경 없음)                        |
| `perf`     | 성능 향상                                  |
| `test`     | 테스트 추가 또는 수정                           |
| `build`    | 빌드 시스템 또는 외부 의존성 관련 작업                 |
| `ci`       | CI 설정 관련 변경                            |
| `chore`    | 기타 변경 (예: 빌드 스크립트 수정, 라이브러리 버전 업데이트 등) |
| `revert`   | 이전 커밋 되돌림                              |

---

## scope (적용 범위, 선택 사항)

해당 변경이 영향을 미치는 **기능/모듈/디렉터리 명시**

**예시**:

- `feat(auth):`
- `fix(api):`

팀에서 사용하는 주요 스코프 예시를 정의할 수 있음 (예: `login`, `user-profile`, `db`, `config` 등)

---

## description (설명, 필수)

- 현재형으로 간결하게 작성 (예: "사용자 인증 추가", "에러 메시지 수정")
- 마침표(`.`) 생략

---

## body (본문, 선택 사항)

- **무엇을**, **왜** 변경했는지 상세 설명
- 여러 줄 작성 가능
- 관련된 이슈, 기술적 배경 등 포함 가능

---

## footer (푸터, 선택 사항)

**주요 용도**:

- `BREAKING CHANGE:` (하위 호환 불가 변경)
- `Closes #123`, `Fixes #456` 등 이슈 연동

**예시**:

```bash
BREAKING CHANGE: 로그인 API 인증 방식 변경됨
Closes #99
```

---

## BREAKING CHANGE 처리 방식

- 변경이 하위 호환 불가일 경우 반드시 `BREAKING CHANGE:` 추가
- `type!:` 형태로 커밋 제목에도 명시 가능 (예: `feat!:` ...)

---

## 커밋 메시지 예시

### 예시 1: 기본 형식

```bash
feat(account): 회원가입 시 이메일 인증 추가
```

### 예시 2: 본문 포함

```bash
fix(session): 세션 만료 오류 해결

- 기존 세션 유효성 검사에서 토큰 시간 계산이 잘못되어 만료가 즉시 처리되는 문제가 있었음
```

### 예시 3: BREAKING CHANGE 포함

```bash
refactor!: 로그인 로직 분리

- BREAKING CHANGE: 로그인 요청 방식이 GET에서 POST로 변경됨.
```

---

## 참고 자료

- [Conventional Commits](https://www.conventionalcommits.org/)
- [Angular Commit Guidelines](https://github.com/angular/angular/blob/main/CONTRIBUTING.md#commit)
