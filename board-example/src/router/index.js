import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("@/views/Home.vue"),
    },
    {
      path: "/board/:category",
      name: "board",
      component: () => import("@/views/board/BoardsView.vue"),
      redirect: { name: "board-list" },
      children: [
        {
          path: "new",
          name: "new",
          component: () => import("@/components/board/BoardForm.vue"),
        },
        {
          path: "",
          name: "board-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:boardId",
          name: "detail",
          component: () => import("@/components/board/Detail.vue"),
        },
        {
          path: "edit/:boardId",
          name: "edit",
          component: () => import("@/components/board/BoardEdit.vue"),
        },
      ],
    },
  ],
});

export default router;
