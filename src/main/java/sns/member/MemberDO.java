package sns.member;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDO {
	// ȸ�� �̸�
	private String name;
	// ȸ�� ���̵�
	private String uid;
	// �α��� ��й�ȣ
	private String passwd;
	// �̸��� �ּ�
	private String email;
	// ������
	private Date date;
	
}
