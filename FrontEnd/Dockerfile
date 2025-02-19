# 第一階段：建置 Angular 應用
FROM node:18.20.6-alpine3.21 AS angular
WORKDIR /app
COPY package*.json ./
RUN npm ci
RUN npm install -g @angular/cli
COPY  . .
RUN npm run build --configuration=production

# 第二階段：使用 Nginx 伺服 Angular 應用
FROM nginx:stable-alpine3.20
# 這裡是 Nginx 預設提供靜態資源的目錄
#WORKDIR /usr/share/nginx/html 
# **這一步是關鍵！複製 Nginx 設定檔**
COPY nginx.conf /etc/nginx/conf.d/default.conf
# 複製 Angular 應用（已經 build 完成的 dist/ 目錄）
COPY --from=angular /app/dist/angular-17-crud/browser /usr/share/nginx/html 

# 開放 80 端口
EXPOSE 80
CMD [ "nginx","-g","daemon off;" ]