<template>
   <div>
      <div style="margin: 10px 0">
         <!--搜索框图标suffix-icon-->
         <el-input style="width:200px;margin-left: 5px"
                   placeholder="请输入名称"
                   suffix-icon="el-icon-search"
                   v-model="name"
         ></el-input>
         <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
         <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
      </div>
      <div style="margin: 10px 0">
         <!--这里用饿啦吗ui的上传组件来使用上传接口-->
         <el-upload
             action="http://localhost:9090/file/upload"
             style="display: inline-block"
             :show-file-list="false"
             :on-success="handleFileUploadSuccess"
         >
            <el-button type="primary" style="margin-left: 5px">上传文件<i class="el-icon-bottom"></i></el-button>
         </el-upload>
      
         <el-popconfirm
             style="margin-left: 5px"
             confirm-button-text='好的'
             cancel-button-text='我再想想'
             icon="el-icon-info"
             icon-color="red"
             title="您确定批量删除这些数据吗？"
             @confirm="delBatch"
         >   <!--删除确认框 这里有确认框就不用@click用@confirm-->
            <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
         </el-popconfirm>
      </div>
      <el-table :data="tableData"
                :header-cell-style="{'background-color': '#ccc'}"
                border stripe
                @selection-change="handleSelectionChange"
      >
         <el-table-column type="selection" width="55">
         </el-table-column>
         <el-table-column prop="id" label="ID" width="80">
         </el-table-column>
         <el-table-column prop="name" label="文件名称" >
         </el-table-column>
         <el-table-column prop="type" label="文件类型" >
         </el-table-column>
         <el-table-column prop="size" label="文件大小">
         </el-table-column>
         <el-table-column label="下载">
            <template slot-scope="scope">
               <!---->
               <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
            </template>
         </el-table-column>
         <el-table-column  label="启用">
            <template slot-scope="scope">
               <div @click="changeEnable(scope.row)">
                  <el-switch v-model="scope.row.enable"
                             active-color="#13ce66"
                             inactive-color="#ccc"
                             >
                  </el-switch>
               </div>
            </template>
         </el-table-column>
      
         <el-table-column label="操作" width="200" align="center">
            <template slot-scope="scope">
               <!--这里必须要绑定slot scope不然没法获取编辑数据
                  scope.row就是获取的属性
               -->
               <el-popconfirm
                   style="margin-left: 5px"
                   confirm-button-text='好的'
                   cancel-button-text='我再想想'
                   icon="el-icon-info"
                   icon-color="red"
                   title="您确定删除吗？"
                   @confirm="handleDelete(scope.row.id)"
               >   <!--删除确认框 这里有确认框就不用@click用@confirm-->
                  <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
               </el-popconfirm>
            </template>
         </el-table-column>
      </el-table>
   
      <div style="padding: 10px 0">
         <el-pagination
             @size-change="handleSizeChange"
             @current-change="handleCurrentChange"
             :current-page="pageNum"
             :page-sizes="[10, 20]"
             :page-size="pageSize"
             layout="total, sizes, prev, pager, next, jumper"
             :total="total">
         </el-pagination>
      </div>
   </div>
</template>

<script>
import request from "@/utils/request";

export default {
   name: "File",
   data(){
       return{
          tableData:[],
          name:"",
          multipleSelection:[],
          pageNum:1,
          pageSize:10,
          total:0
       }
   },
   created() {
      this.load()
   },
   methods:{
      reset(){
         this.name=""
         this.load()
      },
   
      load(){
         request.get("/file/page",{
            params:{
               pageNum:this.pageNum,
               pageSize:this.pageSize,
               name:this.name,
            }
         }).then(res=>{
            //console.log(res)
            this.tableData=res.data.records  //这里参数需要在浏览器的网络监视器里面去看
            this.total=res.data.total
         })
      },
   
      handleDelete(id){
         request.delete("/file/"+id).then(res=>{
            if (res.code==="200"){
               this.$message.success("删除成功")//成功弹窗
               this.dialogFormVisible=false
               this.load()
            }else{
               this.$message.error("删除失败")
            }
         })
      },
      handleSelectionChange(val){//这个函数用于选择返回多选框的id
         this.multipleSelection=val
      },
      delBatch(){
         let ids=this.multipleSelection.map(v=>v.id)//把对象是数组转换为纯数组
         request.post("/file/del/batch",ids).then(res=>{
            if (res.data){
               this.$message.success("批量删除成功")//成功弹窗
               this.dialogFormVisible=false
               this.load()
            }else{
               this.$message.error("批量删除失败")
            }
         })
      },
   
      handleSizeChange(pageSize){
         this.pageSize=pageSize
         this.load()
      
      },
      handleCurrentChange(pageNum){
         this.pageNum=pageNum
         this.load()
      },
      handleFileUploadSuccess(){
         //上传接口完成后直接load
         this.load()
      },
      download(url){
         //scope.row.url就是fileuid所以这里用window.open的方式访问下载接口
         window.open(url)
      },
      changeEnable(row){//scope.row就代表文件的对象
       request.post("/file/update",row).then(res=>{
          console.log(res)
          if(res.code==="200"){
             this.$message.success("操作成功")
         }
       })
      }
   }
}
</script>

<style scoped>

</style>
