package org.wl.core.util.regexp;
/**
 * MatcherResult类
 * 
 */
public class MatcherResult {
	private int start;

	private int end;

	private String content;

	private boolean autoClose = false;

	public MatcherResult() {
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getContent() {
		return content;
	}

	public boolean isAutoClose() {
		return autoClose;
	}

	public String toString() {
		return "[" + start + "]" + content + "[" + end + "]";
	}
}
