FROM nginx:latest

ADD conf/nginx.conf /etc/nginx/conf.d/nginx.conf
EXPOSE 80 443 
CMD ["nginx", "-g", "daemon off;"]