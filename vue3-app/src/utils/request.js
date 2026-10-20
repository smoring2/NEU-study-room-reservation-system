import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";
import { useUserStore } from "@/stores/user";
import { getToken } from "@/utils/auth";

// Create axios instance
const service = axios.create({
  baseURL: import.meta.env.VITE_BASE_API, // api: base_url
  timeout: 5000 // request timeout
});

// request interceptor
service.interceptors.request.use(
  config => {
    // the store is resolved lazily, inside the interceptor, so that this module
    // can be imported before the Pinia instance is installed on the app
    const userStore = useUserStore();
    if (userStore.token) {
      config.headers["token"] = getToken(); // Let each request carry a custom token, please modify it according to the actual situation
    }
    return config;
  },
  error => {
    // Do something with request error
    console.log(error); // for debug
    Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
  response => {
    /**
     * code != 200: throw fault Can be modified according to your own business
     */
    const res = response.data
    if (res.code !== 200) {
      ElMessage({
        message: res.message,
        type: "error",
        duration: 5 * 1000
      })

      // 50008:illegal token; 50012:Another client is logged in;  50014:Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        ElMessageBox.confirm(
          "You have been logged out, you can cancel to stay on this page, or log in again",
          "log out",
          {
            confirmButtonText: "re-register",
            cancelButtonText: "Cancel",
            type: "warning"
          }
        ).then(() => {
          const userStore = useUserStore();
          userStore.FedLogOut().then(() => {
            location.reload(); // In order to re-instantiate the vue-router object， avoid bug
          });
        });
      }
      return Promise.reject("error");
    } else {
      return response.data;
    }
  },
  error => {
    console.log("err" + error); // for debug
    ElMessage({
      message: error.message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
