/**
 * 
 */
package com.chen.service.impl;

import java.util.List;

import com.chen.bean.Helps;
import com.chen.bean.Pages;
import com.chen.dao.HelpDao;
import com.chen.dao.PageDao;
import com.chen.service.HelpService;

/**
 * @author chenguoji
 * @email chenguo_ji@163.com
 */
public class HelpServiceImpl implements HelpService {

	private HelpDao helpDao;
	private PageDao pageDao;

	public HelpDao getHelpDao() {
		return helpDao;
	}

	public void setHelpDao(HelpDao helpDao) {
		this.helpDao = helpDao;
	}

	public PageDao getPageDao() {
		return pageDao;
	}

	public void setPageDao(PageDao pageDao) {
		this.pageDao = pageDao;
	}

	public Helps find(int index) {
		return this.helpDao.find(index);
	}

	public List<Helps> getAll() {
		return this.helpDao.getAll();
	}

	public Pages ManageAllForPages(int pageSize, int nowPage) {
		final String sql = "from Helps as thelp order by thelp.id desc";
		int allRecords = this.pageDao.getAllRowCount(sql);
		int totalPage = Pages.calculateTotalPage(pageSize, allRecords);// æ€»é¡µæ•°
		final int currentoffset = Pages.currentPage_startRecord(pageSize,
				nowPage);// å½“å‰�é¡µçš„å¼€å§‹è®°å½•
		final int length = pageSize;
		final int currentPage = Pages.judgeCurrentPage(nowPage);
		List<Helps> listHelps = this.pageDao.query_Objects_ForPages(sql,
				currentoffset, length);
		Pages pagebean = new Pages();
		pagebean.setPageSize(pageSize);
		pagebean.setAllRecords(allRecords);
		pagebean.setCurrentPage(currentPage);
		pagebean.setTotalPages(totalPage);
		pagebean.setListHelp(listHelps);
		pagebean.init();
		return pagebean;
	}

	public void ManageAdd(Helps thelp) {
		this.helpDao.add(thelp);
	}

	public void ManageUpdate(Helps thelp) {
		this.helpDao.update(thelp);
	}

}
