/**
 * 
 */
package com.chen.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chen.bean.Categorys;
import com.chen.bean.Pages;
import com.chen.bean.Topics;
import com.chen.bean.Types;
import com.chen.dao.CategoryDao;
import com.chen.dao.TopicDao;
import com.chen.dao.TypeDao;
import com.chen.service.CategoryService;

/**
 * @author chenguoji
 * @email chenguo_ji@163.com
 */
public class CategoryServiceImpl implements CategoryService {

	private CategoryDao cateDao;
	private TypeDao typeDao;
	private TopicDao topicDao;

	public CategoryDao getCateDao() {
		return cateDao;
	}

	public void setCateDao(CategoryDao cateDao) {
		this.cateDao = cateDao;
	}

	public TypeDao getTypeDao() {
		return typeDao;
	}

	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}

	public TopicDao getTopicDao() {
		return topicDao;
	}

	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	public boolean add(Categorys cate) {
		return this.cateDao.add(cate);
	}

	public boolean delete(int index) {
		return false;
	}

	public boolean update(Categorys cate) {
		return this.cateDao.update(cate);
	}

	public List<Categorys> getAll() {
		return this.cateDao.getAll();
	}

	public List<Types> getType(int id) {
		return this.typeDao.getByCate(id);
	}

	public List<Types> new_getType(int id) {
		return this.typeDao.new_getByCate(id);
	}
	public List<Types>manageType(int id) {
		return this.typeDao.manageByCate(id);
	}

	public List<Topics> getTopic(List<Types> listType) {
		return this.topicDao.getByType(listType);
	}

	public Pages getAllForPages(int pageSize, int nowPage, List<Types> listType) {
		List<Topics> listTopic = this.topicDao.getByType(listType);
		int allRecords = listTopic.size();
		int totalPage = Pages.calculateTotalPage(pageSize, allRecords);// æ€»é¡µæ•°
		final int currentoffset = Pages.currentPage_startRecord(pageSize,
				nowPage);// å½“å‰�é¡µçš„å¼€å§‹è®°å½•
		final int length = pageSize;
		final int currentPage = Pages.judgeCurrentPage(nowPage);
		int toIndex = 0;
		if (allRecords >= length + currentoffset) {
			toIndex = currentoffset + length;
		} else {
			toIndex = allRecords;
		}
		List<Topics> subListTopic = listTopic.subList(currentoffset, toIndex);
		Pages pagebean = new Pages();
		pagebean.setPageSize(pageSize);
		pagebean.setAllRecords(allRecords);
		pagebean.setCurrentPage(currentPage);
		pagebean.setTotalPages(totalPage);
		pagebean.setListTopics(subListTopic);
		pagebean.init();
		return pagebean;
	}

	public Categorys find(int id) {
		return this.cateDao.find(id);
	}

}
