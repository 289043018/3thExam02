package com.hand.Exam02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.integration.jboss.ExtendedMysqlExceptionSorter;

/**
 在控制台中接收用户输入的Customer	 ID。返回这个
Customer中	 租的所的Film,按租用时间倒排序.
样例:
输入:	
请输入Customer	ID:	571
输出:
JOHNNIE.CHISHOLM租用的Film->
Film	ID|Film	名称|	租用时间
628			|	NORTHWEST	POLISH	|	2005-08-23	15:35:59	
……
 */
public class App 
{
	public static void main(String[] args) throws SQLException{
		 
		  //当前的数据库连接
		  Connection conn = null;
		  //发送sql语句
		  Statement st = null;
		  //结果集
		  ResultSet rs = null;
		  ResultSet rs2 = null;
		  ResultSet rs3 = null;
		  ResultSet rs4 = null;
		 System.out.println("输入：");
		 System.out.println("请输入Customer	ID:");
		 Scanner scanner = new Scanner(System.in);
		 String customer_id;
		 
		 customer_id= scanner.nextLine();
		 
		 
		 
		  //创建一个字符串保存SQL语句
		  String sql = "select first_name,last_name from customer where customer_id ="+ customer_id;
		  String sql2 = "select rental_date,inventory_id from rental where customer_id ="+customer_id;
		  try {
		   //注册一个驱动
		   Class.forName("com.mysql.jdbc.Driver");
		   //获取数据库连接
		   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila","root","root");
		   //发送SQL语句
		   st = conn.createStatement();
		   rs = st.executeQuery(sql);
		   System.out.println("输出：");
		   while(rs.next()){
				  
				  System.out.print(rs.getString("first_name"));
				  System.out.print(".");
				  System.out.print(rs.getString("last_name"));
			  }
		   System.out.println("租用的Film-> ");
		  
		   //获取用户的租借日期和库存ID
		   rs2 = st.executeQuery(sql2);
		   List<String> list1 = new ArrayList<String>();
		   List<Date> list2 = new ArrayList<Date>();
		   List<Integer> list3 = new ArrayList<Integer>();
		   List<String>  list4 = new ArrayList<String>();
		   while(rs2.next()){
			   list1.add(rs2.getString("inventory_id"));
			   list2.add(rs2.getDate("rental_date"));
		   }

		   for(int i = 0;i <list1.size();i++){
			   String sql3 = "select film_id from inventory where inventory_id ="+ list1.get(i);
			   rs3 = st.executeQuery(sql3);
			   while(rs3.next()){
				   list3.add(rs3.getInt("film_id"));
			   }
		   }
		   
		   for(int i = 0;i <list1.size();i++){
			   String sql3 = "select title from film_text where film_id ="+ list3.get(i);
			   rs3 = st.executeQuery(sql3);
			   while(rs3.next()){
				   list4.add(rs3.getString("title"));
			   }
		   }

		   System.out.println("Film	ID|Film	名称|	租用时间");
		   for(int i = 0;i<list4.size();i++){
			   System.out.print(list3.get(i)+" |");
			   System.out.print(list4.get(i)+" |");
			   System.out.println(list2.get(i));
			   
		   }

		   
		   
		   
		   
		   
		  } catch (Exception e) {
		   e.printStackTrace();
		  }finally{
		   try {//从由小到大的顺序关闭资源
			   rs3.close();
			   rs2.close();
			   rs.close();
		   } catch (Exception e2) {
		   }
		   try {
		    st.close();
		   } catch (Exception e3) {
		   }
		   try {
		    conn.close();
		   } catch (Exception e4) {
		   
		   }
		  }
		 }
}
