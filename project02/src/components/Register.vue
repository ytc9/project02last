<template>
   <div class="wrapper">
      <div style="margin: 100px auto;background-color: #fff;width: 350px;height: 400px;padding: 20px;border-radius: 10px">
         <div style="margin: 20px 0;text-align:center;font-size: 24px"><b>注 册</b></div>
          <!--prefix-icon 图标-->
          <!-- 这里必须要绑定:model和:rules 不然会有问题  ref打标签用于判断是否发送请求-->
         <el-form :model="user" :rules="rules" ref="userForm">
            <el-form-item prop="username">
               <el-input  placeholder="请输入账号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item prop="Password">
               <el-input  placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword">
               <el-input  placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
            </el-form-item>
            
            <div style="margin: 5px 0;text-align: center">
               <el-button type="primary" size="small" autocomplete="off" @click="login">注册</el-button>
               <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
            </div>
         </el-form>
      </div>
   </div>
</template>

<script>
import request from "@/utils/request";

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
            ],
            confirmPassword:[
               {required:true, message:"请确认密码",trigger:"blur"},
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
               if (this.user.password!==this.user.confirmPassword){
                  this.$message.error("两次输入密码不一致")
                  return false
               }
               request.post("/user/register",this.user).then(res=>{
                  if (res.code==='200') {//根据后端给的code来判断是否Login res里的都是后端传来的数据
                     this.$message.success("注册成功")
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
