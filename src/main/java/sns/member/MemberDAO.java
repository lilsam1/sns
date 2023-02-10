package sns.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import sns.util.DBManager;

@Log4j2
public class MemberDAO {
	// �ű� ȸ�� ���
	public boolean addMember(MemberDO member) throws Exception {
		int flag = 0;
		String sql = "insert into s_member(name, uid, passwd, email, date) values(?, ?, ?, ?, now())";
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, member.getName());
		preparedStatement.setString(2, member.getUid());
		preparedStatement.setString(3, member.getPasswd());
		preparedStatement.setString(4, member.getEmail());
		flag = preparedStatement.executeUpdate();
		return flag == 1;
	}
	
	// ȸ�� �α���
	public boolean login(String uid, String passwd) throws Exception {
		String sql = "select uid, passwd from s_member where uid = ?";
		boolean result = false;
		
		log.info(uid,passwd);
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, uid);
		@Cleanup ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();
		if(resultSet.getString("passwd").equals(passwd)) {
			result = true;
		}
		return result;
	}
	
	// ����ȭ�� ���� �ű�ȸ�� ���
	public ArrayList<String> getNewMembers() throws Exception {
		// ȸ�� ����� 7�� ������ ������
		String sql = "select * from s_member order by date desc limit 0, 7";
		ArrayList<String> members = new ArrayList<>();
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);		
		@Cleanup ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			members.add(resultSet.getString("uid"));
		}
		return members;
	}
}

