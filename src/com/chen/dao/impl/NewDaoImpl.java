/**
 * 
 */
package com.chen.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chen.bean.News;
import com.chen.dao.NewDao;

/**
 * @author chenguoji
 * @email chenguo_ji@163.com
 */
public class NewDaoImpl implements NewDao {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean add(News tnew) {
		Session session = sessionFactory.getCurrentSession();
		session.save(tnew);
		return true;
	}

	public boolean update(News tnew) {
		Session session = sessionFactory.getCurrentSession();
		session.update(tnew);
		return true;
	}
	public News find(int id) {
		Session session = sessionFactory.getCurrentSession();
		News tnew = (News) session.get(News.class,id);
		return tnew;
	}

	public void delete(News tnew) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(tnew);
	}

}
