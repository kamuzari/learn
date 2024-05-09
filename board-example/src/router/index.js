import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/Home.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
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
            component: () => import('../views/About.vue')
        },
        {
            path: '/declaredRendering',
            name: 'declaredRendering',
            component: ()=> import('../views/DeclaredRendering.vue')
        },
        {
            path: '/propertyBinding',
            name: 'propertyBinding',
            component: ()=> import('../views/PropertyBindingComponent.vue')
        },
        {
            path: '/eventListener',
            name: 'eventListener',
            component: ()=> import('../views/EventListnerComponent.vue')
        },
        {
            path: '/formBinding',
            name: 'formBinding',
            component: ()=> import('../views/FormBinding.vue')
        },
        {
            path: '/conditionRendering',
            name: 'conditionRendering',
            component: ()=> import('../views/ConditionRendering.vue')
        },
        {
            path: '/listRendering',
            name: 'listRendering',
            component: ()=> import('../views/ListRendering.vue')
        },
        {
            path: '/computeProperty',
            name: 'computeProperty',
            component: ()=> import('../views/ComputeProperty.vue')
        },
        {
            path: '/mount',
            name: 'mount',
            component: ()=> import('../views/MountInLifeCycle.vue')
        },
        {
            path: '/whatcher',
            name: 'whatcher',
            component: ()=> import('../views/Whatcher.vue')
        },
        {
            path: '/props',
            name: 'props',
            component: ()=> import('../views/PropsParentToChild.vue')
        },
        {
            path: '/emits',
            name: 'emits',
            component: ()=> import('../views/EmitsChildToParent.vue')
        }
    ]
})

export default router