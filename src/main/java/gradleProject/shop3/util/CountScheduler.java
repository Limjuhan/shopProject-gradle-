//package util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import service.ShopService;
//
//
//public class CountScheduler {
//	@Autowired
//	ShopService service;
//
//	private int cnt;
//
//	/*
//	 * @Scheduled(cron="0/5 * * * * ?")
//	 * @Scheduled(fixedRate = 5000) : 밀리초
//	 * @Scheduled(fixedDelay = 5000) : 밀리초
//	 */
//	@Scheduled(cron="0/5 * * * * ?")
//	public void execute1() {
//		System.err.println("cnt: " + cnt++);
//	}
//
//	@Scheduled(initialDelay=3000, fixedRate = 5000)
//	public void execute2() {
//		System.err.println("서버시작 3초후 첫번째 실행");
//		System.err.println("5초마다 실행");
//	}
//
//	/*
//	 * cron
//	 * 1. 특정시간, 주기적으로 프로그램을 실행.
//	 * 2. 리눅스에서 crontab 명령으로 설정 가능.
//	 *
//	 * 0/10 * * * * ? : 10초마다 한번씩
//	 * 0 /0/1 * * * ? : 1분마다 한번씩
//	 * 0 20, 50 * * * ? : 매시간 20, 50분 마다 실행
//	 * 0 0 0/3 * * ? : 3시간 마다 한번씩 실행
//	 * 0 0 12 ? * 1 : 월요일 12시에 실행
//	 * 0 0 12 ? * MON : 월요일 12시에 실행
//	 *
//	 * cron 작성 사이트 : www.cronmaker.com
//	 */
//
//
//	/*
//	 * 1. 평일 아침 10시 30분에 환율 정보를 조회하여 db에 등록
//	 * 2. exchange 테이블 생성
//	 * 		create table exchange (
//	 * 			eno int primary key auto_increment, -- 키값
//	 * 			code varchar(10),					-- 통화코드
//	 * 			name varchar(50),					-- 통화명
//	 * 			sellamt float,						-- 매도율
//	 * 			buymat float,						-- 매입율
//	 * 			priamt float,						-- 기준율
//	 * 			edate varchar(10)					-- 환율 기준일
//	 */
//	@Scheduled(cron="0 30 10 * * 1,2,3,4,5")
//	public void exchange() {
//		service.exchangeCreate();
//	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
