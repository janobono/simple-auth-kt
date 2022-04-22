FROM node:16-alpine As development

WORKDIR /usr/src/app

COPY . .

RUN npm install && npm run build

FROM nginx:stable as production

COPY --from=development /usr/src/app/build /usr/share/nginx/html
