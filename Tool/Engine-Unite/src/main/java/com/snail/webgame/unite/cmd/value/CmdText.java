package com.snail.webgame.unite.cmd.value;

import com.snail.webgame.unite.common.value.ComValue;

/**
 * 文本定义区
 * @author panxj
 * @version 1.0 2010-9-25
 */

public class CmdText {
	
	 public final static String WELCOME_CMD_TXT = "合服工具DIY Alpha V1.0—Powered BY 帝国文明\n\n"		
		 +"友情提示："+ComValue.prompt_msg_value+"\n";
	
	 public final static String MENU_CMD_TXT = "请您选择您需要的选项：\n 1. 查询所有数据源\n 2. 修改数据源\n 3. 启动合服，请等待...合服时请勿关闭本工具，否则合服将失败 \n 4. 可恢复角色迁移,请等待...迁移时请勿关闭本工具，否则迁移将失败 \n 5. 退出合服工具 \n 格式如下：1";
	
	 public final static String CHANGE_DB_TXT = "请您输入:数据源 url port 数据库名 用户名 密码\n格式如下：FROM_DB 192.168.6.244 1433 EmpireDB_0708 GAME_ADMIN 123456";

	 public final static String UNITE_DB_TXT = "请您输入:合服标识";
	 
	 public final static String MOVE_DB_TXT = "请您输入:迁移类型";
	
	 public final static String ERROR_CMD_TXT = "您没有指定格式输入,操作失败，将会出现不可预知的错误，请重新再来！";
	
	 public final static String CONFIRM_CMD_TXT = "请您确认操作，Y 继续 N 返回上一步 格式如下：Y";
	 
	 public final static String CHANGE_SUC_TXT = "修改数据源成功了。恭喜您进入下一步操作！";
	 
	 public final static String CHANGE_FAIL_TXT = "修改数据源失败了。很杯具，请您查看配置，再来修改，否则合服将会失败！";
}
