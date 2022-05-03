<template>
<div>
  
  <div style="margin: 10px 0">
    <!--搜索框图标suffix-icon-->
    <el-input style="width:200px;margin-left: 5px"
              placeholder="请输入名称"
              suffix-icon="el-icon-search"
              v-model="username"
    >
    </el-input>
    <el-input style="width:200px;margin-left: 5px"
              placeholder="请输入邮箱"
              suffix-icon="el-icon-message"
              v-model="email"
    >
    </el-input>
    <el-input style="width:200px;margin-left: 5px"
              placeholder="请输入地址"
              suffix-icon="el-icon-position"
              v-model="address"
    >
    
    </el-input>
    <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
    <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
  </div>
  <div style="margin: 10px 0">
    <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    
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
     <!--这里用饿啦吗ui的上传组件来使用上传接口-->
     <el-upload
         action="http://localhost:9090/user/import"
         style="display: inline-block"
         :show-file-list="false"
         accept="xlsx"
         :on-success="handleExcelImportSuccess"
     >
        <el-button type="primary" style="margin-left: 5px">导入<i class="el-icon-bottom"></i></el-button>
     </el-upload>
    <el-button type="primary" style="margin-left: 5px" @click="exp">导出<i class="el-icon-top"></i></el-button>
  </div>
  <!--border stripe表格分割线
  element ui的bug  :header-cell-style="{'background-color': '#ccc'}"
  -->
  <el-table :data="tableData"
            :header-cell-style="{'background-color': '#ccc'}"
            border stripe
            @selection-change="handleSelectionChange"
  >
    <el-table-column
        type="selection"
        width="55">
    </el-table-column>
    <el-table-column prop="id" label="ID" width="80">
    </el-table-column>
    <el-table-column prop="username" label="用户名" width="140">
    </el-table-column>
    <el-table-column prop="nickname" label="昵称" width="120">
    </el-table-column>
    <el-table-column prop="email" label="邮箱">
    </el-table-column>
    <el-table-column prop="address" label="电话">
    </el-table-column>
    <el-table-column prop="address" label="地址">
    </el-table-column>
    
    <el-table-column label="操作" width="200" align="center">
      <template slot-scope="scope">
        <!--这里必须要绑定slot scope不然没法获取编辑数据
           scope.row就是获取的属性
        -->
        <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
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
  
  
  <!--visible.sync绑定false属性默认不展示  编辑框
   autocomplete="off" 关闭自动填充
   这里的v-model用于显示选定项
   -->
  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" style="border-radius: 20px" >
    <el-form label-width="80px" size="small">
      <el-form-item label="用户名" >
          <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="选择角色" >
          <el-select clearable v-model="form.name" placeholder="请选择角色" style="width: 100%">
              <el-option v-for="item in roles" :key="roles.name" :label="roles.name" :value="roles.key"></el-option>
          </el-select>
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
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "User",
    data(){
        return{
            tableData:[],
            total:0,
            pageNum:1,
            pageSize:10,
            username:"",
            email:"",
            address:"",
            nickname:"",
            form:{},
            multipleSelection:[],
            dialogFormVisible:false,
            roles:[]
        }
    },
    created() {//生命钩子里面发送请求
        this.load()
    },
    methods:{
        reset(){
            this.username=""
            this.email=""
            this.address=""
            this.load()
        },
    
        load(){
            /*fetch("/user/page").then(res=>res.json()).then(res=>{
              this.tableData=res.data
              this.total=res.total
              console.log(res)
            })*/
            /* axios.get("/user/page").then(res=>JSON.stringify(res)).then(
             res=>{
               this.tableData=res.data
               this.total=res.total
               console.log(res)
             }*/
            request.get("/user/page",{
                params:{
                    pageNum:this.pageNum,
                    pageSize:this.pageSize,
                    username:this.username,
                    email:this.email,
                    address:this.address
                }
            }).then(res=>{
               console.log(res)
               this.tableData=res.data.records  //这里参数需要在浏览器的网络监视器里面去看
               this.total=res.data.total
            })
            
            request.get("/role").then(res=>{
                this.roles=res.data
            })
        },
    
        save(){
            request.post("/user",this.form).then(res=>{
                if (res.code==="200"){
                    this.$message.success("保存成功")//成功弹窗
                    this.dialogFormVisible=false
                    this.load()
                }else{
                    this.$message.error("保存失败")
                }
            })
        },
    
        handleDelete(id){
            request.delete("/user/"+id).then(res=>{
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
            request.post("/user/del/batch",ids).then(res=>{
                if (res.code==="200"){
                    this.$message.success("批量删除成功")//成功弹窗
                    this.dialogFormVisible=false
                    this.load()
                }else{
                    this.$message.error("批量删除失败")
                }
            })
        },
       exp(){
         window.open('http://localhost:9090/user/export')
       },
   
       handleExcelImportSuccess(){
          this.$message.success("文件导入成功")
          this.load()
       },
       
        handleEdit(row){
            this.form=row
            this.dialogFormVisible=true
        },
    
        handleAdd(){
            this.dialogFormVisible=true
            this.form={}
        },
    
        handleSizeChange(pageSize){
            this.pageSize=pageSize
            this.load()
        
        },
        handleCurrentChange(pageNum){
            this.pageNum=pageNum
            this.load()
        },
       
    }
}
</script>

<style scoped>

</style>
