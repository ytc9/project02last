import Vue from "vue"
import Vuex from "vuex"
import router, {resetRouter} from "@/router";

Vue.use(Vuex)


const store =new Vuex.Store({
    actions:{

    },
    mutations:{
       setPath(state,value){
           state.currentPathName=value
       },
       logout(){
           localStorage.removeItem("user")
           localStorage.removeItem("menus")
           router.push("/login")
           //每次退出都要重置成新路由
           resetRouter()
       }
    },
    state:{
      currentPathName:""
    }
})

export default store
