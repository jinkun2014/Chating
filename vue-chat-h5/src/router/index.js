import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const routes = [
    {
        path: '/',
        component: () => import('@/layout/index'),
        redirect: '/home',
        children: [
            {
                path: 'home',
                component: () => import('@/views/home/index'),
                meta: {title: '首页'},
            },
            {
                path: 'vipOrder/create',
                component: () => import('@/views/vipOrder/detail'),
                meta: {title: 'VIP订单'},
            },
            {
                path: 'chat',
                component: () => import('@/views/chat/index'),
                meta: {title: '群聊'},
            },
        ]
    },
    {
        path: '/login',
        component: () => import('@/views/login/index_username_password'),
        // component: () => import('@/views/login/index_phone_code'),
        meta: {title: '登录'}
    },
]

const router = new Router({
    routes: routes
})

export default router
