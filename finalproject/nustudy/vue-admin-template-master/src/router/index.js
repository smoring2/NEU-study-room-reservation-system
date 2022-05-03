import Vue from "vue";
import Router from "vue-router";

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router);

/* Layout */
import Layout from "../views/layout/Layout";

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [
  {
    path: "/login",
    component: () => import("@/views/login/index"),
    hidden: true
  },
  { path: "/404", component: () => import("@/views/404"), hidden: true },

  {
    path: "/",
    component: Layout,
    redirect: "/dashboard",
    name: "Dashboard",
    hidden: true,
    children: [
      {
        path: "dashboard",
        component: () => import("@/views/dashboard/index")
      }
    ]
  },

  {
    path: "/cmn",
    component: Layout,
    redirect: "/cmn/list",
    name: "Data Mangement",
    alwaysShow: true,
    meta: { title: "Data Management", icon: "example" },
    children: [
      {
        path: "list",
        name: "Data Dict",
        component: () => import("@/views/dict/list"),
        meta: { title: "Data Dict", icon: "table" }
      }
    ]
  },

  {
    path: "/campusset",
    component: Layout,
    redirect: "/campusset/list",
    name: "Campus Set",
    meta: { title: "Campus set", icon: "example" },
    children: [
      {
        path: "list",
        name: "Campus Set List",
        component: () => import("@/views/campusSet/list"),
        meta: { title: "Campus Set List", icon: "table" }
      },
      {
        path: "add",
        name: "Campus Set Add",
        component: () => import("@/views/campusSet/add"),
        meta: { title: "Campus Set Add", icon: "tree" }
      },
      {
        path: "edit/:id",
        name: "Campus Set Edit",
        component: () => import("@/views/campusSet/add"),
        meta: { title: "Campus Set Edit", noCache: true },
        hidden: true
      },
      {
        path: "campus/list",
        name: "Campus List",
        component: () => import("@/views/campus/list"),
        meta: { title: "Campus List", icon: "table" }
      },
      {
        path: "campus/show/:id",
        name: "Deatils",
        component: () => import("@/views/campus/show"),
        meta: { title: "Deatils", icon: "table" },
        hidden: true
      },
      {
        path: "campus/schedule/:campuscode",
        name: "Schedule",
        component: () => import("@/views/campus/schedule"),
        meta: { title: "Schedule", icon: "table" },
        hidden: true
      }
    ]
  },


  { path: "*", redirect: "/404", hidden: true }
];

export default new Router({
  // mode: 'history', //Backend support is available
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
});
