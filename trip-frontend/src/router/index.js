import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "../views/TheMainView.vue";
import LoginView from "../views/LoginView.vue";
import MyPageView from "../views/MyPageView.vue";
import NoticeView from "../views/NoticeView.vue";
import SignUpView from "../views/SignUpView.vue";
import TripPlanView from "../views/TripPlanView.vue";
import TripSearchView from "../views/TripSearchView.vue";

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
    { path: "/login", name: "login", component: LoginView },
    { path: "/signup", name: "signup", component: SignUpView },
    { path: "/mypage", name: "mypage", component: MyPageView },
    { path: "/notice", name: "notice", component: NoticeView },
    { path: "/plan", name: "plan", component: TripPlanView },
    { path: "/search", name: "search", component: TripSearchView },
  ],
});

export default router;
