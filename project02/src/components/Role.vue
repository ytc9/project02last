<template>
<div>
  
  <div style="margin: 10px 0">
    <!--搜索框图标suffix-icon-->
    <el-input style="width:200px;margin-left: 5px"
              placeholder="请输入名称"
              suffix-icon="el-icon-search"
              v-model="name"
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

     <!--
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
    -->
  
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
    <el-table-column prop="name" label="名称" >
    </el-table-column>
    <el-table-column prop="description" label="描述" >
    </el-table-column>
    
    <el-table-column label="操作" width="280" align="center">
      <template slot-scope="scope">
        <!--这里必须要绑定slot scope不然没法获取编辑数据
           scope.row就是获取的属性
        -->
         <el-button type="info" @click="selectMenu(scope.row.id)">分配菜单<i class="el-icon-menu"></i></el-button>
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
  
  
  <!--visible.sync绑定false属性默认不展示  编辑框 -->
  <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%" style="border-radius: 20px" >
    <el-form label-width="80px" size="small">
      <el-form-item label="名称" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="描述" >
        <el-input v-model="form.description" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
   
   <el-dialog title="菜单分配" :visible.sync="menuDialogVisible" width="30%" style="border-radius: 20px;" >
         <!-- node-key="id"设置节点
              :default-expanded-keys="[2, 3]"
              :default-checked-keys="[5]"      -->
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          node-key="id"
          :default-expanded-keys="[2, 3]"
          :default-checked-keys="[5]"
          @check-change="handleCheckChange">
      </el-tree>
   
      <div slot="footer" class="dialog-footer">
         <el-button @click="menuDialogVisible = false">取 消</el-button>
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
            name:"",
            nickname:"",
            form:{},
            multipleSelection:[],
            dialogFormVisible:false,
            menuDialogVisible:false,
            menuData:[],
            props:{
              label:"name"
            }
        }
    },
    created() {//生命钩子里面发送请求
        this.load()
    },
    methods:{
        reset(){
            this.name=""
            this.load()
        },
    
        load(){
            /* axios.get("/user/page").then(res=>JSON.stringify(res)).then(
             res=>{
               this.tableData=res.data
               this.total=res.total
               console.log(res)
             }*/
            request.get("/role/page",{
                params:{
                    pageNum:this.pageNum,
                    pageSize:this.pageSize,
                    name:this.name,
                }
            }).then(res=>{
               console.log(res)
                this.tableData=res.data.records  //这里参数需要在浏览器的网络监视器里面去看
                this.total=res.data.total
            })
          
        },
    
        save(){
            request.post("/role",this.form).then(res=>{
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
            request.delete("/role/"+id).then(res=>{
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
            request.post("/role/del/batch",ids).then(res=>{
                if (res.code==="200"){
                    this.$message.success("批量删除成功")//成功弹窗
                    this.dialogFormVisible=false
                    this.load()
                }else{
                    this.$message.error("批量删除失败")
                }
            })
        },
   
       selectMenu(roleId){
        this.menuDialogVisible=true
  
         request.get("/menu",{
           params:{
             name:""
           }
         }).then(res=>{
           this.menuData=res.data
         })
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
   
       handleCheckChange(data, checked, indeterminate) {
          console.log(data, checked, indeterminate);
       },
       
    }
}
</script>

<style scoped>

</style>
