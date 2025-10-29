
FROM node:20-alpine AS build
WORKDIR /dist/src/app
RUN npm cache clean --force
COPY . .
RUN npm install
RUN npx ng build --configuration production
FROM nginx:latest AS ngi
COPY --from=build /dist/src/app/dist/ng-pym-kata/browser /usr/share/nginx/html
COPY /nginx.conf  /etc/nginx/conf.d/default.conf
EXPOSE 80
