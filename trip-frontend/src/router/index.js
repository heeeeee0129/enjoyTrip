import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "MainView",
      component: () => import("@/views/MainView.vue"),
    },
    {
      path: "/board",
      name: "BoardView",
      component: () => import("@/views/BoardView.vue"),
      redirect: { name: "BoardList" },
      children: [
        {
          path: "list",
          name: "BoardList",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "detail/:articleNo",
          name: "BoardDetail",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "BoardWrite",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleNo",
          name: "BoardModify",
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
