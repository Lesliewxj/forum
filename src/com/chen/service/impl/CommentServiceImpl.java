/**
 * 
 */
package com.chen.service.impl;

import java.util.Date;

import com.chen.bean.Comments;
import com.chen.bean.News;
import com.chen.bean.Topics;
import com.chen.bean.Users;
import com.chen.dao.CommentDao;
import com.chen.dao.NewDao;
import com.chen.dao.PageDao;
import com.chen.dao.TopicDao;
import com.chen.service.CommentService;

/**
 * @author chenguoji
 * @email chenguo_ji@163.com
 */

public class CommentServiceImpl implements CommentService {
	private CommentDao commentDao;
	private PageDao pageDao;
	private TopicDao topicDao;
	private NewDao newDao;

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public PageDao getPageDao() {
		return pageDao;
	}

	public void setPageDao(PageDao pageDao) {
		this.pageDao = pageDao;
	}

	public TopicDao getTopicDao() {
		return topicDao;
	}

	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	public NewDao getNewDao() {
		return newDao;
	}

	public void setNewDao(NewDao newDao) {
		this.newDao = newDao;
	}

	public boolean newComment(Comments comment, Users user, Topics topic) {
		Date cTime = new Date();
		comment.setCommentTime(cTime);
		Topics tpc = this.topicDao.find(topic.getId());
		tpc.setCountComment(tpc.getCountComment() + 1); // å¸–å­�è¯„è®ºæ•°åŠ 1
		tpc.getTopicsType().setCountComments(tpc.getTopicsType().getCountComments() + 1); // å¸–å­�å°�ç±»åž‹è¯„è®ºæ•°åŠ 1
		tpc.getTopicsType().getTypesCategory().setCountComments(tpc.getTopicsType().getTypesCategory().getCountComments() + 1); // å¸–å­�å¤§ç±»åž‹è¯„è®ºæ•°åŠ 1
		user.setIntegral(user.getIntegral()+1);  //å›žå¤�å¸–å­�ï¼Œç§¯åˆ†åŠ 1
		user.setComCount(user.getComCount()+1);
		if (tpc.getTopicsUser().getId() != user.getId()) {
			tpc.getTopicsUser().setClock(tpc.getTopicsUser().getClock() + 1);
			News tnew = new News();
			tnew.setNewsCommentUser(user);
			tnew.setNewsTopic(tpc);
			tnew.setStatus(0); // å°†æ¶ˆæ�¯è®¾ä¸ºæœªè¯»
			tnew.setNewTime(cTime);
			this.newDao.add(tnew);
		}
		if (this.topicDao.update(tpc) != true) {
			return false;
		}
		comment.setCommentsTopic(tpc);
		comment.setFloor(tpc.getCountComment()); // è®¾ç½®æ­¤è¯„è®ºæ‰€åœ¨æ¥¼å±‚
		comment.setCommentsUser(user);
		this.commentDao.add(comment);
		return true;
	}
	public void deleteComment(Comments comment, Topics topic) {
		System.out.println("é€šçŸ¥ï¼šè¿™é‡Œæ˜¯comment çš„ actionï¼Œå‡†å¤‡åˆ é™¤å¸–å­�idä¸º"+topic.getId()+"çš„è¯„è®º");
		Topics tpc = this.topicDao.find(topic.getId());
		Comments com = this.commentDao.find(comment.getId());
		System.out.println("è¯„è®ºçš„æ¥¼å±‚ä¸ºï¼š"+com.getFloor());
		if (tpc.getTopicsUser().getId() != com.getCommentsUser().getId()) {
			System.out.println("è¢«åˆ è¯„è®ºçš„ç”¨æˆ·ä¸�æ˜¯è¯¥å¸–å­�çš„ç”¨æˆ·ï¼Œå›žå¤�è¢«åˆ ï¼Œç§¯åˆ†å‡�1");
			com.getCommentsUser().setIntegral(com.getCommentsUser().getIntegral()-1);  //å›žå¤�è¢«åˆ ï¼Œç§¯åˆ†å‡�1
		}
		com.setStatus(1);
		System.out.println("ä¿®æ”¹éœ€è¦�åˆ é™¤çš„è¯„è®ºå�Žï¼Œè¯¥è¯„è®ºçš„çŠ¶æ€�ä¸º"+com.getStatus());
		this.commentDao.update(com);
		System.out.println("é€šçŸ¥ï¼šå¸–å­�è¯„è®ºåˆ é™¤æ“�ä½œå®Œæ¯•ï¼Œå‰�å¾€æŒ‡å®šå¸–å­�");
	}



}
