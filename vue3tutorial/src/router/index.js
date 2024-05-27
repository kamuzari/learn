import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('@/views/HomeView.vue')
        },
        {
            path: '/render-list',
            name: 'render-list',
            component: () => import('@/views/core/RenderingList.vue')
        },
    ]
})

export default router