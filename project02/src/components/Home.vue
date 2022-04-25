<template>
<div>
<!--流式布局-->
   <el-row :span="10" style="margin-bottom: 40px">
      <el-col :span="6">
         <el-card style="color: #3F5EFB">
            <div><i class="el-icon-user-solid"></i>用户总数</div>
            <div style="padding: 10px 0;font-weight: bold;text-align: center" >100</div>
            
         </el-card>
      </el-col>
      <el-col :span="6">
         <el-card style="color: #67C23A">
            <div><i class="el-icon-money"></i>销售总量</div>
            <div style="padding: 10px 0;font-weight: bold;text-align: center" >100</div>
         </el-card>
      </el-col>
      <el-col :span="6">
         <el-card style="color: #E6A23C">
            <div><i class="el-icon-bank-card"></i>收益总额</div>
            <div style="padding: 10px 0;font-weight: bold;text-align: center" >100</div>
         </el-card>
      </el-col>
      <el-col :span="6">
         <el-card style="color: #F56C6C">
            <div><i class="el-icon-s-shop"></i>门店总数</div>
            <div style="padding: 10px 0;font-weight: bold;text-align: center" >100</div>
         </el-card>
      </el-col>
   </el-row>
   
   <el-row>
      <el-col :span="12">
         <div id="main" style="width: 500px;height: 450px"></div>
      </el-col>
      <el-col :span="12">
         <div id="pie" style="width: 500px;height: 400px"></div>
      </el-col>
      
   </el-row>
  <!--所有的echarts图都要用id去绑定，指定宽高才能显示   -->
  
</div>
</template>

<script>
//引入echarts
import * as echarts from 'echarts';
import request from "@/utils/request";

export default {
   name: "Home",
   data(){
      return{
       
       }
   },
   mounted() {//页面元素挂载之后才会触发  echarts用法有点像路由
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option = {
         title: {
            text: '各季度会员数量统计',
            subtext: '趋势图',
            left: 'center'
         },
         xAxis: {
            //z轴的属性
            type: 'category',
            data: ["第一季度","第二季度","第三季度","第四季度"]
         },
         yAxis: {
            type: 'value'
         },
         tooltip: {
            trigger: 'item'
         },
         series: [
            {
               //折线图
               name:"星巴克",
               data: [],
               type: 'line'
            },
            {
               name:"星巴克",
               data: [],
               type: 'bar'
            },
            {
               name:"瑞幸",
               data: [],
               type: 'bar'
            },
            {
               name:"瑞幸",
               data: [],
               type: 'line'
            }
         ]
      };
      //饼图
      var pieDom = document.getElementById('pie');
      var pieChart = echarts.init(pieDom);
      var pieoption = {
         title: {
            text: '各季度会员数量统计',
            subtext: '比例图',
            left: 'center'
         },
         tooltip: {
            trigger: 'item'
         },
         legend: {
            orient: 'vertical',
            left: 'left'
         },
         series: [
            {
               type: 'pie',
               radius: '70%',//控制饼图的大小
               label:{
                  normal:{
                     show:true,
                     position:"inner",
                     textStyle:{
                        fontWeight:300,
                        fontsize:14
                     },
                     formatter:"{d}%"
                  }
               },
               data: [],
               emphasis: {
                  itemStyle: {
                     shadowBlur: 10,
                     shadowOffsetX: 0,
                     shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
               }
            },
            {
               name: '各季度人数',
               type: 'pie',
               radius: '70%',//控制饼图的大小
               label:{
                  normal:{
                     show:true,
                     position:"inner",
                     textStyle:{
                        fontWeight:300,
                        fontsize:14
                     },
                     formatter:"{d}%"
                  }
               },
               data: [],
               emphasis: {
                  itemStyle: {
                     shadowBlur: 10,
                     shadowOffsetX: 0,
                     shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
               }
            }
         ]
      };
      
      request.get("/echarts/members").then(res=>{
         //setOption要放在请求里面不然没有图标数据
         //option.xAxis.data=res.data.x
         option.series[0].data=res.data
         option.series[1].data=res.data
         option.series[2].data=["5","1","2","3"]
         option.series[3].data=["5","1","2","3"]
         myChart.setOption(option);
         pieoption.series[0].data=[
            {name:"第一季度",value:res.data[0]},
            {name:"第二季度",value:res.data[1]},
            {name:"第三季度",value:res.data[2]},
            {name:"第四季度",value:res.data[3]}
         ]
         pieChart.setOption(pieoption);
      })
   }
}
</script>

<style scoped>

</style>
