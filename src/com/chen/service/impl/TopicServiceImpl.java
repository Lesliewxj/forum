/**
 * 
 */
package com.chen.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chen.bean.Comments;
import com.chen.bean.News;
import com.chen.bean.Pages;
import com.chen.bean.Topics;
import com.chen.bean.Types;
import com.chen.bean.Users;
import com.chen.dao.CommentDao;
import com.chen.dao.NewDao;
import com.chen.dao.PageDao;
import com.chen.dao.TopicDao;
import com.chen.dao.TypeDao;
import com.chen.dao.UserDao;
import com.chen.service.TopicService;

/**
 * @author chenguoji
 * @email chenguo_ji@163.com
 */
public class TopicServiceImpl implements TopicService {

	private TopicDao topicDao;
	private TypeDao typeDao;
	private PageDao pageDao;
	private CommentDao commentDao;
	private NewDao newDao;
	private UserDao userDao;

	public TopicDao getTopicDao() {
		return topicDao;
	}

	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	public TypeDao getTypeDao() {
		return typeDao;
	}

	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}

	public PageDao getPageDao() {
		return pageDao;
	}

	public void setPageDao(PageDao pageDao) {
		this.pageDao = pageDao;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public NewDao getNewDao() {
		return newDao;
	}

	public void setNewDao(NewDao newDao) {
		this.newDao = newDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean add(Topics topic, Users user, HttpServletResponse response,
			HttpServletRequest request, Types ttype) {
		Date ntime = new Date();
		user.setIntegral(user.getIntegral() + 2 - topic.getIntegral()); // ç”¨æˆ·å�‘å¸ƒä¸€æ�¡å¸–å­�åŠ 2ç§¯åˆ†
		user.setTopCount(user.getTopCount()+1);
		topic.setTopicsUser(user);

		Types ty = this.typeDao.find(ttype.getId());
		ty.setCountTopics(ty.getCountTopics() + 1); // å¸–å­�ç›¸åº”çš„ç±»åž‹é‡Œçš„å¸–å­�æ•°åŠ 1
		ty.getTypesCategory().setCountTopics(
				ty.getTypesCategory().getCountTopics() + 1); // å¸–å­�ç›¸åº”çš„å¤§ç±»åž‹é‡Œçš„å¸–å­�æ•°åŠ 1
		topic.setTopicsType(ty);

		topic.setTopicTime(ntime);
		topic.setCountComment(0);
		topic.setStatus(0);
		this.topicDao.add(topic);
		return true;
	}

	public Topics find(int id) {
		return this.topicDao.find(id);
	}

	public List<Topics> getIndexHotTopic(int index) {
		return this.topicDao.getIndexHot(index);
	}

	public List<Topics> getIndexNiceTopic(int index) {
		return this.topicDao.getIndexNice(index);
	}

	public List<Topics> getIndexFreshTopic(int index) {
		return this.topicDao.getIndexFresh(index);
	}

	public Pages getAllForPages(int pageSize, int nowPage) {
		final String sql = "from Topics topic  order by topic.id desc";
		int allRecords = this.pageDao.getAllRowCount(sql);
		int totalPage = Pages.calculateTotalPage(pageSize, allRecords);// æ€»é¡µæ•°
		final int currentoffset = Pages.currentPage_startRecord(pageSize,
				nowPage);// å½“å‰�é¡µçš„å¼€å§‹è®°å½•
		final int length = pageSize;
		final int currentPage = Pages.judgeCurrentPage(nowPage);
		List<Topics> listTopics = this.pageDao.query_Objects_ForPages(sql,
				currentoffset, length);
		Pages pagebean = new Pages();
		pagebean.setPageSize(pageSize);
		pagebean.setAllRecords(allRecords);
		pagebean.setCurrentPage(currentPage);
		pagebean.setTotalPages(totalPage);
		pagebean.setListTopics(listTopics);
		pagebean.init();
		return pagebean;
	}

	public Pages getHotForPages(int pageSize, int nowPage) {
		final String sql = "from Topics topic where topic.countComment >=3 order by topic.countComment desc";
		int allRecords = this.pageDao.getAllRowCount(sql);
		int totalPage = Pages.calculateTotalPage(pageSize, allRecords);// æ€»é¡µæ•°
		final int currentoffset = Pages.currentPage_startRecord(pageSize,
				nowPage);// å½“å‰�é¡µçš„å¼€å§‹è®°å½•
		final int length = pageSize;
		final int currentPage = Pages.judgeCurrentPage(nowPage);
		List<Topics> listTopics = this.pageDao.query_Objects_ForPages(sql,
				currentoffset, length);
		Pages pagebean = new Pages();
		pagebean.setPageSize(pageSize);
		pagebean.setAllRecords(allRecords);
		pagebean.setCurrentPage(currentPage);
		pagebean.setTotalPages(totalPage);
		pagebean.setListTopics(listTopics);
		pagebean.init();
		return pagebean;
	}
	public Pages getNiceForPages(int pageSize, int nowPage) {
		final String sql = "from Topics topic where topic.niceTopic =1 order by topic.id desc";
		int allRecords = this.pageDao.getAllRowCount(sql);
		int totalPage = Pages.calculateTotalPage(pageSize, allRecords);// æ€»é¡µæ•°
		final int currentoffset = Pages.currentPage_startRecord(pageSize,
				nowPage);// å½“å‰�é¡µçš„å¼€å§‹è®°å½•
		final int length = pageSize;
		final int currentPage = Pages.judgeCurrentPage(nowPage);
		List<Topics> listTopics = this.pageDao.query_Objects_ForPages(sql,
				currentoffset, length);
		Pages pagebean = new Pages();
		pagebean.setPageSize(pageSize);
		pagebean.setAllRecords(allRecords);
		pagebean.setCurrentPage(currentPage);
		pagebean.setTotalPages(totalPage);
		pagebean.setListTopics(listTopics);
		pagebean.init();
		return pagebean;
	}
	public void endTopic(int[] listFloor, List<Comments> listComment) {
		for (int i = 0; i < listComment.size(); i++) {
			listComment.get(i).setIntegral(listFloor[i]);
			listComment
					.get(i)
					.getCommentsUser()
					.setIntegral(
							listComment.get(i).getCommentsUser().getIntegral()
									+ listFloor[i]);
			this.commentDao.update(listComment.get(i));
		}
	}

	public void update(Topics topic) {
		this.topicDao.update(topic);
	}

	public Pages search(String content, int pageSize, int nowPage) {
		final String sql = "from Topics topic where topic.title like '%"
				+ content + "%' or topic.content like '%" + content
				+ "%' order by topic.id desc";
		int allRecords = this.pageDao.getAllRowCount(sql);
		int totalPage = Pages.calculateTotalPage(pageSize, allRecords);// æ€»é¡µæ•°
		final int currentoffset = Pages.currentPage_startRecord(pageSize,
				nowPage);// å½“å‰�é¡µçš„å¼€å§‹è®°å½•
		final int length = pageSize;
		final int currentPage = Pages.judgeCurrentPage(nowPage);
		List<Topics> listTopics = this.pageDao.query_Objects_ForPages(sql,
				currentoffset, length);
		Pages pagebean = new Pages();
		pagebean.setPageSize(pageSize);
		pagebean.setAllRecords(allRecords);
		pagebean.setCurrentPage(currentPage);
		pagebean.setTotalPages(totalPage);
		pagebean.setListTopics(listTopics);
		pagebean.init();
		return pagebean;
	}

	public void updateNews(News tnew, Users user) {
		News tnews = this.newDao.find(tnew.getId());
		if (user.getClock() > 0) {
			user.setClock(user.getClock() - 1);
			//this.userDao.update(user);
		}
		tnews.setStatus(1);
		tnews.getNewsTopic().setTopicsUser(user);
		this.newDao.update(tnews);
	}

}
