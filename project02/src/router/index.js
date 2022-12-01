import VueRouter from "vue-router";
import Manage from "@/components/Manage";
import User from "@/components/User";
import Home from "@/components/Home";
import store from "@/store";
import Login from "@/components/Login";
import Register from "@/components/Register";
import Person from "@/components/Person";
import File from "@/components/File";
import Role from "@/components/Role";
import Menu from "@/components/Menu"

const routes=[
    {
        path:"/login",
        name:"Login",
        component:Login
    },
    {
        path:"/register",
        name:"Register",
        component:Register
    },
    //配置404页面的跳转
    {
        path: "/404",
        name:"404",
        component:()=>import("../components/404")
    }
]

const router=new VueRouter({
    mode:"history",
    base:process.env.BASE_URL,
    routes
})

//提供一个重置所有路由方法可以添加新页面的时候不跳转到404
export const resetRouter=()=>{
    router.matcher=new VueRouter({
        mode:"history",
        base:process.env.BASE_URL,
        routes
    })
}

//拼装动态路由
export const setRoutes=()=>{
    const storeMenus=localStorage.getItem("menus")
    if (storeMenus){

        //设置号动态路由的路径分配
        const manageRoute={
            path:"/" ,
            component:Manage,
            //redirect:"/home", 别乱重定向会有问题最好用默认路径
            children: [
                {path:"person",name:"个人信息",component:()=>import("../components/Person")}
            ]
        }
        const menus=JSON.parse(storeMenus)
        menus.forEach(item =>{
            if (item.path) {
                let itemMenu={
                    path: item.path.replace("/",""),
                    name: item.name,
                    //通过后台传来的vue文件名来拼接路由 这里只能用路径不然路由会找不到组件
                    component:()=>import("../components/"+item.pagePath+".vue")
                }
                manageRoute.children.push(itemMenu)
            }
            else if (item.children.length){
                item.children.forEach(item=>{
                    if (item.path){
                        let itemMenu={
                            path: item.path.replace("/",""),
                            name: item.name,
                            //通过后台传来的vue文件名来拼接路由
                            component:()=>import("../components/"+item.pagePath+".vue")
                        }
                     manageRoute.children.push(itemMenu)
                    }
                })
            }
        })
        //这里最好判断一下Manage是否重复因为有setRoutes的回调所以回有重复
     const currentRoutes=router.getRoutes().map(v=>v.name)
        if (!currentRoutes.includes("Manage")) {
            //动态添加路由
            router.addRoute(manageRoute)
        }
    }
}

//这里必须要重置拼装一下路由不然跳转到空白页面会回不来
setRoutes()



router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName",to.name) //给对应的页面存上name值
    store.commit("setPath",to.name) //这里直接将改变的name值 提交到mutations

    //路由守卫来进行localhost:8080的首次跳转问题
    //未找到路由的情况
    if (!to.matched.length){
        debugger
        const storeMenus=localStorage.getItem("menus")
        if (storeMenus){
            next("/404")
        }else {
            //通过菜单判断是否 跳回登录页面
            next("/login")
        }
    }
    //其他情况放行
    next()
})

export default router
