package com.masiya.topup.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBConnectionFactory {
	private static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "mybatis-config.xml";
			//Properties prop = new Properties();
			//InputStream in = DBConnectionFactory.class.getResourceAsStream("jdbc.properties");
			//prop.load(in);
			//in.close();
			Reader reader = Resources.getResourceAsReader(resource);
			if (sqlSessionFactory == null) {
				//sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, prop);
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}