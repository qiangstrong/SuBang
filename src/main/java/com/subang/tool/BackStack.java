package com.subang.tool;

import java.util.Stack;

import com.subang.bean.PageState;
import com.subang.util.WebConst;

public class BackStack {
	private Stack<PageState> stack;

	public BackStack() {
		this.stack = new Stack<PageState>();
	}

	// 清空back栈；如果栈顶为pageName，则保留栈顶元素
	public void clear(String pageName) {
		if (stack.isEmpty()) {
			return;
		}
		PageState pageState = stack.peek();
		stack.clear();
		if (pageState.getName().equals(pageName)) {
			stack.push(pageState);
		}
	}

	// 如果栈顶元素名称和新元素名称相同，则修改栈顶元素。函数执行结束,栈顶一定为传入的pageState
	public void push(PageState pageState) {
		if (stack.isEmpty()) {
			stack.push(pageState);
			return;
		}
		PageState pageState_old = stack.peek();
		if (pageState_old.getName().equals(pageState.getName())) {
			stack.pop();
		}
		stack.push(pageState);
	}

	// 如果栈顶元素名称和新元素名称相同，则放弃压栈。函数执行结束,栈顶不一定为传入的pageState
	public void pushOptional(PageState pageState) {
		if (stack.isEmpty()) {
			stack.push(pageState);
			return;
		}
		PageState pageState_old = stack.peek();
		if (pageState_old.getName().equals(pageState.getName())) {
			return;
		}
		stack.push(pageState);
	}

	public PageState pop() {
		return stack.pop();
	}

	public PageState peek() {
		return stack.peek();
	}

	public boolean isTop(String pageName) {
		if (stack.isEmpty()) {
			return false;
		}
		PageState pageState = stack.peek();
		if (!pageState.getName().equals(pageName)) {
			return false;
		}
		return true;
	}

	public String getBackLink(String pageName) {
		if (stack.isEmpty()) {
			return WebConst.BACK_PREFIX + "/index.html";
		}
		if (!isTop(pageName)) {
			return stack.peek().calcBackLink();
		}
		if (stack.size() == 1) {
			return WebConst.BACK_PREFIX + "/index.html";
		}
		return stack.elementAt(stack.size() - 2).calcBackLink();
	}
}
