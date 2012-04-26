/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

// TODO use spring-data-common's model
public class PagingInfo {
	private String key;

	private String url;

	private long total;

	private int pageIndex;

	private int numberPerPage;

	private long totalPage;

	public PagingInfo() {
		this("", "", 0, 0, 1);
	}

	public PagingInfo(String key, String url, long total, int pageIndex, int numberPerPage) {
		this.key = key;
		this.url = url;
		this.total = total;
		this.pageIndex = pageIndex;
		if (numberPerPage <= 0) {
			this.numberPerPage = 1;
		} else {
			this.numberPerPage = numberPerPage;
		}
		computeTotalPage();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getNumberPerPage() {
		return numberPerPage;
	}

	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
		computeTotalPage();
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		computeTotalPage();
	}

	public long getTotalPage() {
		return totalPage;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("{");
		sb.append("key=").append(key).append(",url=").append(url);
		sb.append(",total=").append(total).append(",pageIndex=").append(pageIndex);
		sb.append(",numberPerPage=").append(numberPerPage).append("}");
		return sb.toString();
	}

	private void computeTotalPage() {
		totalPage = (total + numberPerPage - 1) / numberPerPage;
	}
}
