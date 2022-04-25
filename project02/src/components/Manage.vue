<template>
    <el-container style="min-height: 100vh;"><!--高度都设置好就能贴边-->
        <el-aside :width="sideWidth+'px'"  style="background-color: rgb(238, 241, 246); " >
          <Aside :isCollapse="isCollapse" :logoShow="logoShow"></Aside>
        </el-aside>

        <el-container>
            <el-header
                style="border-bottom: 1px solid#ccc;"
            ><!--弹性布局-->
            <Header :collapseBtnClass="collapseBtnClass"
                    :collapse="collapse"
                    :user="user"
            ></Header>

            </el-header>
            <el-main>
               <!--展示子路由-->
              <!--这里用自定义事件用子组件调用父组件的函数-->
              <router-view @refreshUser="getUser"></router-view>
            </el-main>
               <!--这里将页面绑定通过url传给后端然后返回数据
               @size-change="handleSizeChange"
               @current-change="handleCurrentChange"
               这两个函数用于将点击页面的数值返回给data然后重新再向服务端发送请求
               -->
        </el-container>
    </el-container>
</template>

<script>
import axios from "axios";
import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
   name: 'HelloWorld',
    data() {
      return {
         isCollapse:false,
         sideWidth:200,
         logoShow:true,
         collapseBtnClass:"el-icon-s-fold",/*element ui 将el-icon-s-fold绑定到span的class上*/
         user:{}
        }
    },

   created() {
     this.getUser()//初始化数据
   },
   components:{Aside,Header},
    methods:{
       collapse() {
          this.isCollapse = !this.isCollapse
          if (this.isCollapse) {
             this.sideWidth = 64
             this.collapseBtnClass = "el-icon-s-unfold"
             this.logoShow = false
          } else {
             this.sideWidth = 200
             this.collapseBtnClass = "el-icon-s-fold"
             this.logoShow = true
          }
       },
       getUser(){
          //先通过父组件自定义事件给子组件函数回调然后数据会在父组件里面请求最后用组件传值给子组件显示
          let username=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:{}
          request.get("/user/username/"+username).then(res=>{
            this.user=res.data//获取数据 这里将token那部分的数据和内部的数据做了分离方便以后展示
          })
       }
      },

}

</script>

<style>

</style>
