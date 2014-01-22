/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

/**
 * 分页信息。
 */
public class PagingInfo {
	private String key;

	private String url;

	private long total;

	private int pageIndex;

	private int pageSize;

	private long totalPage;

	public PagingInfo() {
		this("", "", 0, 0, 1);
	}

	public PagingInfo(String key, String url, long total, int pageIndex, int pageSize) {
		this.key = key;
		this.url = url;
		this.total = total;
		this.pageIndex = pageIndex;
		if (pageSize <= 0) {
			this.pageSize = 1;
		} else {
			this.pageSize = pageSize;
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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		computeTotalPage();
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
		sb.append(",pageSize=").append(pageSize).append("}");
		return sb.toString();
	}

	private void computeTotalPage() {
		totalPage = (total + pageSize - 1) / pageSize;
	}
}
