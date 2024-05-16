import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
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
          path: "/user/findpassword",
          name: "FindPassword",
          component: () => import("@/components/user/UserFindPassword.vue"),
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
      name: "NoticeView",
      component: () => import("@/views/NoticeView.vue"),
      redirect: { name: "NoticeList" },
      children: [
        {
          path: "list",
          name: "NoticeList",
          component: () => import("@/components/notice/NoticeList.vue"),
        },
        {
          path: "write",
          name: "NoticeWrite",
          component: () => import("@/components/notice/NoticeWrite.vue"),
        },
        {
          path: "modify/:articleNo",
          name: "NoticeModify",
          component: () => import("@/components/notice/NoticeModify.vue"),
        },
        {
          path: "detail/:articleNo",
          name: "NoticeDetail",
          component: () => import("@/components/notice/NoticeDetail.vue"),
        },
      ],
    },
    {
      path: "/hotplace",
      name: "HotPlaceView",
      component: () => import("@/views/HotPlaceView.vue"),
      redirect: { name: "HotPlaceList" },
      children: [
        {
          path: "list",
          name: "HotPlaceList",
          component: () => import("@/components/hotplace/HotPlaceList.vue"),
        },
        {
          path: "write",
          name: "HotPlaceWrite",
          component: () => import("@/components/hotplace/HotPlaceWrite.vue"),
        },
        {
          path: "modify/:hotNo",
          name: "HotPlaceModify",
          component: () => import("@/components/hotplace/HotPlaceModify.vue"),
        },
        {
          path: "detail/:hotNo",
          name: "HotPlaceDetail",
          component: () => import("@/components/hotplace/HotPlaceDetail.vue"),
        },
      ],
    },
    {
      path: "/plan",
      name: "plan",
      component: () => import("@/components/trip/TripPlanView.vue"),
    },
    {
      path: "/search",
      name: "search",
      component: () => import("@/components/trip/TripSearchView.vue"),
      children: [
        {
          path: "detail/:id",
          name: "TripDetail",
          component: () => import("@/components/trip/TripDetailView.vue"),
        },
      ],
    },
  ],
});

export default router;
