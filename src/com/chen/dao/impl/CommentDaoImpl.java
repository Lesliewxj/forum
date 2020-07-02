/**
 * 
 */
package com.chen.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chen.bean.Comments;
import com.chen.dao.CommentDao;

/**
 * @author chenguoji
 * @email chenguo_ji@163.com
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class CommentDaoImpl implements CommentDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean add(Comments comment) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(comment);
		return true;
	}

	public void update(Comments comment) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(comment);
	}

	public Comments find(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Comments comment = (Comments) session.get(Comments.class, id);
		return comment;
	}

	public void delete(Comments comment) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(comment);
	}

}
