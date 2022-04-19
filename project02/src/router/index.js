import VueRouter from "vue-router";
import Manage from "@/components/Manage";
import User from "@/components/User";
import Home from "@/components/Home";
import store from "@/store";
import Login from "@/components/Login";

const router=new VueRouter({
    routes:[
        {
            path:"/",
            name:"首页",
            component:Manage,
            redirect:"/home",  //默认重定向到home
            children:[
                {
                    path:"user",
                    name:"系统管理/用户管理",
                    component:User
                },
                {
                    path:"home",
                    name:"首页",
                    component:Home
                }
            ]
        },
        {
            path:"/login",
            name:"Login",
            component:Login
        }
    ]
})

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName",to.name) //给对应的页面存上name值
    store.commit("setPath",to.name) //这里直接将改变的name值 提交到mutations
    next()//放行路由
})

export default router
