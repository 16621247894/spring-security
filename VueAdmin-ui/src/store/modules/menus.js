import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
    state: {
        //菜单
        menuList: [],
        // 权限
        permList: [],
        //路由
        hasRoutes: false,
        // 动态tab
        editableTabsValue: 'Index',
        editableTabs: [
            {title: '首页', name: 'Index'},
        ],

    },
    mutations: {
        // 菜单
        setMenuList(state, menus) {
            state.menuList = menus
        },
        //权限
        setPermList(state, perms) {
            state.permList = perms
        },
        //
        changeRouteStatus(state, hasRoutes) {
            state.hasRoutes = hasRoutes
        },
        // 添加tab
        addTab(state, tab) {

            let index = state.editableTabs.findIndex(e => e.name === tab.name);
            if (index === -1) {
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name
                });
            }
            // 切换到tab
            state.editableTabsValue = tab.name

        },
        // 退出
        resetState: (state => {
            state.menuList = []
            state.permList = []
            state.hasRoutes = false
            state.editableTabsValue = 'Index'
            state.editableTabs =
                [
                    {title: '首页', name: 'Index'},
                ]

        })


    },
    actions: {},
    modules: {}
}

