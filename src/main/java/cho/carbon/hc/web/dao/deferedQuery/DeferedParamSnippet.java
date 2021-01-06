package cho.carbon.hc.web.dao.deferedQuery;

import java.util.Stack;

public class DeferedParamSnippet {
	private String snippetName;
	private StringBuffer buffer = new StringBuffer();
	private String prependWhenNotEmpty;
	private Stack<String[]> wrapStack = new Stack();

	DeferedParamSnippet(String snippetName) {
		this.snippetName = snippetName;
	}

	DeferedParamSnippet(String snippetName, String snippet) {
		this.snippetName = snippetName;
		if (snippet != null) {
			this.buffer.append(snippet);
		}

	}

	public String getName() {
		return this.snippetName;
	}

	public String getSnippet() {
		StringBuffer temp = new StringBuffer(this.buffer.toString());
		Stack stack = (Stack) this.wrapStack.clone();

		while (!stack.isEmpty()) {
			String[] wrap = (String[]) this.wrapStack.pop();
			if (wrap[0] != null) {
				temp.insert(0, wrap[0]);
			}

			if (wrap[1] != null) {
				temp.append(wrap[1]);
			}
		}

		return temp.toString();
	}

	public boolean isEmpty() {
		return this.buffer.length() == 0 && this.wrapStack.isEmpty();
	}

	public void append(String part) {
		this.appendWithoutPadding(" " + part + " ");
	}

	public DeferedParamSnippet appendWithoutPadding(String part) {
		if (part != null) {
			this.buffer.append(part);
		}

		return this;
	}

	public String getPrependWhenNotEmpty() {
		return this.prependWhenNotEmpty;
	}

	public void setPrependWhenNotEmpty(String prependWhenNotEmpty) {
		this.prependWhenNotEmpty = prependWhenNotEmpty;
	}

	public void trimBefore(String trimStr) {
		if (trimStr != null && !trimStr.isEmpty()) {
			int index = this.buffer.indexOf(trimStr);
			if (index == 0) {
				this.buffer.substring(trimStr.length());
			}
		}

	}

	public void pushWrap(String prefix, String suffix) {
		String[] wrap = new String[]{prefix, suffix};
		this.wrapStack.push(wrap);
	}

	public String[] popWrap() {
		return (String[]) this.wrapStack.pop();
	}
}