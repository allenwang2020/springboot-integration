FROM mysql:5.7.18
#mysql的工作位置
ENV WORK_PATH /usr/local/

#定義會被容器自動執行的目錄
ENV AUTO_RUN_DIR /docker-entrypoint-initdb.d

#初始化數據庫的SQL
ENV FILE_0 seckill.sql

#執行SQL
ENV INSTALL_DATA_SHELL docker-entrypoint.sh

COPY ./$FILE_0 $WORK_PATH/
#COPY ./$FILE_1 $WORK_PATH/
#把要執行的shell文件放到/docker-entrypoint-initdb.d/目錄下，容器會自動執行這個shell
COPY ./$INSTALL_DATA_SHELL $AUTO_RUN_DIR/

#給執行文件增加可執行權限
RUN chmod a+x $AUTO_RUN_DIR/$INSTALL_DATA_SHELL

#設置容器啓動時執行的命令
#CMD ["sh", "/docker-entrypoint-initdb.d/import.sh"]