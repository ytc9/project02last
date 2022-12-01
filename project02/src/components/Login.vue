<template>
   <div class="wrapper">
      <div style="margin: 200px auto;background-color: #fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
         <div style="margin: 20px 0;text-align:center;font-size: 24px"><b>登录</b></div>
          <!--prefix-icon 图标-->
          <!-- 这里必须要绑定:model和:rules 不然会有问题  ref打标签用于判断是否发送请求-->
         <el-form :model="user" :rules="rules" ref="userForm">
            <el-form-item prop="username">
               <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
               <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
            </el-form-item>
            
            <div style="margin: 10px 0;text-align: center">
               <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
               <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
            </div>
         </el-form>
      </div>
   </div>
</template>

<script>
import request from "@/utils/request";
import {setRoutes} from "@/router";

export default {
   name: "Login",
   data(){
      return{
         user:{},
         rules:{
            username:[
               {required:true, message:"请输入用户名",trigger:"blur"},
               {min:3,max:10,  message: "长度在3到10个字符",trigger:"blur"}
            ],
            password:[
               {required:true, message:"请输入密码",trigger:"blur"},
               {min:4,max:20,  message: "长度在4到20个字符",trigger:"blur"}
            ]
         }
      }
   },
   methods:{
      //valid就是用于判断用户名和密码规则判断是否发送请求
      login(){
         this.$refs["userForm"].validate((valid)=>{
            if (valid){
               request.post("/user/login",this.user).then(res=>{
                  if (res.code==='200') {//根据后端给的code来判断是否Login res里的都是后端传来的数据
                     localStorage.setItem("user",JSON.stringify(res.data)) //用户信息存入浏览器(包括菜单)
                     localStorage.setItem("menus",JSON.stringify(res.data.menus))
                     
                    //登录时调用动态路由方法
                     setRoutes()
                     this.$message.success("登录成功")
                     this.$router.push("/")//成功就跳转到/页面
                  }else {
                     this.$message.error(res.msg)
                  }
               })
            }else
               return false
         })
      }
   }
}
</script>

<style scoped>
.wrapper{
   height: 100vh;/*100vh表示100%窗口高度*/
   background-image: linear-gradient(to bottom right,#FC466B,#3F5EFB);/*渐变色*/
   overflow: hidden;
}
</style>
