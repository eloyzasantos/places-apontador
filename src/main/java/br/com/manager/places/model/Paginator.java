package br.com.manager.places.model;

public class Paginator {

	private long found;
	private int page;
	private int pages;
	
	public long getFound() {
		return found;
	}
	public void setFound(long found) {
		this.found = found;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
}
