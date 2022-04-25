<template>
  <el-menu :default-openeds="['1', '3']"
           style="min-height: 100%;overflow: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
  >     <!--overflow：hidden
            router表示开启路由模式 在index里面定义重定向路径点击就会进入其他路由
            :collapse-transition="false"取消掉默认动画
            :collapse判断是否展开分组
            内容突出对其-->
    <div style="height: 60px;line-height: 60px;text-align: center" >
      <img src="../assets/logo.png"  style="width: 20px
                    ;position: relative;top: 5px;margin-right: 4px;"  >
      <b style="color: white" v-show="logoShow">后台管理系统</b>
    </div>
     
      <el-menu-item index="/home">
          <template slot="title">
              <i class="el-icon-house"></i>
              <span slot="title">主页</span>
          </template>
      </el-menu-item>
      
      <el-submenu index="2">
          <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">系统管理</span>
          </template>
          <el-menu-item index="/user">
              <i class="el-icon-s-custom"></i>
              <span slot="title">用户管理</span>
          </el-menu-item>
         <el-menu-item index="/file">
            <i class="el-icon-document"></i>
            <span slot="title">文件管理</span>
         </el-menu-item>
      </el-submenu>
    
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{//接收组件传参
    isCollapse:Boolean,
    logoShow:Boolean
  },
  methods:{
    handleSelect(){
    this.$route.fullPath
    }
  }
}
</script>

<style scoped>

</style>
