import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Index from '../views/Index.vue'

import Menu from "../views/sys/Menu.vue";
import User from "../views/sys/User.vue";
import Role from "../views/sys/Role.vue";
import axios from "../axios";
import store from '../store'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/index',
                name: 'Index',
                component: Index
            },
            {
                path: '/userCenter',
                name: 'UserCenter',
                meta:{
                    title:"个人中心"
                },
                component: () => import( '../views/UserCenter.vue')
            }
        ]
    },


    {
        path: '/Login',
        name: 'Login',
        // 懒加载方式引入
        component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    // 就是为了不需要每次都去加载这个路由
    let hasRoute = store.state.menus.hasRoutes
    let token = localStorage.getItem("token")
    if (to.path == '/login'){
        next()
    }else if(!token){
        // 没有token跳转到登录页面
        next({path: '/login'})

    }else if( token && !hasRoute){
        axios.get("/sys/menu/nav", {
            headers: {
                Authorization: localStorage.getItem("token")
            }
        }).then(res => {

           // console.log(res.data.data)

            // 拿到menuList
            store.commit("setMenuList", res.data.data.nav)

            // 拿到用户权限
            store.commit("setPermList", res.data.data.authoritys)

            //console.log(store.state.menus.menuList)

            // 动态绑定路由
            let newRoutes = router.options.routes

            console.log('res.data.data.nav')
            console.log(res.data.data.nav)

            res.data.data.nav.forEach(menu => {
                if (menu.children) {
                    menu.children.forEach(e => {
                        console.log('获取的icon')
                        console.log(e.icon+" \t\t name:"+e.title)
                        // 转成路由
                        let route = menuToRoute(e)

                        // 吧路由添加到路由管理中
                        if (route) {
                            newRoutes[0].children.push(route)
                        }

                    })
                }
            })

            router.addRoutes(newRoutes)

            hasRoute = true
            store.commit("changeRouteStatus", hasRoute)
        })
    }


    next()
})
// 导航转成路由
const menuToRoute = (menu) => {

    if (!menu.component) {
        return null
    }

    let route = {
        name: menu.name,
        path: menu.path,
        meta: {
            icon: menu.icon,
            title: menu.title
        }
    }
    route.component = () => import('@/views/' + menu.component + '.vue')

    return route
}
export default router

