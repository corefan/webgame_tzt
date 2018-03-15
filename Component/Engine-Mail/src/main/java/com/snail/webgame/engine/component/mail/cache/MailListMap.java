package com.snail.webgame.engine.component.mail.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import com.snail.webgame.engine.component.mail.common.MailInfo;

public class MailListMap {
	private static HashMap<Long, LinkedHashMap<Long, MailInfo>> map = new HashMap<Long, LinkedHashMap<Long, MailInfo>>();
	
	public static void addMailInfo(MailInfo mailInfo){
		if(map.containsKey(mailInfo.getReceRoleId())){
			HashMap<Long, MailInfo> map1 = map.get(mailInfo.getReceRoleId());
			map1.put(mailInfo.getId(), mailInfo);
		}
		else{
			LinkedHashMap<Long, MailInfo> map1 = new LinkedHashMap<Long, MailInfo>();
			map1.put(mailInfo.getId(), mailInfo);
			map.put(mailInfo.getReceRoleId(), map1);
		}
	}
	
	public static LinkedHashMap<Long, MailInfo> getMailInfo(long recRoleId){
		return map.get(recRoleId);
	}
	
	public static Set<Long> keySet(){
		return map.keySet();
	}
}
