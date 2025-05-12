### 팀 msa 도입으로 공부용 프로젝트 ###

msa-board-server
- 게이트웨이에서 jwt 인증에 성공한 client에 한해 게시글 CRUD, 댓글 CRUD 가능
- Redis 캐시 사용 -> 게시글 최초 조회시 10분간 캐싱. 10분내 조회시 레디스에 글 호출하도록 설정. 글의 수정 및 삭제발생 -> 레디스에서 삭제 @CachePut 발동.



msa
cloudGatewayStudy : board, auth, member 서버의 모든 접근을 통제하는 게이트웨이. https://github.com/JinAru16/cloudGatewayStudy
config-server : board, auth, member 서버의 모든 application.yml을 가지고 있는 서버
msaCommon : board, auth, member 서버의 공통 SpringSecurity 담당. https://github.com/JinAru16/msaCommon
msaAuth : 모든 엔드포인트의 인가를 담당. oAuth2 로그인, 구글로그인 담당
msaMember : 엔드포인트 내에서 회원 정보를 조회용 서버.
