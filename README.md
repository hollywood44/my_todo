# Share my todo!

## 프로젝트 기획 의도
> 할일(TODO)과 완료 날짜를 등록하면, 서로 팔로우한 친구들과 할일의 달성률을 공유 할 수 있는 서비스 입니다.
<br/>지속적인 업데이트를 통해 기능의 추가 및 리팩토링을 할 계획입니다!

## V3 설명
> V3에서는 기존 프로젝트를 프론트 서버와 백엔드 서버로 나누고 로그인 방식을 Jwt토큰 로그인으로 변경했습니다.
> 그리고 기존 중복되는 코드들을 리팩토링 하고 성능 향상을 위한 작업을 예상중에 있습니다.

## 프로젝트 아키텍쳐
![archit_img](src/main/resources/static/assets/TODO_Architecture.png)
## 주요기능
### VERSION 1,2

- **SpringBatch + Schduler**를 이용한 대용량을 일괄적으로 처리하는 **자동화 시스템** 구축
- **JPA(ORM)** 를 사용한 CRUD
- **querydsl**을 사용한 **동적인,복잡한 쿼리문 작성** 및 컴파일 시점에 오류 발견
- **EC2 + RDS** 를 통한 **AWS환경의 클라우드 서버 구축**
- **Route53 + RoadBalancer**를 이용한 **도메인 연결**
- **Route53 + CertificateManager**를 이용한 인증서 발급(**HTTPS**사용)
- **WebSocket + STOMP**를 이용한 **실시간 채팅**
### VERSION 3
- 기존의 **Form로그인**을 **Jwt토큰**을 이용한 로그인으로 변경
- 기존의 SpringBoot에서 처리하던 화면 렌더링 영역을 → **S3+CloudFront에** **VUE**를 배포해서 변경
- 기존의 **SringBoot서버를 REST API서버로 리팩토링**
- **Mock객체를 사용**해서 **테스트 편의성 개선**

## 

## API DOCS(추가중)
https://documenter.getpostman.com/view/24029041/2s93JzMLy6

## Front GitHub
https://github.com/hollywood44/TODO-FRONT

## 상세한 프로젝트 설명은
### [V1 상세보기](https://jonguks.notion.site/Share-my-todo-db8dfcba1d934e1b8bb60323c095086b)에서 볼 수 있습니다.
### [V2 상세보기](https://jonguks.notion.site/Share-my-todo-V2-816de2295d90421f807fc8da5c03822c)에서 볼 수 있습니다.
### [V3 상세보기](https://jonguks.notion.site/Share-my-todo-V3-1639078ab6fa491285107cc6bb38d55e) - V2를 REST API로 리팩토링 한 버전입니다.(진행중)

