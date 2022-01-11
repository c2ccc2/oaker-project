-- 增加我的工时下 填报记录按钮
INSERT INTO sys_menu( parent_id, menu_name, order_num, is_frame, is_cache, menu_type, visible, status, perms, create_by, create_time )VALUES( 2003, '填报记录', '3', '1', '0', 'F', '0', '0', 'mh:hour:stat', 'admin', sysdate() );
-- 停用我的统计菜单
UPDATE sys_menu SET `status`=1 WHERE menu_id = 2004