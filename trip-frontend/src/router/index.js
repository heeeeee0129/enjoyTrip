import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "@/views/TheMainView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/user",
      name: "UserView",
      component: () => import("@/views/UserView.vue"),
      children: [
        {
          path: "/user/login",
          name: "UserLogin",
          component: () => import("@/components/user/UserLogin.vue"),
        },
        {
          path: "/user/mypage",
          name: "UserMypage",
          component: () => import("@/components/user/UserMypage.vue"),
        },
        {
          path: "/user/modify",
          name: "UserModify",
          component: () => import("@/components/user/UserModify.vue"),
        },
        {
          path: "/user/join",
          name: "UserJoin",
          component: () => import("@/components/user/UserJoin.vue"),
        },
      ],
    },
    {
      path: "/notice",
      name: "notice",
      component: () => import("@/views/NoticeView.vue"),
    },
    {
      path: "/plan",
      name: "plan",
      component: () => import("@/views/TripPlanView.vue"),
    },
    {
      path: "/search",
      name: "search",
      component: () => import("@/views/TripSearchView.vue"),
    },
  ],
});

export default router;
