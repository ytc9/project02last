<template>
<el-card style="width: 500px;margin: 100px auto">
   <el-form label-width="80px" size="small">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
         <img v-if="form.avatar" :src="form.avatar" class="avatar">
         <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      
      <!--用户名按业务来说是不能修改的-->
      <el-form-item label="用户名" >
         <el-input v-model="form.username" disabled autocomplete="off"></el-input>
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
         <el-input v-model="form.address" autocomplete="off" type="textarea"></el-input>
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
      this.getUser().then(res=>{
      this.form=res
      })
   },
   methods:{
      //async把异步的方法变成同步
      async getUser(){
         return  (await request.get("/user/username/"+this.user.username)).data
      },
      //getUser就相当于request.get()
      save(){
         request.post("/user",this.form).then(res=>{
            if (res.code==="200"){
               this.$message.success("保存成功")//成功弹窗
               //自定义事件
               this.$emit("refreshUser")
               //更新浏览器缓存
               this.getUser().then(res=>{
                  res.token=JSON.parse(localStorage.getItem("user")).token
                  localStorage.setItem("user",JSON.stringify(res))
               })
               
            }else{
               this.$message.error("保存失败")
            }
         })
      },
      handleAvatarSuccess(res){
         this.form.avatar=res
      }
      },
}
</script>

<style>
.avatar-uploader{
   text-align: center;
   padding-bottom: 10px;
}
.avatar-uploader .el-upload {
   border: 1px dashed #d9d9d9;
   border-radius: 6px;
   cursor: pointer;
   position: relative;
   overflow: hidden;
}
.avatar-uploader .el-upload:hover {
   border-color: #409EFF;
}
.avatar-uploader-icon {
   font-size: 28px;
   color: #8c939d;
   width: 178px;
   height: 178px;
   line-height: 178px;
   text-align: center;
}
.avatar {
   width: 158px;
   height: 158px;
   display: block;
}
</style>
