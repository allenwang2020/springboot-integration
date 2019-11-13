# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install --production
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
ADD conf/nginx.conf /etc/nginx/conf.d/nginx.conf
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80 433
CMD ["nginx", "-g", "daemon off;"]