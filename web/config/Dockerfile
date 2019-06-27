FROM node:8
MAINTAINER August

WORKDIR /usr/src/app
COPY ../. .

RUN npm install

EXPOSE 8080
CMD ["npm", "start"]
