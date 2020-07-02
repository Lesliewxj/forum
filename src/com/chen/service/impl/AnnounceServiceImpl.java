/**
 * 
 */
package com.chen.service.impl;

import java.util.List;

import com.chen.bean.Announces;
import com.chen.bean.Pages;
import com.chen.bean.Topics;
import com.chen.dao.AnnounceDao;
import com.chen.dao.PageDao;
import com.chen.service.AnnounceService;

/**
 * @author chenguoji
 * @email chenguo_ji@163.com
 */
public class AnnounceServiceImpl implements AnnounceService {

	private AnnounceDao announceDao;
	private PageDao pageDao;

	public AnnounceDao getAnnounceDao() {
		return announceDao;
	}

	public void setAnnounceDao(AnnounceDao announceDao) {
		this.announceDao = announceDao;
	}

	public PageDao getPageDao() {
		return pageDao;
	}

	public void setPageDao(PageDao pageDao) {
		this.pageDao = pageDao;
	}

	public List<Announces> getIndexAnno(int index) {
		return this.announceDao.getIndexAnno(index);
	}

	public List<Announces> getAll() {
		return this.announceDao.getAll();
	}

	public Pages ManageAllForPages(int pageSize, int nowPage) {
		final String sql = "from Announces as anno order by anno.id desc";
		int allRecords = this.pageDao.getAllRowCount(sql);
		int totalPage = Pages.calculateTotalPage(pageSize, allRecords);// æ€»é¡µæ•°
		final int currentoffset = Pages.currentPage_startRecord(pageSize,
				nowPage);// å½“å‰�é¡µçš„å¼€å§‹è®°å½•
		final int length = pageSize;
		final int currentPage = Pages.judgeCurrentPage(nowPage);
		List<Announces> listAnnounces = this.pageDao.query_Objects_ForPages(sql,
				currentoffset, length);
		Pages pagebean = new Pages();
		pagebean.setPageSize(pageSize);
		pagebean.setAllRecords(allRecords);
		pagebean.setCurrentPage(currentPage);
		pagebean.setTotalPages(totalPage);
		pagebean.setListAnno(listAnnounces);
		pagebean.init();
		return pagebean;
	}

	public Announces find(int id) {
		return this.announceDao.find(id);
	}

	public void ManageUpdate(Announces announce) {
		this.announceDao.update(announce);
	}

	public void ManageAdd(Announces announce) {
		this.announceDao.add(announce);
		
	}

}
