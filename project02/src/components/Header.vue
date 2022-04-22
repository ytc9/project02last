<template>
<div style="line-height: 60px;display: flex">
  <div style="flex: 1;">
     <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px;" @click="collapse"></span>
   
     <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
     </el-breadcrumb>
     
  </div><!--cursor: pointer 箭头移到箭头时鼠标变换手形状-->
  <el-dropdown style="width: 100px;cursor: pointer">
      <!--这里必须要加div不然点击没个人信息和退出窗口-->
     <div style="display: inline-block">
        <img :src="user.avatar" alt=""
             style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px"
        >
        <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
     </div>
    <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">
         <span @click="login">个人信息</span>
      </el-dropdown-item>
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">
         <span style="text-decoration: none" @click="logout">退出</span>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
   name: "Header",
   props:{
     collapseBtnClass:String,
     isCollapse:Boolean,
     collapse:Function
   },
   computed:{
     currentPathName(){
        //这里通过vuex来监控路由守卫传来对应路由的名称来实现标题的变化
        return this.$store.state.currentPathName
     }
   },
   watch:{
   },
  data(){
      return{
        user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}
      }
  },
  methods: {
      logout(){
         this.$router.push("/login")
         localStorage.removeItem("user")
         this.$message.success("退出成功")
      },
      login(){
        this.$router.push("/person")
     }
  }
}
</script>

<style scoped>

</style>
