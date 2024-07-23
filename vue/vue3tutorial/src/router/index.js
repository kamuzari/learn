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
          component: () => import("vue/vue3tutorial/src/views/ChildView.vue"),
        },
        {
          path: "list",
          name: "home-list",
          component: () => import("vue/vue3tutorial/src/views/HomeList.vue"),
        },
      ],
    },
    {
      path: "/render-list",
      name: "render-list",
      component: () => import("vue/vue3tutorial/src/views/core/RenderingList.vue"),
    },
    {
      path: "/form-binding",
      name: "form-binding",
      component: () => import("vue/vue3tutorial/src/views/core/FormBinding.vue"),
    },
    {
      path: "/watchers",
      name: "watchers",
      component: () => import("vue/vue3tutorial/src/views/core/Watchers.vue"),
    },
  ],
});

export default router;
