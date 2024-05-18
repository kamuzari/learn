import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/TutorialView/Home.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/todo',
            name: 'TodoProject',
            component: () => import('../views/TodoProject.vue')
        },
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (About.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import('../views/TutorialView/About.vue')
        },
        {
            path: '/declaredRendering',
            name: 'declaredRendering',
            component: () => import('../views/TutorialView/DeclaredRendering.vue')
        },
        {
            path: '/propertyBinding',
            name: 'propertyBinding',
            component: () => import('../views/TutorialView/PropertyBindingComponent.vue')
        },
        {
            path: '/eventListener',
            name: 'eventListener',
            component: () => import('../views/TutorialView/EventListnerComponent.vue')
        },
        {
            path: '/formBinding',
            name: 'formBinding',
            component: () => import('../views/TutorialView/FormBinding.vue')
        },
        {
            path: '/conditionRendering',
            name: 'conditionRendering',
            component: () => import('../views/TutorialView/ConditionRendering.vue')
        },
        {
            path: '/listRendering',
            name: 'listRendering',
            component: () => import('../views/TutorialView/ListRendering.vue')
        },
        {
            path: '/computeProperty',
            name: 'computeProperty',
            component: () => import('../views/TutorialView/ComputeProperty.vue')
        },
        {
            path: '/mount',
            name: 'mount',
            component: () => import('../views/TutorialView/MountInLifeCycle.vue')
        },
        {
            path: '/whatcher',
            name: 'whatcher',
            component: () => import('../views/TutorialView/Whatcher.vue')
        },
        {
            path: '/props',
            name: 'props',
            component: () => import('../views/TutorialView/PropsParentToChild.vue')
        },
        {
            path: '/emits',
            name: 'emits',
            component: () => import('../views/TutorialView/EmitsChildToParent.vue')
        }
    ]
})

export default router