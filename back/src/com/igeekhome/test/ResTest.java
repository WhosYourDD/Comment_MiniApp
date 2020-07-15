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
 * ���ص����б�
 * ������Ƭurl���������֣�����detail
 */

public class ResTest extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������Ӧ�����ʽ
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		int curPage=1;//��ǰҳ��
		int pageSize=5;//ÿҳ���ݸ���
		int a=0;
		
		Statement st=null;
		ResultSet rs=null;
		Connection conn=null;
		JSONArray ja=new JSONArray();
		
		
		//����ǰչʾ�ڼ�ҳ����
		String curpa=req.getParameter("curPage");
		//����ǰҪչʾ������   ����
		String tablename=req.getParameter("curtablename");
		System.out.println("չʾ������Ϣ����ǰlist������"+tablename);
		
		//���õ��� ��ҳ���ַ����� ǿת�� int ��
		if(curpa!=null) {
		curPage = Integer.parseInt(curpa);
		System.out.println(curPage);
		a=curPage*pageSize;
		}
		
	
		try {
			conn=JDBCUtil.getconn();
			st=conn.createStatement();
			String sql="select *from "+tablename+" order by (score) desc limit "+a+","+pageSize+" ";
			rs=st.executeQuery(sql);
			Resturate res=null;
			while(rs.next()) {
				res=new Resturate();
				res.setName(rs.getString("name"));
				res.setPhotourl(rs.getString("photourl"));
				res.setDetail(rs.getString("detail"));
				res.setScore(rs.getInt("score"));
				System.out.println("�������ƣ�"+rs.getString("name")+"\t"+rs.getString("photourl")+"\t"+rs.getString("detail"));
				ja.put(new JSONObject(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, st, rs);
		}
		
		resp.getWriter().write(ja.toString());
	}
       
    
	
	

}
