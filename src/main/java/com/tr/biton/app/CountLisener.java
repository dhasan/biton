package com.tr.biton.app;

import java.util.List;
import java.util.Map;

public interface CountLisener {
	Long getCount(Map<String, Object> args);
	List getData(Integer start, Integer count, Map<String, Object> args);
}
