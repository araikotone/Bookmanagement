package test;

import java.sql.Connection;
import java.sql.DriverManager;

class Connect {
	static Connection getConnection() throws Exception {
		//JDBCドライバのロード
		Class.forName("com.mysql.cj.jdbc.Driver");

		//設定

		String url = "jdbc:mysql://localhost/bookmng?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";
		String user = "root";
		String pass = "arai9999";

		//データベースに接続
		Connection con = DriverManager.getConnection(url, user, pass);
		return con;

	}

}
