package com.subang.bean;

public class PageState {

	private SearchArg searchArg;

	public PageState() {
	}

	public PageState(SearchArg searchArg) {
		this.searchArg = searchArg;
	}

	public SearchArg getSearchArg() {
		return searchArg;
	}

	public void setSearchArg(SearchArg searchArg) {
		this.searchArg = searchArg;
	}

}
