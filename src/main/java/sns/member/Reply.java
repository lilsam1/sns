package sns.member;

import lombok.Data;

@Data
public class Reply {
	// ������ id
	private int mid;
	// ��� ������ id
	private int rid;
	// ��� �ۼ���
	private String uid;
	// ��� ����
	private String rmsg;
	// ��� �ۼ�����
	private String date;
}
