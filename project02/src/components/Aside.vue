<template>
  <el-menu :default-openeds="opens"
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
    
    <!-- 动态菜单绑定后台传来的数据但不能防止路径访问 -->
    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{item.name}}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title">{{subItem.name}}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
    
    
    
<!--      <el-submenu index="2">
          <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">系统管理</span>
          </template>
         <el-menu-item index="/user">
              <i class="el-icon-s-custom"></i>
              <span slot="title">用户管理</span>
         </el-menu-item>
         <el-menu-item index="/role">
            <i class="el-icon-s-custom"></i>
            <span slot="title">角色管理</span>
         </el-menu-item>
         <el-menu-item index="/menu">
            <i class="el-icon-s-custom"></i>
            <span slot="title">菜单管理</span>
         </el-menu-item>
         <el-menu-item index="/file">
            <i class="el-icon-document"></i>
            <span slot="title">文件管理</span>
         </el-menu-item>
      </el-submenu>-->
    
  </el-menu>
</template>

<script>


export default {
  name: "Aside",
  props:{//接收组件传参
    isCollapse:Boolean,
    logoShow:Boolean,
    //从Manage获取菜单数据传给子组件Aside
  },
  data(){
    return {
      menus:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")):[],
      opens:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")).map(v=>v.id+""):[]
    }
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
