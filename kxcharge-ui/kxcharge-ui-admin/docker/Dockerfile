# 使用一个基础镜像
FROM nginx:stable

# 将当前目录下的所有文件复制到镜像的指定路径
COPY . /usr/share/nginx/html

COPY nginx.conf.template /etc/nginx/templates/default.conf.template

# 暴露容器的端口
EXPOSE 80

# 启动Nginx服务器
CMD ["nginx", "-g", "daemon off;"]
