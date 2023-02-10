package sns.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lombok.Cleanup;
import sns.util.DBManager;

public class MessageDAO {
	// �ű� �޽��� ���
	public boolean newMsg(Message msg) throws Exception {
		int flag = 0;
		String sql = "insert into s_message(uid, msg, date) values(?, ?, now())";
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, msg.getUid());
		preparedStatement.setString(2, msg.getMsg());
		flag = preparedStatement.executeUpdate();
		return flag == 1;
	}
	
	// �޽��� ����
	public boolean delMsg(int mid) throws Exception {
		int flag = 0;
		String sql = "delete from s_message where mid = ?";
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1, mid);
		flag = preparedStatement.executeUpdate();
		return flag == 1;
	}
	
	// ��� ���
	public boolean newReply(Reply reply) throws Exception {
		int flag = 0;
		String sql = "insert into s_reply(mid, uid, rmsg, date) values(?, ?, ?, now())";
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1, reply.getMid());
		preparedStatement.setString(2, reply.getUid());
		preparedStatement.setString(3, reply.getRmsg());
		flag = preparedStatement.executeUpdate();
		return flag == 1;
	}
	
	// ��� ����
	public boolean delReply(int rid) throws Exception {
		int flag =  0;
		String sql = "delete from s_reply where rid = ?";
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setInt(1, rid);
		flag = preparedStatement.executeUpdate();
		return flag == 1;
	}
	
	public ArrayList<MessageSet> getAll(int cnt, String suid) throws Exception {
		ArrayList<MessageSet> datas = new ArrayList<MessageSet>();
		String sql;
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = null;
		// ��ü �Խù��� ���
		if((suid == null) || (suid.equals(""))) {
			sql = "select * from s_message order by date desc limit 0, ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, cnt);
		}
		// Ư�� ȸ�� �Խù� only �� ���
		else {
			sql = "select * from s_message where uid=? order by date desc limit 0, ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, suid);
			preparedStatement.setInt(2, cnt);
		}
		
		@Cleanup ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			MessageSet messageSet = new MessageSet();
			Message message = new Message();
			ArrayList<Reply> replies = new ArrayList<>();
			
			message.setMid(resultSet.getInt("mid"));
			message.setMsg(resultSet.getString("msg"));
			message.setDate(resultSet.getDate("date")+ " / " + resultSet.getTime("date"));
			message.setFavcount(resultSet.getInt("favcount"));
			message.setUid(resultSet.getString("uid"));
			
			String sqlReply = "select * from s_reply where mid=? order by date desc";
			preparedStatement = connection.prepareStatement(sqlReply);
			preparedStatement.setInt(1, resultSet.getInt("mid"));
			@Cleanup ResultSet resultSetReply = preparedStatement.executeQuery();
			while(resultSetReply.next()) {
				Reply reply = new Reply();
				reply.setRid(resultSetReply.getInt("rid"));
				reply.setUid(resultSetReply.getString("uid"));
				reply.setRmsg(resultSetReply.getString("rmsg"));
				reply.setDate(resultSetReply.getDate("date")+ " / " + resultSetReply.getTime("date"));
				replies.add(reply);
			}
			resultSetReply.last();
			message.setReplycount(resultSetReply.getRow());
			
			messageSet.setMessage(message);
			messageSet.setRlist(replies);
			datas.add(messageSet);
		}
		return datas;
	}
	
	// ���ƿ� �߰�
	public void favorite(int mid) throws Exception  {
		// ���ƿ� �߰��� ���� favcount�� +1 �ؼ� update
		String sql = "update s_message set favcount=favcount+1 where mid=?";
		
		@Cleanup Connection connection = DBManager.INSTANCE.getConnection();
		@Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, mid);
		preparedStatement.executeUpdate();
	}
}
