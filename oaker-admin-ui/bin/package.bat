@echo off
echo.
echo [��Ϣ] ��װWeb���̣�����node_modules�ļ���
echo.

%~d0
cd %~dp0

cd ..
npm install --registry=https://registry.npm.taobao.org
npm uninstall node-sass
cnpm install -D node-sass@5.0.0 --save

pause