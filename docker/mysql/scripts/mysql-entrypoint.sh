#!/bin/bash
#-u爲數據庫用戶名 -p爲密碼如果數據庫密碼不是root記得修改這裏
mysql -uroot -proot <<EOF
source /usr/local/seckill.sql;