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
      component: () => import("@/components/trip/TripPlanListView.vue"),
    },
    {
      path: "/plan/detail/:id",
      name: "PlanDetail",
      component: () => import("@/components/trip/TripPlanDetailView.vue"),
    },
    {
      path: "/plan/write",
      name: "PlanWrite",
      component: () => import("@/components/trip/TripPlanWriteView.vue"), // 수정된 부분
    },
    {
      path: "/plan/myplan",
      name: "MyPlanView",
      component: () => import("@/components/trip/MyTripPlanView.vue"), // 수정된 부분
    },
    {
      path: "/trip",
      name: "TripView",
      component: () => import("@/views/TripView.vue"),
      redirect: { name: "search" },
      children: [
        {
          path: "/search",
          name: "search",
          component: () => import("@/components/trip/TripSearchView.vue"),
        },
        {
          path: "detail/:contentId",
          name: "TripDetail",
          component: () => import("@/components/trip/TripDetailView.vue"),
        },
      ],
    },
    {
      path: "/favorite",
      name: "FavoriteView",
      component: () => import("@/views/FavoriteView.vue"),
      redirect: { name: "FavoriteList" },
      children: [
        {
          path: "list",
          name: "FavoriteList",
          component: () => import("@/components/favorite/FavoriteList.vue"),
        },
        {
          path: "suggest",
          name: "SuggestList",
          component: () => import("@/components/favorite/SuggestList.vue"),
        },
      ],
    },
    {
      path: "/friend",
      name: "FriendView",
      component: () => import("@/views/FriendView.vue"),
      redirect: { name: "FriendList" },
      children: [
        {
          path: "list",
          name: "FriendList",
          component: () => import("@/components/friend/FriendList.vue"),
        },
        {
          path: "add",
          name: "FriendAdd",
          component: () => import("@/components/friend/FriendAdd.vue"),
        },
        {
          path: "favoritelist/:friendId",
          name: "FriendFavoriteList",
          component: () => import("@/components/friend/FriendFavoriteList.vue"),
        },
      ],
    },
    {
      path: "/message",
      name: "MessageView",
      component: () => import("@/views/MessageView.vue"),
      redirect: { name: "MessageList" },
      children: [
        {
          path: "list",
          name: "MessageList",
          component: () => import("@/components/message/MessageList.vue"),
        },
        {
          path: "write",
          name: "MessageWrite",
          component: () => import("@/components/message/MessageWrite.vue"),
        },
        {
          path: "sendList",
          name: "MessageSendList",
          component: () => import("@/components/message/MessageSendList.vue"),
        },
        {
          path: "detail/:messageNo",
          name: "MessageDetail",
          component: () => import("@/components/message/MessageDetail.vue"),
        },
      ],
    },
    {
      path: "/chat",
      name: "ChatView",
      component: () => import("@/views/ChatView.vue"),
    },
    {
      path: "/news",
      name: "NewsView",
      component: () => import("@/views/NewsView.vue"),
      redirect: { name: "NewsList" },
      children: [
        {
          path: "list",
          name: "NewsList",
          component: () => import("@/components/news/NewsList.vue"),
        },
      ],
    },
    {
      path: "/weather",
      name: "WeatherDetail",
      component: () => import("@/components/weather/WeatherDetail.vue"),
    },
  ],
});

export default router;
