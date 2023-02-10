package sns.member;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDO {
	// 회원 이름
	private String name;
	// 회원 아이디
	private String uid;
	// 로그인 비밀번호
	private String passwd;
	// 이메일 주소
	private String email;
	// 가입일
	private Date date;
	
}
