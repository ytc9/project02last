<template>
<el-card style="width: 500px;margin: 180px auto">
   <el-form label-width="80px" size="small">
<<<<<<< HEAD
=======
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
         <img v-if="form.avatar" :src="form.avatar" class="avatar">
         <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      
>>>>>>> parent of 6ea6c68... code
      <el-form-item label="用户名" >
         <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称" >
         <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" >
         <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话" >
         <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址" >
         <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
         <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
   </el-form>
</el-card>
</template>

<script>
import request from "@/utils/request";

export default {
   name: "person",
   data(){
      return{
         form:{},
         user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}
      }
   },
   created() {
      request.get("/user/username/"+this.user.username).then(res=>{
         if (res.code==="200"){
            this.form=res.data
         }
      })
   },
   methods:{
      save(){
         request.post("/user",this.form).then(res=>{
            if (res.data){
               this.$message.success("保存成功")//成功弹窗
<<<<<<< HEAD
=======
               //自定义事件
               this.$emit("refreshUser")
               
>>>>>>> parent of 6ea6c68... code
            }else{
               this.$message.error("保存失败")
            }
         })
      },
   }
}
</script>

<style scoped>

</style>
