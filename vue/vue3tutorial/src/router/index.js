import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      redirect: { name: "home-list" },
      children: [
        {
          path: "child",
          name: "child",
          component: () => import("@/views/ChildView.vue"),
        },
        {
          path: "list",
          name: "home-list",
          component: () => import("@/views/HomeList.vue"),
        },
      ],
    },
    {
      path: "/render-list",
      name: "render-list",
      component: () => import("@/views/core/RenderingList.vue"),
    },
    {
      path: "/form-binding",
      name: "form-binding",
      component: () => import("@/views/core/FormBinding.vue"),
    },
    {
      path: "/watchers",
      name: "watchers",
      component: () => import("@/views/core/Watchers.vue"),
    },
    {
      path: "/dynamiccomponent",
      name: "dynamiccomponent",
      component: () => import("@/views/DynamicComponentView.vue"),
    },
  ],
});

export default router;
