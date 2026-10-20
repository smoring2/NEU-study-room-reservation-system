import { createApp } from "vue";
import { createPinia } from "pinia";

import "normalize.css/normalize.css"; // A modern alternative to CSS resets

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import locale from "element-plus/es/locale/lang/en"; // lang i18n

import "@/styles/index.scss"; // global css
import "@/styles/show.css"; // global css

import App from "./App.vue";
import router from "./router";

import registerSvgIcon from "@/icons"; // icon
import "@/permission"; // permission control

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(ElementPlus, { locale });
registerSvgIcon(app);

app.mount("#root");

export default app;
