#!/bin/bash
echo "--------------------------------------------------------------------"
ACTIVE_PROFILE=build
echo "ACTIVE_PROFILE=[$ACTIVE_PROFILE]"
echo "--------------------------------------------------------------------"

echo "-----> [`date +%F` `date +%T`]:更新 开始 <-----"

# 更新
echo "-----> [`date +%F` `date +%T`]:从GIT拉取 开始 <-----"
git pull origin master
echo "-----> [`date +%F` `date +%T`]:从GIT拉取 结束 <-----"

# 下载依赖
echo "-----> [`date +%F` `date +%T`]:下载依赖 开始 <-----"
npm install
echo "-----> [`date +%F` `date +%T`]:下载依赖 结束 <-----"

# 打包
echo "-----> [`date +%F` `date +%T`]:打包 开始 <-----"
npm run $ACTIVE_PROFILE
echo "-----> [`date +%F` `date +%T`]:打包 结束 <-----"

echo "-----> [`date +%F` `date +%T`]:更新 结束 <-----"
