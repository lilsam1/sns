package sns.member;

import lombok.Data;

@Data
public class Message {
	// �Խñ� ������ id
	private int mid;
	// �Խñ� �ۼ���
	private String uid;
	// �Խñ� ����
	private String msg;
	// ���ƿ� Ƚ��
	private int favcount;
	// ��� ����
	private int replycount;
	// �Խñ� �ۼ��� �ð�
	private String date;
}
