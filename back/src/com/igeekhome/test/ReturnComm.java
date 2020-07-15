package com.igeekhome.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;


 

import com.jdbc.utils.JDBCUtil;
/*
* �����ݿⱣ���  ������������Ϣ��  ���ظ�ǰ��
 * ���յ��̵����ֲ��Ҹõ��̵��������۲�չʾ
 */
public class ReturnComm extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������Ӧ�����ʽ
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=UTF-8");
		
		int curPage=0;//��ǰҳ��
		int pageSize=15;//ÿҳ���ݸ���
		int a=0;
	
		Statement st=null;
		Statement st2=null;
		ResultSet rs=null;
		Connection conn=null;
		JSONArray ja=new JSONArray();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		//����Ҫ�������۵ĵ�������
		String shopname2=req.getParameter("shopname");
		System.out.println("�鿴���۵ĵ�������"+shopname2);
		//����ǰչʾ�ڼ�ҳ����
		String curpa=req.getParameter("curPage");
		if(curpa!=null) {
			curPage = Integer.parseInt(curpa);
			System.out.println("��ǰ���������ҳ����"+curPage);
			a=curPage*pageSize;
		}
		//����ǰ���ĸ����࣬Ҫ��ı���
		String commentablename=req.getParameter("commenttablename");
		System.out.println("��ǰ�Ĵ��࣬��comment��"+commentablename);
		//��ǰҪ���ȶȵı���
		String shoptablename=req.getParameter("shoptablename");
		System.out.println("��ǰ��������list�����ȶȣ����"+shoptablename);
		
		if(shopname2!=null) {
		try {
			conn=JDBCUtil.getconn();
			st=conn.createStatement();
			String sql="select *from "+commentablename+" where shopname ='"+shopname2+"' limit "+a+","+pageSize+" ";
			rs=st.executeQuery(sql);
						
			Comment co=null;
			while(rs.next()) {
				co=new Comment();				
				co.setShopname(rs.getString("shopname"));				
				co.setUsername(rs.getString("username"));	
				co.setUserphoto(rs.getString("userphoto"));
				co.setTime(rs.getString("time"));
				co.setComment(rs.getString("comment"));
				co.setId(rs.getInt("id"));
				co.setLikenum(rs.getInt("likenum"));
				System.out.println(rs.getInt("id")+rs.getString("shopname")+rs.getString("username")+rs.getString("userphoto")+rs.getString("time")+rs.getString("comment")+rs.getInt("likenum"));
				ja.put(new JSONObject(co));
			}
				
			//ÿ�鿴һ�θõ������ۣ����õ����ȶ�+1
			String sql2="update "+shoptablename+" set score=score+1 where name='"+shopname2+"'";
			st.executeUpdate(sql2);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  finally {
			  JDBCUtil.close(conn, st, rs);
		  }
		resp.getWriter().write(ja.toString());
		}
	}
}
