import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex)


const store =new Vuex.Store({
    actions:{

    },
    mutations:{
       setPath(state,value){
           state.currentPathName=value
       }
    },
    state:{
      currentPathName:""
    }
})

export default store
